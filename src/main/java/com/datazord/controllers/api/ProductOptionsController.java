package com.datazord.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.ProductOptions.Data;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.service.ProductOptionsService;

@RestController
@RequestMapping({"/productOptions"})
public class ProductOptionsController {

	@Autowired
	private TomatoServiceImpl apiService;
	
	@Autowired
	private ProductOptionsService  productOptionsService;
	
	@GetMapping("/{id}")
	private ProductOptions findProductOptionsById(@PathVariable("id") String Id){
		ProductOptions productOptions=apiService.findProductOptionsValue(Id);
		if(productOptions.getSuccess()==1)
		 productOptionsService.saveProductOptions(productOptions);
		return productOptions;
	}
}
