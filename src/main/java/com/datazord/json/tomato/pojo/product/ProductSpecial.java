package com.datazord.json.tomato.pojo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSpecial {

	@JsonProperty("customer_group_id")
	private String customer_group_id;
	
	@JsonProperty("price")
	private String price;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("date_start")
	private String date_start;
	
	@JsonProperty("date_end")
	private String date_end;

	public String getCustomer_group_id() {
		return customer_group_id;
	}

	public void setCustomer_group_id(String customer_group_id) {
		this.customer_group_id = customer_group_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDate_start() {
		return date_start;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
}
