package com.lcf.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.lcf.erp.entity.Dep;
import com.lcf.erp.mapper.DepMapper;
import com.lcf.erp.service.IDepService;

@Service
public class DepServiceImpl implements IDepService {
	@Autowired
	private DepMapper mapper;
	
	@Override
	public List<Dep> findDeps(Dep dep) {
		//构造查询条件
		Example example = new Example(Dep.class);
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(dep.getName())) {
			c.andLike("name", "%" + dep.getName() + "%");
		}
		if (!StringUtils.isEmpty(dep.getTele())) {
			c.andEqualTo("tele", dep.getTele());
		}
		
		//设置排序
		example.setOrderByClause("uuid desc");

		return mapper.selectByExample(example);
	}

	@Override
	public int getTotal(Dep dep) {
		//构造查询条件
		Example example = new Example(Dep.class);
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(dep.getName())) {
			c.andLike("name", "%" + dep.getName() + "%");
		}
		if (!StringUtils.isEmpty(dep.getTele())) {
			c.andLike("tele", dep.getTele());
		}
		return mapper.selectCountByExample(example);
	}

	@Override
	public void addDep(Dep dep) {
		mapper.insertSelective(dep);
	}

	@Override
	public void delDep(int uuid) {
		mapper.deleteByPrimaryKey(uuid);
	}

	@Override
	public void updateDep(Dep dep) {
		mapper.updateByPrimaryKey(dep);
	}

}
