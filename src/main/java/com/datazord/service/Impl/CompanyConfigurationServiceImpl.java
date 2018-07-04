package com.datazord.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.constants.TomatoConstants;
import com.datazord.dto.CompanyConfigurationDto;
import com.datazord.model.CompanyConfiguration;
import com.datazord.repositories.CompanyConfigurationRepository;
import com.datazord.service.CompanyConfigurationService;
import com.datazord.utils.Utils;

@Component
public class CompanyConfigurationServiceImpl implements CompanyConfigurationService{

	private static final Logger logger = LoggerFactory.getLogger(CompanyConfigurationServiceImpl.class);
			
	@Autowired
	private CompanyConfigurationRepository configurationRepository;
	
	@Override
	public CompanyConfigurationDto getCompanyConfig() {
		try {
		CompanyConfiguration configuration=configurationRepository.findByCompanyId(TomatoConstants.TOMATO_COMPANY_ID).blockFirst();
	    CompanyConfigurationDto configurationDto=new CompanyConfigurationDto();
	    if(Utils.isNotEmpty(configuration))
		 BeanUtils.copyProperties(configuration, configurationDto);
	    return configurationDto;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	@Override
	public void saveCompanyConfiguration(CompanyConfigurationDto companyConfigurationDto) {
		try {
			CompanyConfiguration configuration=new CompanyConfiguration();
			BeanUtils.copyProperties(companyConfigurationDto, configuration);
			configuration.setId(TomatoConstants.TOMATO_COMPANY_ID);
			configuration.setCompanyId(TomatoConstants.TOMATO_COMPANY_ID);
			configurationRepository.save(configuration).subscribe();
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

}