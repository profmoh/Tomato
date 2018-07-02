package com.datazord.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.constants.TomatoConstants;
import com.datazord.dto.DestinationDto;
import com.datazord.dto.SourceDto;
import com.datazord.enums.MappingFlag;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;
import com.datazord.model.MappingResult;
import com.datazord.model.destination.DestinationCategories;
import com.datazord.model.destination.DestinationColor;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.destination.DestinationSize;
import com.datazord.repositories.MappingResultRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.service.CategoriesService;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.utils.Utils;

import reactor.core.publisher.Flux;

@Component
public class MappingServiceImpl implements MappingService {

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private ProductOptionsService productOptionsService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SequenceRepository sequenceRepositorys;
	
	@Autowired
	private MappingResultRepository mappingResultRepository;

	@Override
	public MappingForm getMappingLists(Integer MappingType) {
		MappingForm mappingForm = new MappingForm();

		try {
			List<SourceDto> sourceDtosList = new ArrayList<>();
			List<DestinationDto> destinationDtoList = new ArrayList<>();

			switch (MappingType) {
			case TomatoConstants.CATEGORY_MAPPING:
				sourceDtosList = getSourceList(categoriesService.getSourceCategoryList(),TomatoConstants.CATEGORY_MAPPING);
				destinationDtoList = copyDestinationProperties(categoriesService.getDestinationCategoryList());
				break;
			case TomatoConstants.COLOR_MAPPING: 
				sourceDtosList = getSourceList(productOptionsService.getSourceColorList(),TomatoConstants.COLOR_MAPPING);
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationColorList());
				break;
			case TomatoConstants.SIZE_MAPPING: 
				sourceDtosList = getSourceList(productOptionsService.getSourceSizeList(),TomatoConstants.SIZE_MAPPING);
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationSizeList());
				break;
			case TomatoConstants.PRODUCT_MAPPING: 
				sourceDtosList = getSourceList(productService.getSourceProductList(),TomatoConstants.PRODUCT_MAPPING);
				destinationDtoList = copyDestinationProperties(productService.getDestinationProductList());
				break;
			}

