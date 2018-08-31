package com.lcf.erp.entity;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RoleMenu {
	@Id
	private String roleuuid;
	private String menuuuid;
}
