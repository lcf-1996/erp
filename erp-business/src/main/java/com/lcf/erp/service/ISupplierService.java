package com.lcf.erp.service;

import java.util.List;

import com.lcf.erp.entity.Supplier;

public interface ISupplierService extends IBaseService<Supplier> {

	/**
	 * 查询供应商的数据
	 * @param supplier
	 * @return
	 */
	List<Supplier> findSuppliers(Supplier supplier);
	
}
