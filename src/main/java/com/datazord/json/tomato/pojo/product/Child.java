package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Child {

	@JsonProperty("size_id")
	public String size_id;
	
	@JsonProperty("quantity")
	public String quantity;
	
	@JsonProperty("subtract")
	public String subtract = "1";
	
	@JsonProperty("price_prefix")
	public String price_prefix = "+";
	
	@JsonProperty("price")
	public String price;
	
	@JsonProperty("sort_order")
	public String sort_order = "0";
}
