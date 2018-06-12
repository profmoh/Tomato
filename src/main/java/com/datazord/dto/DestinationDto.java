package com.datazord.dto;

import java.io.Serializable;

public class DestinationDto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2303831025008979709L;
	
	private String name;
	private Integer id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
