package com.datazord.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.comparators.DestinationDtoComparator;
import com.datazord.comparators.SourceDtoComparator;
import com.datazord.constants.TomatoConstants;
import com.datazord.dto.DestinationDto;
import com.datazord.dto.SourceDto;
import com.datazord.enums.MappingType;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;
import com.datazord.json.tomato.pojo.categories.Category;
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
import reactor.core.publisher.Mono;

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
	
	@Autowired
	private TomatoServiceImpl apiService;

	@Override
	public MappingForm getMappingLists(MappingType mappingType) {
		MappingForm mappingForm = new MappingForm();

		try {
		    List<SourceDto> sourceDtosList = new ArrayList<>();
			List<DestinationDto> destinationDtoList = new ArrayList<>();

			switch (mappingType) {
			case category:
				sourceDtosList = getSourceList(categoriesService.getSourceCategoryList(), MappingType.category);
				destinationDtoList = copyDestinationProperties(categoriesService.getDestinationCategoryList());
				break;
			case color:
				sourceDtosList = getSourceList(productOptionsService.getSourceColorList(), MappingType.color);
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationColorList());
				break;
			case size:
				sourceDtosList = getSourceList(productOptionsService.getSourceSizeList(), MappingType.size);
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationSizeList());
				break;
			case productPath:
				sourceDtosList = getSourceList(productService.getSourceProductList(), MappingType.productPath);
				destinationDtoList = copyDestinationProperties(productService.getDestinationProductList());
				break;
			}

//			removeduplication(sourceDtosList, destinationDtoList);

			Collections.sort(sourceDtosList, new SourceDtoComparator());
			Collections.sort(destinationDtoList, new DestinationDtoComparator());

			mappingForm.setSourceList(sourceDtosList);
			mappingForm.setDestinationList(destinationDtoList);
		} catch (Exception e) {
			logger.error("", e);
		}

		return mappingForm;
	}

