package com.datazord.controllers;

import java.io.IOException;
import java.io.StringReader;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.datazord.constants.TomatoConstants;
import com.datazord.dto.CompanyConfigurationDto;
import com.datazord.exceptions.MissedMappingException;
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
		try {
			CompanyConfigurationDto configurationDto = new CompanyConfigurationDto();
			BeanUtils.copyProperties(form, configurationDto);
			configurationService.saveCompanyConfiguration(configurationDto);
			form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
			return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
		} catch (MissedMappingException me) {
			form.setErrorCode(TomatoConstants.ERROR_CODE_FAILED);
			form.setErrorMessage(me.getErrMsg());
			return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
		}
	}
	
	@PostMapping("/updateXmlPath")
	public ResponseEntity<?> updateXmlPath(@RequestBody ConfigurationForm form){
		CompanyConfigurationDto configurationDto=new CompanyConfigurationDto();
		BeanUtils.copyProperties(form, configurationDto);
		configurationService.saveCompanyConfiguration(configurationDto);
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
	}
	
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody ConfigurationForm form){
		try{
		CompanyConfigurationDto configurationDto=new CompanyConfigurationDto();
		BeanUtils.copyProperties(form, configurationDto);
		configurationService.addProduct(configurationDto);
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
		}catch(MissedMappingException me){
			form.setErrorCode(TomatoConstants.ERROR_CODE_FAILED);
			form.setErrorMessage(me.getErrMsg());
			return new ResponseEntity<ConfigurationForm>(form, HttpStatus.OK);
		}
	}
}
