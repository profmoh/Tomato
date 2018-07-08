package com.datazord.service;

import java.util.List;
import java.util.Map;

import com.datazord.enums.MappingType;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;
import com.datazord.model.MappingResult;

public interface MappingService {

	static final String MAPPING_RESULT_SEQ_KEY = "mapping_result_seq";

	MappingForm getMappingLists(MappingType MappingType);

	void saveMappingResult(MappingForm mappingForm);

	public Map<MappingType, Map<String, String>> getMappingMap() throws MissedMappingException;

	void deleteMappingResult(List<MappingResult> mappingList, MappingType MappingType);

	List<MappingResult> findMappingResultBySourceIdAndMappingType(String sourceId, MappingType mappingType);

	MappingResult findMappingResultByDestinationIdAndMappingType(String destinationId, MappingType mappingType);

	void reloadObjects(MappingType MappingType);
}
