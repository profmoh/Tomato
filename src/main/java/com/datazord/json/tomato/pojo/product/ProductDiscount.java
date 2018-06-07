package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDiscount {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("customer_group_id")
	private String customer_group_id;
	
	@JsonProperty("price")
	private String price;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("quantity")
	private String quantity;
	
	@JsonProperty("date_start")
	private String date_start;
	
	@JsonProperty("date_end")
	private String date_end;
	
}
