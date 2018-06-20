package com.datazord.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.dto.DestinationDto;
import com.datazord.form.MappingForm;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.service.TomatoCategoriesService;

@Component
public class MappingServiceImpl implements MappingService{

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
			
	@Autowired
	private TomatoCategoriesService categoriesService;
	
	@Autowired
	private ProductOptionsService productOptionsService; 
	
	@Autowired
	private ProductService productService;
		
	@Override
	public MappingForm getMappingLists(Integer MappingType) {
		MappingForm mappingForm=new MappingForm();
		try{
		
		List<DestinationDto> destinationDtoList=new ArrayList<>();
		 switch (MappingType) {
		case 1: //CATEGORY_MAPPING
			destinationDtoList=copyDestinationProperties(categoriesService.getDestinationCategoryList());
			break;
		case 2: //COLOR_MAPPING 
			destinationDtoList=copyDestinationProperties(productOptionsService.getDestinationColorList());
			break;
		case 3: //SIZE_MAPPING	
			destinationDtoList=copyDestinationProperties(productOptionsService.getDestinationSizeList());
			break;
		case 4: //PRODUCT_MAPPING
			destinationDtoList=copyDestinationProperties(productService.getDestinationProducts());
			break;
		}
		 
		 mappingForm.setDestinationList(destinationDtoList); 
		}catch (Exception e) {
			logger.error("",e);
		}
		
		return mappingForm;
	}
	
	private List<DestinationDto> copyDestinationProperties(List<?>Source){
		DestinationDto dto;
		List<DestinationDto> destinationDtos=new ArrayList<>();
		for (Object object : Source) {
			dto=new DestinationDto();
			BeanUtils.copyProperties(object,dto);
			destinationDtos.add(dto);
		}
		return destinationDtos;
	}

}
