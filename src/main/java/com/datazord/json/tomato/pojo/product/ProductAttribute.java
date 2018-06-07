package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAttribute {

	@JsonProperty("attribute_id")
	private String attribute_id;
	
	@JsonProperty("product_attribute_description")
	private List<ProductAttributeDescription>product_attribute_description=new ArrayList<>();
}
