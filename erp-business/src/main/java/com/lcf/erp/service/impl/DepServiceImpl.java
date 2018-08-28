package com.lcf.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lcf.erp.entity.Dep;
import com.lcf.erp.mapper.DepMapper;
import com.lcf.erp.service.IDepService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(readOnly = true)
public class DepServiceImpl extends BaseServiceImpl<Dep> implements IDepService {
	@Autowired
	private DepMapper mapper;
	
	@Override
	public Mapper<Dep> getMapper() {
		return this.mapper;
	}
	
	@Override
	public List<Dep> findDeps(Dep dep) {
		//封装查询条件
		Example example = new Example(Dep.class);
		//查询条件
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(dep.getName())) {
			c.andLike("name", "%" + dep.getName() + "%"); //相当于name like '%xxx%'
		}
		if (!StringUtils.isEmpty(dep.getTele())) {
			c.andEqualTo("tele", dep.getTele()); //相当于tele = xxxx
		}
		//设置排序
		example.setOrderByClause("uuid desc");
		return mapper.selectByExample(example);
	}

	@Override
	public int getTotal(Dep dep) {
		Example example = new Example(Dep.class);
		//查询条件
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(dep.getName())) {
			c.andLike("name", "%" + dep.getName() + "%"); //相当于name like '%xxx%'
		}
		if (!StringUtils.isEmpty(dep.getTele())) {
			c.andEqualTo("tele", dep.getTele()); //相当于tele = xxxx
		}
		return mapper.selectCountByExample(example);
	}

}