package com.datazord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.constants.TomatoConstants;
import com.datazord.enums.MappingType;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.form.MappingForm;
import com.datazord.service.MappingService;

@CrossOrigin()
@RestController
@RequestMapping({ "/mappingController" })
public class MappingController {

//	private static final Logger logger = LoggerFactory.getLogger(MappingController.class);

	@Autowired
	private MappingService mappingService;

	@GetMapping("/getMappingForm/{mappingType}")
	public ResponseEntity<?> getMappingForm(@PathVariable("mappingType") int mappingType) {
				
		MappingForm form = new MappingForm();

		form = mappingService.getMappingLists(MappingType.valueOf(mappingType));
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);

		return new ResponseEntity<MappingForm>(form, HttpStatus.OK);
	}

	@PostMapping("/saveMappingResult")
	public ResponseEntity<?> saveMappingResult(@RequestBody MappingForm form) {

		mappingService.saveMappingResult(form);
		
		return new ResponseEntity<MappingForm>(form, HttpStatus.OK);
	}
	
	@GetMapping("/reloadObjects/{mappingType}")
	public ResponseEntity<?> reloadObjects(@PathVariable("mappingType") int mappingType) {
		MappingForm form = new MappingForm();
		try{		
		mappingService.reloadObjects(MappingType.valueOf(mappingType));
		
		form.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		
		return new ResponseEntity<MappingForm>(form, HttpStatus.OK);
		}catch (MissedMappingException e) {
			form.setErrorCode(TomatoConstants.ERROR_CODE_FAILED);
			form.setErrorMessage("Mapp Objects Is Missed");
			return new ResponseEntity<MappingForm>(form, HttpStatus.OK);
		}
	}
}
