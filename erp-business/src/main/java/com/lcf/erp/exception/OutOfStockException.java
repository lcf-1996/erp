package com.lcf.erp.exception;

/*
	库存不足异常
*/
public class OutOfStockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OutOfStockException() {}
	
	public OutOfStockException(String msg) {
		super(msg);
	}
	
}
