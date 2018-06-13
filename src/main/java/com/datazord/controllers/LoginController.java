package com.datazord.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.constants.TomatoConstants;
import com.datazord.form.LoginForm;

@RestController
@RequestMapping({ "/loginController" })
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
	public ResponseEntity<?> login(){
	
		LoginForm loginForm=new LoginForm();
		if(loginForm.getUsername().equals("Khaled"))
			loginForm.setErrorCode(TomatoConstants.ERROR_CODE_SUCCESS);
		
		return new ResponseEntity<LoginForm>(loginForm,HttpStatus.OK);
	}
}
