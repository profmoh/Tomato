package com.datazord.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.service.ProductService;

@RestController
@RequestMapping({"/api/Products"})
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getParamterPath")
	private String getProductParamterPath(){
		logger.info(">>> Start inserting Product Paramter Path into DB ");
		
		productService.getParamterPathFromObject();
		
		return "Success";
		
	}

}
