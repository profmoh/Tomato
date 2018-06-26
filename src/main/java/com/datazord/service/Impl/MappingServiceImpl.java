package com.datazord.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.constants.TomatoConstants;
import com.datazord.dto.DestinationDto;
import com.datazord.dto.SourceDto;
import com.datazord.form.MappingForm;
import com.datazord.model.MappingResult;
import com.datazord.repositories.MappingResultRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.service.CategoriesService;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.utils.Utils;

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
			case 1: // CATEGORY_MAPPING
				sourceDtosList = copySourceProperties(categoriesService.getSourceCategoryList());
				destinationDtoList = copyDestinationProperties(categoriesService.getDestinationCategoryList());
				break;
			case 2: // COLOR_MAPPING
				sourceDtosList = copySourceProperties(productOptionsService.getSourceColorList());
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationColorList());
				break;
			case 3: // SIZE_MAPPING
				sourceDtosList = copySourceProperties(productOptionsService.getSourceSizeList());
				destinationDtoList = copyDestinationProperties(productOptionsService.getDestinationSizeList());
				break;
			case 4: // PRODUCT_MAPPING
				sourceDtosList = copySourceProperties(productService.getSourceProductList());
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

	private List<SourceDto> copySourceProperties(List<?> ObjectList) {
		SourceDto sourceDto;
		List<SourceDto> sourceDtos = new ArrayList<>();

		for (Object object : ObjectList) {
			sourceDto = new SourceDto();
			BeanUtils.copyProperties(object, sourceDto);
			sourceDtos.add(sourceDto);
		}

		return sourceDtos;
	}

	@Override
	public void saveMappingResult(MappingForm mappingForm) {
		try{
			switch (mappingForm.getMappingType()) {
			case 1: 
				saveMultiMapping(mappingForm.getSourceList(), TomatoConstants.CATEGORY_MAPPING);				
				break;
			case 2:
				saveSingleMApping(mappingForm.getSourceList(), TomatoConstants.COLOR_MAPPING);
				break;
				
			case 3:
				saveSingleMApping(mappingForm.getSourceList(), TomatoConstants.SIZE_MAPPING);
				break;
				
			case 4:
				saveMultiMapping(mappingForm.getSourceList(), TomatoConstants.PRODUCT_MAPPING);
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
					mappingResult=new MappingResult();
					mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
					mappingResult.setSourceId(sourceDto.getId());
					mappingResult.setDestinationId(destinationDto.getId());
					mappingResult.setMappingType(mappingType);
					
					mappingResultRepository.save(mappingResult).subscribe();
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
				if(sourceDto.getChildren() != null){
				mappingResult=new MappingResult();
				mappingResult.setId(sequenceRepositorys.getNextSequenceId(MAPPING_RESULT_SEQ_KEY));
				mappingResult.setSourceId(sourceDto.getId());
				mappingResult.setDestinationId(sourceDto.getChildren().getId());
				mappingResult.setMappingType(mappingType);
				mappingResultRepository.save(mappingResult).subscribe();
				}else throw new NullPointerException("destination obj is null");
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
