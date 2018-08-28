package com.lcf.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcf.erp.entity.OrderReport;
import com.lcf.erp.entity.Orders;
import com.lcf.erp.entity.OrdersDetail;
import com.lcf.erp.entity.StoreDetail;
import com.lcf.erp.entity.StoreOper;
import com.lcf.erp.exception.OutOfStockException;
import com.lcf.erp.mapper.OrdersDetailMapper;
import com.lcf.erp.mapper.OrdersMapper;
import com.lcf.erp.mapper.StoreDetailMapper;
import com.lcf.erp.mapper.StoreOperMapper;
import com.lcf.erp.service.IOrdersService;

import tk.mybatis.mapper.common.Mapper;

@Service
@Transactional(readOnly = true)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements IOrdersService {
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrdersDetailMapper ordersDetailMapper;
	@Autowired
	private StoreDetailMapper storeDetailMapper;
	@Autowired
	private StoreOperMapper storeOperMapper;
	
	@Override
	public Mapper<Orders> getMapper() {
		return this.ordersMapper;
	}

	@Override
	public void addOrders(Orders orders) {
		//保存订单
		ordersMapper.insertPrimaryKey(orders);
		//保存订单明细
		for (OrdersDetail ordersDetail : orders.getOrderDetails()) {
			ordersDetail.setOrdersuuid(orders.getUuid()); //设置关联订单的编号
			ordersDetail.setState("0"); //订单明细状态为“未入库”
			ordersDetailMapper.insertSelective(ordersDetail);
		}
	}

	@Override
	public void doInstore(Integer ordersdetailuuid, Integer storeuuid,
			Long empuuid) {
		//1.更新订单明细的状态
		OrdersDetail ordersDetail = ordersDetailMapper.selectByPrimaryKey(
				ordersdetailuuid.longValue());
		ordersDetail.setEnder(empuuid.intValue()); //设置操作员
		ordersDetail.setEndtime(new Date()); //设置入库时间
		ordersDetail.setState("1"); //修改为“已入库”状态
		ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
		//2.修改商品的库存
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setStoreuuid(storeuuid.longValue());
		storeDetail.setGoodsuuid(ordersDetail.getGoodsuuid());
		StoreDetail sd = storeDetailMapper.selectOne(storeDetail);
		//判断仓库库存中是否存在该商品的记录
		if (sd != null) { //如果不为空就代表该商品已经存在
			int amount = sd.getNum() + ordersDetail.getNum();
			sd.setNum(amount);
			storeDetailMapper.updateByPrimaryKeySelective(sd);
		} else { //如果商品不存在，往库存表中添加一条新的记录
			storeDetail.setNum(ordersDetail.getNum());
			storeDetailMapper.insertSelective(storeDetail);
		}
		//3. 添加仓库操作记录
		StoreOper storeOper = new StoreOper();
		storeOper.setEmpuuid(empuuid);
		storeOper.setOpertime(new Date());
		storeOper.setStoreuuid(storeuuid.longValue());
		storeOper.setGoodsuuid(ordersDetail.getGoodsuuid());
		storeOper.setNum(ordersDetail.getNum());
		storeOper.setType("1");
		storeOperMapper.insertSelective(storeOper);
		//4. 判断当前操作的订单明细是否全部入库，如果全部入库，将订单主表中的state修改为3
		OrdersDetail temp = new OrdersDetail();
		temp.setState("0");
		temp.setOrdersuuid(ordersDetail.getOrdersuuid());
		List<OrdersDetail> ordersDetailList  = ordersDetailMapper.select(temp);
		if (ordersDetailList.size()  == 0) { //如果集合为空，那么就代表所有商品已入库
			Orders orders = new Orders();
			orders.setUuid(ordersDetail.getOrdersuuid());
			orders.setState("3");
			orders.setEndtime(new Date());
			orders.setEnder(empuuid);
			ordersMapper.updateByPrimaryKeySelective(orders);
		}
	}

	@Override
	public void doOutstore(Integer ordersdetailuuid, Integer storeuuid,
			Long empuuid) {
		//1.更新订单明细的状态
		OrdersDetail ordersDetail = ordersDetailMapper.selectByPrimaryKey(
				ordersdetailuuid.longValue());
		ordersDetail.setEnder(empuuid.intValue()); //设置操作员
		ordersDetail.setEndtime(new Date()); //设置出库时间
		ordersDetail.setState("1"); //修改为“已出库”状态
		ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
		//2.修改商品的库存
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setStoreuuid(storeuuid.longValue());
		storeDetail.setGoodsuuid(ordersDetail.getGoodsuuid());
		StoreDetail sd = storeDetailMapper.selectOne(storeDetail);
		//判断仓库库存中是否存在该商品的记录
		if (sd != null) { //如果不为空就代表该商品已经存在
			int amount = sd.getNum() - ordersDetail.getNum();
			if (amount < 0) {
				//进行库存不足的处理...
				throw new OutOfStockException("库存不足");
			} else {
				sd.setNum(amount);
				storeDetailMapper.updateByPrimaryKeySelective(sd);
			}
		} else { //如果商品不存在
			//进行库存不足的处理...
			throw new OutOfStockException("库存不足");
		}
		//3. 添加仓库操作记录
		StoreOper storeOper = new StoreOper();
		storeOper.setEmpuuid(empuuid);
		storeOper.setOpertime(new Date());
		storeOper.setStoreuuid(storeuuid.longValue());
		storeOper.setGoodsuuid(ordersDetail.getGoodsuuid());
		storeOper.setNum(ordersDetail.getNum());
		storeOper.setType("2");
		storeOperMapper.insertSelective(storeOper);
		//4. 判断当前操作的订单明细是否全部出库，如果全部出库，将订单主表中的state修改为3
		OrdersDetail temp = new OrdersDetail();
		temp.setState("0");
		temp.setOrdersuuid(ordersDetail.getOrdersuuid());
		List<OrdersDetail> ordersDetailList  = ordersDetailMapper.select(temp);
		if (ordersDetailList.size()  == 0) { //如果集合为空，那么就代表所有商品已出库
			Orders orders = new Orders();
			orders.setUuid(ordersDetail.getOrdersuuid());
			orders.setState("3");
			orders.setEndtime(new Date());
			orders.setEnder(empuuid);
			ordersMapper.updateByPrimaryKeySelective(orders);
		}
	}

	@Override
	public List<OrderReport> getOrderReport(Date startDate, Date endDate) {
		return ordersMapper.selectOrderReport(startDate, endDate);
	}

}
