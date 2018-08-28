package com.lcf.erp.mapper;

import org.springframework.stereotype.Repository;

import com.lcf.erp.entity.Dep;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DepMapper extends Mapper<Dep> {

	/*@Select("select * from dep")
	public List<Dep> findAll();*/
	
}
