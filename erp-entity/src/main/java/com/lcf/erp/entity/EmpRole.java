package com.lcf.erp.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class EmpRole {
	@Id
	private String empuuid;
	private String roleuuid;
}
