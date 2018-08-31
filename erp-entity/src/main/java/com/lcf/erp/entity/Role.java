package com.lcf.erp.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Role {
	@Id
	private String uuid;
	private String name;
}