//	private List<DestinationDto> removeduplication(List<SourceDto> sourceDtosList, List<DestinationDto> destinationDtoList) {
//		try {
//
//			for (SourceDto sourceDto : sourceDtosList) {
//				if (Utils.isNotEmpty(sourceDto.getChildren()))
//					destinationDtoList.removeIf(p -> {
//						return p.getId().equals(sourceDto.getChildren().getId());
//					});
//				else if (Utils.isNotEmpty(sourceDto.getChildrenList())) {
//					for (DestinationDto childDto : sourceDto.getChildrenList()) {
//						destinationDtoList.removeIf(p -> {
//							return p.getId().equals(childDto.getId());
//						});
//					}
//				}
//			}
//
//			return destinationDtoList;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}

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

	@Override
	public List<MappingResult> findMappingResultBySourceIdAndMappingType(String sourceId, MappingType mappingType) {
		Flux<MappingResult> flux = mappingResultRepository.findBySourceIdAndMappingType(sourceId, mappingType.name());
		return flux.collectList().block();
	}

	@Override
	public MappingResult findMappingResultByDestinationIdAndMappingType(String destinationId, MappingType mappingType) {
		Mono<MappingResult> mappingResult = mappingResultRepository.findByDestinationIdAndMappingType(destinationId, mappingType.name());
		return mappingResult.block();
	}

	private List<SourceDto> getSourceList(List<?> ObjectList, MappingType mappingType) {
		try {

			SourceDto sourceDto;
			DestinationDto dto;
			List<SourceDto> sourceDtos = new ArrayList<>();
			List<DestinationDto> destinationDtos;

			for (Object object : ObjectList) {
				sourceDto = new SourceDto();
				BeanUtils.copyProperties(object, sourceDto);

				List<MappingResult> mappingResults = findMappingResultBySourceIdAndMappingType(sourceDto.getId(), mappingType);

				if (!Utils.isEmptyCollection(mappingResults)) {
					switch (mappingType) {
					case category:
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
					case color:
						DestinationColor destinationColor = productOptionsService
								.getDestinationColorById(mappingResults.get(0).getDestinationId());
						dto = new DestinationDto();
						BeanUtils.copyProperties(destinationColor, dto);
						dto.setIsmapped(true);
						dto.setMappingId(mappingResults.get(0).getId());
						sourceDto.setChildren(dto);
						break;

					case size:
						DestinationSize destinationSize = productOptionsService
								.getDestinationSizeById(mappingResults.get(0).getDestinationId());
						dto = new DestinationDto();
						BeanUtils.copyProperties(destinationSize, dto);
						dto.setIsmapped(true);
						dto.setMappingId(mappingResults.get(0).getId());
						sourceDto.setChildren(dto);
						break;

					case productPath:
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
		try {
			switch (MappingType.valueOf(mappingForm.getMappingType().intValue())) {
			case category:
				saveMultiMapping(mappingForm.getSourceList(), MappingType.category);

				if (!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList()/*, MappingType.category*/);

				break;
			case color:
				saveSingleMApping(mappingForm.getSourceList(), MappingType.color);

				if (!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList()/*, MappingType.color*/);

				break;
			case size:
				saveSingleMApping(mappingForm.getSourceList(), MappingType.size);

				if (!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList()/*, MappingType.size*/);

				break;
			case productPath:
				saveMultiMapping(mappingForm.getSourceList(), MappingType.productPath);

				if (!Utils.isEmptyCollection(mappingForm.getDeletedList()))
					deleteMappingResult(mappingForm.getDeletedList()/*, MappingType.productPath*/);

				break;
			}

		} catch (Exception e) {
			logger.error("", e);
		}

	}
	
	private void saveMultiMapping(List<SourceDto> sourceDtos, MappingType mappingType) {
		try {
			MappingResult mappingResult;

			for (SourceDto sourceDto : sourceDtos) {
				for (DestinationDto destinationDto : sourceDto.getChildrenList()) {
					if (!destinationDto.isIsmapped()) {
						mappingResult = new MappingResult();

						mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
						mappingResult.setSourceId(sourceDto.getId());
						mappingResult.setDestinationId(destinationDto.getId());
						mappingResult.setMappingType(mappingType.name());

						mappingResultRepository.save(mappingResult).subscribe();
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	private void saveSingleMApping(List<SourceDto> sourceDtos, MappingType mappingType) {
		try {
			MappingResult mappingResult;

			for (SourceDto sourceDto : sourceDtos) {
				if (sourceDto.getChildren() != null && !sourceDto.getChildren().isIsmapped()) {
					mappingResult = new MappingResult();

					mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
					mappingResult.setSourceId(sourceDto.getId());
					mappingResult.setDestinationId(sourceDto.getChildren().getId());
					mappingResult.setMappingType(mappingType.name());

					mappingResultRepository.save(mappingResult).subscribe();
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public Map<MappingType, Map<String, String>> getMappingMap() throws MissedMappingException {
		List<MappingResult> mappingResultList = mappingResultRepository.findAll().collectList().block();

		Map<String, String> sourceSizeMap = productOptionsService.getSourceSizeMap();
		Map<String, String> sourceColorMap = productOptionsService.getSourceColorMap();
		Map<String, String> sourceCategoryMap = categoriesService.getSourceCategoriesMap();
		Map<String, String> sourceProductPathMap = productService.getSourceProductPathMap();

		Map<String, String> destinationSizeMap = productOptionsService.getDestinationSizeMap();
		Map<String, String> destinationColorMap = productOptionsService.getDestinationColorMap();
		Map<String, String> destinationCategoryMap = categoriesService.getDestinationCategoriesMap();
		Map<String, String> destinationProductPathMap = productService.getDestinationProductPathMap();

		Map<MappingType, Map<String, String>> resultMapping = new HashMap<>();

		for(MappingResult mappingResult : mappingResultList) {
			MappingType mappingFlag = MappingType.valueOf(mappingResult.getMappingType());

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

			if(mappingFlag.equals(MappingType.productPath))
				resultMapping.get(mappingFlag).put(destinationValue, sourceValue);
			else
				resultMapping.get(mappingFlag).put(sourceValue, destinationValue);
		}

		return resultMapping;
	}

	@Override
	public void deleteMappingResult(List<MappingResult> mappingList) {
		try {

			for (MappingResult mappingResult : mappingList)
				mappingResultRepository.deleteById(mappingResult.getId()).subscribe();

		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void reloadObjects(MappingType MappingType) {
		try {
			switch (MappingType) {
			case productPath:
				productService.saveDestinationProduct();
				break;
			case category:
				List<Category> categories = apiService.findCategories();
				categoriesService.saveDestinationCategories(categories);
				break;
			case color:
				productOptionsService.saveDestinationProductOptions(TomatoConstants.COLOR_PRODUCT_OPTION);
				break;
			case size:
				productOptionsService.saveDestinationProductOptions(TomatoConstants.SIZE_PRODUCT_OPTION);
				break;
			}
		}catch (Exception e) {
			logger.error("", e);
		}
		
	}
}
