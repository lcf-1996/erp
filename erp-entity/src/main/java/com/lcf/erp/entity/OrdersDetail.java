package com.lcf.erp.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Table(name="orderdetail")
public class OrdersDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="orderdetail_seq")
	@SequenceGenerator(name="orderdetail_seq",sequenceName="orderdetail_seq", allocationSize=1)
	private Long uuid;
	private Long goodsuuid;
	private String goodsname;
	private Double price;
	private Integer num;
	private Double money;
	private Date endtime;
	private Integer ender;
	private Integer storeuuid;
	private String state;
	private Long ordersuuid;
}
