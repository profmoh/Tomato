package com.datazord.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SourceDto implements Serializable {

	private static final long serialVersionUID = -1424565048748292347L;

	private String id;
	private String name;

	private DestinationDto children;
	private List<DestinationDto> childrenList = new ArrayList<>();

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



	public List<DestinationDto> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<DestinationDto> childrenList) {
		this.childrenList = childrenList;
	}

	public DestinationDto getChildren() {
		return children;
	}

	public void setChildren(DestinationDto children) {
		this.children = children;
	}

}
