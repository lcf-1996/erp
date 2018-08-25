package com.lcf.erp.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="storeoper")
public class StoreOper {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="storeoper_seq")
	@SequenceGenerator(name="storeoper_seq", sequenceName="storeoper_seq", allocationSize=1)
	private Long uuid;
	private Long empuuid; //操作员ID
	private Date opertime; //操作时间
	private Long storeuuid; //仓库ID
	private Long goodsuuid; //商品ID
	private Integer num; //数量
	private String type; //1代表采购 2代表销售
}
