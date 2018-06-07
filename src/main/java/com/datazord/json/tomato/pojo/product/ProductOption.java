package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOption {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("option_id")
    private String option_id;
	
	@JsonProperty("required")
    private String required;
	
	@JsonProperty("product_option_value")
	private List<ProductOptionValue>product_option_value=new ArrayList<>();
}
