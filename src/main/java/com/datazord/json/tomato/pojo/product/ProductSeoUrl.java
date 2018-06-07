package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSeoUrl {

	@JsonProperty("keyword")
	private String keyword;
	
	@JsonProperty("language_id")
	private String language_id;
	
	@JsonProperty("store_id")
	private String store_id;
}
