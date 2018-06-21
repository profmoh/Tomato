package com.datazord.service.Impl;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsInstanceOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.dto.DestinationDto;
import com.datazord.dto.SourceDto;
import com.datazord.form.MappingForm;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.service.CategoriesService;

@Component
public class MappingServiceImpl implements MappingService{

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
			
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private ProductOptionsService productOptionsService; 
	
	@Autowired
	private ProductService productService;
		
	@Override
	public MappingForm getMappingLists(Integer MappingType) {
		MappingForm mappingForm=new MappingForm();
		try{
		List<SourceDto>sourceDtosList=new ArrayList<>(); 
		List<DestinationDto> destinationDtoList=new ArrayList<>();
		 switch (MappingType) {
		case 1: //CATEGORY_MAPPING
			sourceDtosList=copySourceProperties(categoriesService.getSourceCategoryList());
			destinationDtoList= copyDestinationProperties(categoriesService.getDestinationCategoryList());
			break;
		case 2: //COLOR_MAPPING 
			sourceDtosList=copySourceProperties(productOptionsService.getSourceColorList());
			destinationDtoList=copyDestinationProperties(productOptionsService.getDestinationColorList());
			break;
		case 3: //SIZE_MAPPING	
			sourceDtosList=copySourceProperties(productOptionsService.getSourceSizeList());
			destinationDtoList=copyDestinationProperties(productOptionsService.getDestinationSizeList());
			break;
		case 4: //PRODUCT_MAPPING
			sourceDtosList=copySourceProperties(productService.getSourceProductList());
			destinationDtoList=copyDestinationProperties(productService.getDestinationProductList());
			break;
		}
		 mappingForm.setSourceList(sourceDtosList);
		 mappingForm.setDestinationList(destinationDtoList); 
		}catch (Exception e) {
			logger.error("",e);
		}
		
		return mappingForm;
	}
	
	private List<DestinationDto> copyDestinationProperties(List<?>ObjectList){
		DestinationDto dto;
		List<DestinationDto> destinationDtos=new ArrayList<>();
		for (Object object : ObjectList) {
			dto=new DestinationDto();
			BeanUtils.copyProperties(object,dto);
			destinationDtos.add(dto);
		}
		return destinationDtos;
	}
	
	private List<SourceDto> copySourceProperties(List<?>ObjectList){
        SourceDto sourceDto;
		List<SourceDto> sourceDtos=new ArrayList<>();
			for (Object object : ObjectList) {
				sourceDto=new SourceDto();
				BeanUtils.copyProperties(object,sourceDto);
				sourceDtos.add(sourceDto);
			}
		return sourceDtos;
	}

}
