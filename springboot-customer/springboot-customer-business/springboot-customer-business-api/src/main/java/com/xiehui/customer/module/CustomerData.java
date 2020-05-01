package com.xiehui.customer.module;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2589774561254826052L;
	
	private Long id;
	private String name;
	private String mobile;
	public CustomerData(Long id, String name, String mobile) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
	}

}
