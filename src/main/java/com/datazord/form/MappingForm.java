package com.datazord.form;

import java.util.ArrayList;
import java.util.List;

import com.datazord.dto.DestinationDto;
import com.datazord.dto.SourceDto;
import com.datazord.model.MappingResult;

public class MappingForm extends BaseForm {

	private static final long serialVersionUID = -7416324809633524833L;

	private Integer mappingType;

	private List<SourceDto> sourceList = new ArrayList<>();
	private List<DestinationDto> destinationList = new ArrayList<>();
	private List<MappingResult> deletedList=new ArrayList<>();

	public List<SourceDto> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<SourceDto> sourceList) {
		this.sourceList = sourceList;
	}

	public List<DestinationDto> getDestinationList() {
		return destinationList;
	}

	public void setDestinationList(List<DestinationDto> destinationList) {
		this.destinationList = destinationList;
	}

	public Integer getMappingType() {
		return mappingType;
	}

	public void setMappingType(Integer mappingType) {
		this.mappingType = mappingType;
	}

	public List<MappingResult> getDeletedList() {
		return deletedList;
	}

	public void setDeletedList(List<MappingResult> deletedList) {
		this.deletedList = deletedList;
	}

}
