package com.datazord.service;

import com.datazord.form.MappingForm;

public interface MappingService {
	
	static final String MAPPING_RESULT_SEQ_KEY = "mapping_result_seq";
	
	MappingForm getMappingLists(Integer MappingType);
	
	void saveMappingResult(MappingForm mappingForm);
	
}
