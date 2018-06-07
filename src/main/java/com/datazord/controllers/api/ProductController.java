package com.datazord.controllers.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.reply.API_Reply;
import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.service.ProductService;

@RestController
@RequestMapping({"/Products"})
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private TomatoServiceImpl apiService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/addProduct")
	private Product addProduct(){
		logger.info(">>> Start inserting product into Tomato ");
		
		Product product=new Product();
		
		productService.getParamterPathFromObject();
		//API_Reply api_Reply =apiService.addProduct(product);
		
		return product;
		
	}

}