			mappingForm.setSourceList(sourceDtosList);
			mappingForm.setDestinationList(destinationDtoList);
		} catch (Exception e) {
			logger.error("", e);
		}

		return mappingForm;
	}

	private List<DestinationDto> copyDestinationProperties(List<?> ObjectList) {
		DestinationDto dto;
		List<DestinationDto> destinationDtos = new ArrayList<>();

		for (Object object : ObjectList) {
			dto = new DestinationDto();
			BeanUtils.copyProperties(object, dto);
			destinationDtos.add(dto);
		}

		return destinationDtos;
	}
	
	private List<SourceDto> getSourceList(List<?> ObjectList, Integer mappingType){
		try {

			SourceDto sourceDto;
			DestinationDto dto;
			List<SourceDto> sourceDtos = new ArrayList<>();
			List<DestinationDto> destinationDtos;

			for (Object object : ObjectList) {
				sourceDto = new SourceDto();
				BeanUtils.copyProperties(object, sourceDto);

				Flux<MappingResult> flux = mappingResultRepository.findBySourceIdAndMappingType(sourceDto.getId(),
						MappingFlag.valueOf(mappingType).name());
				List<MappingResult> mappingResults = flux.collectList().block();

				if (!Utils.isEmptyCollection(mappingResults)) {
					switch (mappingType) {
					case TomatoConstants.CATEGORY_MAPPING:
						destinationDtos = new ArrayList<>();
						for (MappingResult mappingResult : mappingResults) {
							DestinationCategories destinationCategory = categoriesService
									.getDestinationCategoryById(mappingResult.getDestinationId());
							dto = new DestinationDto();
							BeanUtils.copyProperties(destinationCategory, dto);
							dto.setIsmapped(true);
							dto.setMappingId(mappingResult.getId());
							destinationDtos.add(dto);
						}
						sourceDto.setChildrenList(destinationDtos);
						break;
					case TomatoConstants.COLOR_MAPPING:
						DestinationColor destinationColor = productOptionsService
								.getDestinationColorById(mappingResults.get(0).getDestinationId());
						dto = new DestinationDto();
						BeanUtils.copyProperties(destinationColor, dto);
						dto.setIsmapped(true);
						dto.setMappingId(mappingResults.get(0).getId());
						sourceDto.setChildren(dto);
						break;

					case TomatoConstants.SIZE_MAPPING:
						DestinationSize destinationSize = productOptionsService
								.getDestinationSizeById(mappingResults.get(0).getDestinationId());
						dto = new DestinationDto();
						BeanUtils.copyProperties(destinationSize, dto);
						dto.setIsmapped(true);
						dto.setMappingId(mappingResults.get(0).getId());
						sourceDto.setChildren(dto);
						break;

					case TomatoConstants.PRODUCT_MAPPING:
						destinationDtos = new ArrayList<>();
						for (MappingResult mappingResult : mappingResults) {
							DestinationProduct destinationProduct = productService
									.getDestinationProductById(mappingResult.getDestinationId());
							dto = new DestinationDto();
							BeanUtils.copyProperties(destinationProduct, dto);
							dto.setIsmapped(true);
							dto.setMappingId(mappingResult.getId());
							destinationDtos.add(dto);
						}
						sourceDto.setChildrenList(destinationDtos);
						break;
					}
				}
					sourceDtos.add(sourceDto);
			}
			return sourceDtos;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public void saveMappingResult(MappingForm mappingForm) {
		try{
			switch (mappingForm.getMappingType()) {
			case TomatoConstants.CATEGORY_MAPPING: 
				saveMultiMapping(mappingForm.getSourceList(), TomatoConstants.CATEGORY_MAPPING);
				if(!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList(),TomatoConstants.CATEGORY_MAPPING);
				break;
			case TomatoConstants.COLOR_MAPPING:
				saveSingleMApping(mappingForm.getSourceList(), TomatoConstants.COLOR_MAPPING);
				if(!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList(),TomatoConstants.COLOR_MAPPING);
				break;
				
			case TomatoConstants.SIZE_MAPPING:
				saveSingleMApping(mappingForm.getSourceList(), TomatoConstants.SIZE_MAPPING);
				if(!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList(),TomatoConstants.SIZE_MAPPING);
				break;
				
			case TomatoConstants.PRODUCT_MAPPING:
				saveMultiMapping(mappingForm.getSourceList(), TomatoConstants.PRODUCT_MAPPING);
				if(!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList(),TomatoConstants.PRODUCT_MAPPING);
				break;
			}
	
		}catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	private void saveMultiMapping(List<SourceDto> sourceDtos, Integer mappingType){
		try {
			MappingResult mappingResult;
			for (SourceDto sourceDto : sourceDtos) {
				for (DestinationDto destinationDto : sourceDto.getChildrenList()) {
					if(!destinationDto.isIsmapped()){
					mappingResult=new MappingResult();
					mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
					mappingResult.setSourceId(sourceDto.getId());
					mappingResult.setDestinationId(destinationDto.getId());
					mappingResult.setMappingType(MappingFlag.valueOf(mappingType).name());
					
					mappingResultRepository.save(mappingResult).subscribe();
					}
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private void saveSingleMApping(List<SourceDto> sourceDtos, Integer mappingType){
		try {
			MappingResult mappingResult;
			for (SourceDto sourceDto : sourceDtos) {
				if(sourceDto.getChildren() != null && !sourceDto.getChildren().isIsmapped()){
				mappingResult=new MappingResult();
				mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
				mappingResult.setSourceId(sourceDto.getId());
				mappingResult.setDestinationId(sourceDto.getChildren().getId());
				mappingResult.setMappingType(MappingFlag.valueOf(mappingType).name());
				mappingResultRepository.save(mappingResult).subscribe();
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	@Override
	public Map<MappingFlag, Map<String, String>> getMappingMap() throws MissedMappingException {
		List<MappingResult> mappingResultList = mappingResultRepository.findAll().collectList().block();

		Map<String, String> sourceSizeMap = productOptionsService.getSourceSizeMap();
		Map<String, String> sourceColorMap = productOptionsService.getSourceColorMap();
		Map<String, String> sourceCategoryMap = categoriesService.getSourceCategoriesMap();
		Map<String, String> sourceProductPathMap = productService.getSourceProductPathMap();

		Map<String, String> destinationSizeMap = productOptionsService.getDestinationSizeMap();
		Map<String, String> destinationColorMap = productOptionsService.getDestinationColorMap();
		Map<String, String> destinationCategoryMap = categoriesService.getDestinationCategoriesMap();
		Map<String, String> destinationProductPathMap = productService.getDestinationProductPathMap();

		Map<MappingFlag, Map<String, String>> resultMapping = new HashMap<>();

		for(MappingResult mappingResult : mappingResultList) {
			MappingFlag mappingFlag = MappingFlag.valueOf(mappingResult.getMappingType());

			if(resultMapping.get(mappingFlag) == null)
				resultMapping.put(mappingFlag, new HashMap<>());

			String sourceValue = null;
			String destinationValue = null;

			switch (mappingFlag) {
			case size:
				sourceValue = sourceSizeMap.get(mappingResult.getSourceId());
				destinationValue = destinationSizeMap.get(mappingResult.getDestinationId());
				break;
			case color:
				sourceValue = sourceColorMap.get(mappingResult.getSourceId());
				destinationValue = destinationColorMap.get(mappingResult.getDestinationId());
				break;
			case category:
				sourceValue = sourceCategoryMap.get(mappingResult.getSourceId());
				destinationValue = destinationCategoryMap.get(mappingResult.getDestinationId());
				break;
			case productPath:
				sourceValue = sourceProductPathMap.get(mappingResult.getSourceId());
				destinationValue = destinationProductPathMap.get(mappingResult.getDestinationId());
				break;
			default:
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);
			}

			if(StringUtils.isBlank(sourceValue) || StringUtils.isBlank(destinationValue))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			resultMapping.get(mappingFlag).put(sourceValue, destinationValue);
		}

		return resultMapping;
	}

	@Override
	public void deleteMappingResult(List<MappingResult> mappingList,Integer MappingType) {
		try {

			for (MappingResult mappingResult : mappingList) {
				mappingResultRepository.deleteById(mappingResult.getId()).subscribe();
			}

		} catch (Exception e) {
			logger.error("", e);
		}

	}
}
