package com.lcf.erp.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
public class Dep implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dep_seq")
	@SequenceGenerator(name="dep_seq", sequenceName="dep_seq", allocationSize=1)
	private Integer uuid;
	private String name;
	private String tele;
	
	public Dep() {}
	
	@Override
	public String toString() {
		return "Dep [uuid=" + uuid + ", name=" + name + ", tele=" + tele + "]";
	}	
}