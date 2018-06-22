package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Child {

	@JsonProperty("size_id")
	private String size_id;
	
	@JsonProperty("quantity")
	private String quantity;
	
	@JsonProperty("subtract")
	private String subtract;
	
	@JsonProperty("price_prefix")
	private String price_prefix;
	
	@JsonProperty("price")
	private String price;
	
	@JsonProperty("sort_order")
	private String sort_order;
}
