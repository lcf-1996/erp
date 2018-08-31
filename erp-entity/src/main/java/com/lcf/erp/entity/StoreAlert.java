package com.lcf.erp.entity;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="v_storealert")
public class StoreAlert {
	private Long uuid;
	private String name;
	private Integer storenum;
	private Integer outnum;
}
