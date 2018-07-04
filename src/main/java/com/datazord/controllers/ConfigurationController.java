package com.datazord.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.constants.TomatoConstants;
import com.datazord.dto.CompanyConfigurationDto;
import com.datazord.form.ConfigurationForm;
import com.datazord.service.CompanyConfigurationService;
import com.datazord.utils.Utils;

@CrossOrigin()
@RestController
@RequestMapping({ "/configurationController" })
public class ConfigurationController {

	@Autowired
	private CompanyConfigurationService configurationService;
	
	@GetMapping("/CompanySetting")
	public ResponseEntity<?> getCompanySetting(){
		
		ConfigurationForm form=new ConfigurationForm();
		CompanyConfigurationDto configurationDto=configurationService.getCompanyConfig();
		if(Utils.isNotEmpty(configurationDto))
		 BeanUtils.copyProperties(configurationDto, form);
		form.setCompanyName("Tomato");
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
	}
	
	@PostMapping("/saveCompanySetting")
	public ResponseEntity<?> saveCompanySetting(@RequestBody ConfigurationForm form){
		
		CompanyConfigurationDto configurationDto=new CompanyConfigurationDto();
		BeanUtils.copyProperties(form, configurationDto);
		configurationService.saveCompanyConfiguration(configurationDto);
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
	}
}
