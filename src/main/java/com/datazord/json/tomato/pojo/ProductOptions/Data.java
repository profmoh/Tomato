package com.datazord.json.tomato.pojo.ProductOptions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

	@JsonProperty("option_id")
	private Integer option_id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("sort_order")
	private Integer sort_order;
	
	@JsonProperty("option_values")
	private List<OptionValue> option_values=new ArrayList<>();

	public Integer getOption_id() {
		return option_id;
	}

	public void setOption_id(Integer option_id) {
		this.option_id = option_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort_order() {
		return sort_order;
	}

	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
	}

	public List<OptionValue> getOption_values() {
		return option_values;
	}

	public void setOption_values(List<OptionValue> option_values) {
		this.option_values = option_values;
	}
}
