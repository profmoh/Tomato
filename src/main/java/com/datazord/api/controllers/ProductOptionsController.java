package com.datazord.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.service.ProductOptionsService;
import com.datazord.utils.Utils;

@RestController
@RequestMapping({ "/api/productOptions" })
public class ProductOptionsController {

	private static final Logger logger = LoggerFactory.getLogger(ProductOptionsController.class);

	@Autowired
	private TomatoServiceImpl apiService;

	@Autowired
	private ProductOptionsService productOptionsService;

	@GetMapping("/getProductOptionsParamters/{id}")
	private String getProductOptionsParamters(@PathVariable("id") Integer Id) {
		logger.info("Find ProductOptions By Id = " + Id);

		try {
			ProductOptions productOptions = apiService.findProductOptionsValue(Id);

			logger.info("response from findProductOptionsValue >> Success = " + productOptions.getSuccess() + " >> error = " + productOptions.getError());

			if (productOptions.getSuccess() == 1)
				productOptionsService.saveDestinationProductOptions(productOptions);

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@GetMapping("/Source/getProductOptionColors")
	private String getSourceProductOptionColors() {
		logger.info("Find Source Colors");

		try {
			List<String> colorsList = apiService.findSourceProductOptionColors();

			if(! Utils.isEmptyCollection(colorsList))
				productOptionsService.saveSourceProductOptionColors(colorsList);

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@GetMapping("/Source/getProductOptionSizes")
	private String getSourceProductOptionSizes() {
		logger.info("Find Source Sizes");

		try {
			List<String> sizesList = apiService.findSourceProductOptionSizes();

			if(! Utils.isEmptyCollection(sizesList))
				productOptionsService.saveSourceProductOptionSizes(sizesList);

			return "Success";

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
}
