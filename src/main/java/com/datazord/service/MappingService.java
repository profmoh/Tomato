package com.datazord.service;

import java.util.List;
import java.util.Map;

import com.datazord.enums.MappingFlag;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;
import com.datazord.model.MappingResult;

public interface MappingService {
	
	static final String MAPPING_RESULT_SEQ_KEY = "mapping_result_seq";
	
	MappingForm getMappingLists(Integer MappingType);
	
	void saveMappingResult(MappingForm mappingForm);

	public Map<MappingFlag, Map<String, String>> getMappingMap() throws MissedMappingException;
	
	void deleteMappingResult(List<MappingResult> mappingList,Integer MappingType);
	
}
