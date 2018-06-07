package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAttributeDescription {

	@JsonProperty("text")
	private String text;
	
	@JsonProperty("language_id")
	private String language_id;
}
