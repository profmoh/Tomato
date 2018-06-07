package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOptionValue {

	@JsonProperty("price")
	private String price;
	
	@JsonProperty("price_prefix")
	private String price_prefix;
	
	@JsonProperty("subtract")
	private String subtract;
	
	@JsonProperty("points")
	private String points;
	
	@JsonProperty("points_prefix")
	private String points_prefix;
	
	@JsonProperty("weight")
	private String weight;
	
	@JsonProperty("weight_prefix")
	private String weight_prefix;
	
	@JsonProperty("option_value_id")
	private String option_value_id;
	
	@JsonProperty("quantity")
	private String quantity;
	
}
