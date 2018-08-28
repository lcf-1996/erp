package com.lcf.erp.service;

import java.util.Date;
import java.util.List;

import com.lcf.erp.entity.OrderReport;
import com.lcf.erp.entity.Orders;

public interface IOrdersService extends IBaseService<Orders> {

	/**
	 * 添加订单
	 * @param orders 订单和订单明细的数据
	 */
	void addOrders(Orders orders);

	/**
	 * 商品入库
	 * @param ordersdetailuuid 商品明细ID
	 * @param storeuuid 仓库ID
	 * @param empuuid 操作员ID
	 */
	void doInstore(Integer ordersdetailuuid, Integer storeuuid, Long empuuid);

	/**
	 * 商品出库
	 * @param ordersdetailuuid 商品明细ID
	 * @param storeuuid 仓库ID
	 * @param empuuid 操作员ID
	 */
	void doOutstore(Integer ordersdetailuuid, Integer storeuuid, Long empuuid);
	
	/**
	 * 统计商品类别的销售额
	 * @startDate 订单创建的开始时间
	 * @endDate 订单创建的结束时间
	 * @return
	 */
	List<OrderReport> getOrderReport(Date startDate, Date endDate);

}
