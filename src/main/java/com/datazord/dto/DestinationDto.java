package com.datazord.dto;

import java.io.Serializable;

public class DestinationDto implements Serializable {

	private static final long serialVersionUID = 2303831025008979709L;

	private String id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
