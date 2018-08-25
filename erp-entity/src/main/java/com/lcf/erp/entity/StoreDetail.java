package com.lcf.erp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="storedetail")
public class StoreDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="storedetail_seq")
	@SequenceGenerator(name="storedetail_seq", sequenceName="storedetail_seq", allocationSize=1)
	private Long uuid;
	private Long storeuuid; //仓库ID
	private Long goodsuuid; //商品ID
	private Integer num; //商品数量
}
