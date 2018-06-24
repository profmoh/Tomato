package com.datazord.json.tomato.pojo.ProductOptions;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionValue {
	
	@JsonProperty("option_value_id")
	private Integer option_value_id;
	
	@JsonProperty("option_value_description")
	private Map<String, OptionValueDescription> OptionValueDescription = new HashMap<String, OptionValueDescription>();
		
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("thumb")
	private String thumb;
	 
	@JsonProperty("sort_order")
	private Integer sort_order;
	
	
	public Integer getOption_value_id() {
		return option_value_id;
	}

	public void setOption_value_id(Integer option_value_id) {
		this.option_value_id = option_value_id;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Integer getSort_order() {
		return sort_order;
	}

	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
	}

	public Map<String, OptionValueDescription> getOptionValueDescription() {
		return OptionValueDescription;
	}

	public void setOptionValueDescription(Map<String, OptionValueDescription> optionValueDescription) {
		OptionValueDescription = optionValueDescription;
	}



	
}
