package com.datazord.service;

import com.datazord.dto.CompanyConfigurationDto;

public interface CompanyConfigurationService {
	
	//static final String COMPANY_CONFIG_SEQ="company_config_seq";
	
	CompanyConfigurationDto getCompanyConfig();
	
	void saveCompanyConfiguration(CompanyConfigurationDto companyConfigurationDto);
}