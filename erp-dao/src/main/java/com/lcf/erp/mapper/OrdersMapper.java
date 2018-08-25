package com.lcf.erp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.lcf.erp.entity.Orders;

@Repository
public interface OrdersMapper extends Mapper<Orders> {
	/**
	 * 保存订单，并返回订单的UUID
	 * @param orders
	 */
	@Insert("insert into orders(uuid, createtime, creater, type, supplieruuid, totalmoney, state)"
			+ " values(#{uuid}, #{createtime}, #{creater}, #{type}, #{supplieruuid}, #{totalmoney}, #{state})")
	@SelectKey(statement="SELECT orders_seq.nextval as uuid from dual"
		, keyProperty="uuid", before=true, resultType=Long.class)
	void insertPrimaryKey(Orders orders);
}
