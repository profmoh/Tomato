package com.datazord.dto;

import java.io.Serializable;

public class DestinationDto implements Serializable {

	private static final long serialVersionUID = 2303831025008979709L;

	private String id;
	private String name;
	private boolean ismapped;
	private String mappingId;

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

	public boolean isIsmapped() {
		return ismapped;
	}

	public void setIsmapped(boolean ismapped) {
		this.ismapped = ismapped;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
}
