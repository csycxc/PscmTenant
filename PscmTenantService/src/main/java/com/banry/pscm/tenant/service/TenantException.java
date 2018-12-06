package com.banry.pscm.tenant.service;

public class TenantException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1476299762910351969L;

	public TenantException(){
		super();
	}
	
	public TenantException(String message){
		super(message);
	}
	
	public TenantException(String message,Throwable e){
		super(message, e);
	}

}
