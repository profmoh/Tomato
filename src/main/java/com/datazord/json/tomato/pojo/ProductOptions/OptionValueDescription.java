package com.datazord.json.tomato.pojo.ProductOptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionValueDescription {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("language_id")
	private String language_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}
	
	
}
