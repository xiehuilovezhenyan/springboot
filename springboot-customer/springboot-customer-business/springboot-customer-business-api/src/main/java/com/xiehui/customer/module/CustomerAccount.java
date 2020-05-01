package com.xiehui.customer.module;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -155401992348403093L;

	private Long id;
	private Long amount;
	public CustomerAccount(Long id, Long amount) {
		super();
		this.id = id;
		this.amount = amount;
	}

}
