package com.lcf.erp.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Supplier {
	@Id
	private Integer uuid;
	private String name;
	private String address;
	private String contact;
	private String tele;
	private String email;
	private String type; //1代表供应商，2代表客户
}
