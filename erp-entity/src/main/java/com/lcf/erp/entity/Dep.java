package com.lcf.erp.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Dep implements Serializable {
	@Id
	private Integer uuid;
	private String name;
	private String tele;
	
	public Dep() {}
	
	@Override
	public String toString() {
		return "Dep [uuid=" + uuid + ", name=" + name + ", tele=" + tele + "]";
	}	
}