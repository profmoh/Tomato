package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class productCustomOption {
	
	@JsonProperty("color_id")
	private String color_id;
	
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("sort_order")
	private String sort_order = "0";
	
	@JsonProperty("child")
	private List<Child>child=new ArrayList<>();
	
	
}
