package com.lcf.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.Role;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EmpMapper extends Mapper<Emp> {

	@Select("select r.* from emp_role e, role r "
			+ "where e.roleuuid = r.uuid "
			+ "and e.empuuid = #{empuuid}")
	List<Role> getEmpRoles(@Param("empuuid") long empuuid);
}