package com.datazord.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.service.ProductOptionsService;

@RestController
@RequestMapping({ "/api/productOptions" })
public class ProductOptionsController {

	private static final Logger logger = LoggerFactory.getLogger(ProductOptionsController.class);

	@Autowired
	private TomatoServiceImpl apiService;

	@Autowired
	private ProductOptionsService productOptionsService;

	@GetMapping("/Destination/getProductOption/{id}")
	private String saveDestinationProductOptions(@PathVariable("id") Integer Id) {
		logger.info("Find ProductOptions By Id =" + Id);

		try {
			productOptionsService.saveDestinationProductOptions(Id);

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@GetMapping("/Source/getProductOptionColors")
	private String getSourceProductOptionColors() {
		logger.info("Save Source Colors");

		try {
			apiService.saveSourceProductOptionColors();

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@GetMapping("/Source/getProductOptionSizes")
	private String getSourceProductOptionSizes() {
		logger.info("Save Source Sizes");

		try {
			apiService.saveSourceProductOptionSizes();

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
}
