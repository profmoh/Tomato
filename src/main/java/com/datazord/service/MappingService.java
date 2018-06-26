package com.datazord.service;

import java.util.Map;

import com.datazord.enums.MappingFlag;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;

public interface MappingService {
	
	static final String MAPPING_RESULT_SEQ_KEY = "mapping_result_seq";
	
	MappingForm getMappingLists(Integer MappingType);
	
	void saveMappingResult(MappingForm mappingForm);

	public Map<MappingFlag, Map<String, String>> getMappingMap() throws MissedMappingException;
	
}
