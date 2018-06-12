package com.datazord.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.datazord.dto.SourceDto;

public class MappingForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -399552764324287606L;
	
	private List<SourceDto> sourceList=new ArrayList<>();

	public List<SourceDto> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<SourceDto> sourceList) {
		this.sourceList = sourceList;
	}
	
}
