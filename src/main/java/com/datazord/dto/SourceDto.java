package com.datazord.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SourceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1424565048748292347L;
	
	private String name;
	private Integer id;
	private List<DestinationDto>destinationMappingList=new ArrayList<>();
	private DestinationDto destinationMappingObj;
	
	
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
	public List<DestinationDto> getDestinationMappingList() {
		return destinationMappingList;
	}
	public void setDestinationMappingList(List<DestinationDto> destinationMappingList) {
		this.destinationMappingList = destinationMappingList;
	}
	public DestinationDto getDestinationMappingObj() {
		return destinationMappingObj;
	}
	public void setDestinationMappingObj(DestinationDto destinationMappingObj) {
		this.destinationMappingObj = destinationMappingObj;
	}

}
