package com.lcf.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.lcf.erp.entity.Menu;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MenuMapper extends Mapper<Menu> {

	/**
	 * 查询指定角色所拥有的菜单
	 * @param roleId
	 * @return
	 */
	@Select("select m.* from role_menu r, menu m where r.menuuuid = m.menuid and r.roleuuid = #{roleId}")
	List<Menu> getRoleMenu(@Param("roleId") long roleId);
	
	@Select("select * from menu where menuid in ("
			+ "select menuuuid from role_menu where roleuuid in ("
			+ "select roleuuid from emp_role where empuuid = #{empId}))")
	List<Menu> getEmpMenu(@Param("empId") long empId);
	
}
