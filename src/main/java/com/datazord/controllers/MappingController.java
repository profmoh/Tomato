package com.datazord.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.form.MappingForm;



@RestController
@RequestMapping({ "/mappingController" })
public class MappingController {

	private static final Logger logger = LoggerFactory.getLogger(MappingController.class);
	
	@GetMapping("/")
	public ResponseEntity<?> getMappingForm(){
	
		MappingForm form=new MappingForm();
		
		return new ResponseEntity<MappingForm>(form,HttpStatus.OK);
	}
}
