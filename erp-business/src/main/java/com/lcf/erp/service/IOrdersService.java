package com.lcf.erp.service;

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
}
