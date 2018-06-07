package com.datazord.service.Impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.json.tomato.pojo.product.ProductAttribute;
import com.datazord.json.tomato.pojo.product.ProductAttributeDescription;
import com.datazord.json.tomato.pojo.product.ProductDescription;
import com.datazord.json.tomato.pojo.product.ProductDiscount;
import com.datazord.json.tomato.pojo.product.ProductOption;
import com.datazord.json.tomato.pojo.product.ProductOptionValue;
import com.datazord.json.tomato.pojo.product.ProductSeoUrl;
import com.datazord.json.tomato.pojo.product.ProductSpecial;
import com.datazord.service.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.connection.Stream;

@Component
public class ProductServiceImpl implements ProductService{

	@Override
	public void getParamterPathFromObject(){
		
	    Field[] fields = Product.class.getDeclaredFields();
	    List<String>productParmaeterPath=new ArrayList<>();

	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {	
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_seo_url")|| annotationValue.equals("product_description") || annotationValue.equals("product_special")
	            	|| annotationValue.equals("product_discount") || annotationValue.equals("product_attribute")  || annotationValue.equals("product_option"))
	            	continue;
	            String parameterPath="Product."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
		
	    fields = ProductSeoUrl.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_seo_url."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    
	    fields = ProductSpecial.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_special."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductDescription.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_description."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    fields = ProductDiscount.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_discount."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductAttribute.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_attribute_description"))
	                continue;
	            String parameterPath="Product.product_attribute."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductAttributeDescription.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_attribute.product_attribute_description."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductOption.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_option_value"))
	            	continue;
	            String parameterPath="Product.product_option."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductOptionValue.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_option.product_option_value."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    productParmaeterPath.forEach(param->System.out.println(param));
	}
	
}
