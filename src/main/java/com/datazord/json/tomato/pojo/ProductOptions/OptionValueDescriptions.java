package com.datazord.json.tomato.pojo.ProductOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionValueDescriptions {

	
	@JsonIgnore
	private Map<String, List<OptionValueDescription>> optionValuDesMap = new HashMap<String, List<OptionValueDescription>>();
	
	
	public OptionValueDescriptions(){
		
	}
	
	public OptionValueDescriptions(Map<String, List<OptionValueDescription>> optionValuDesMap) {
		super();
		this.optionValuDesMap = optionValuDesMap;
	}

	
	@JsonAnyGetter
	public Map<String, List<OptionValueDescription>> getOptionValuDesMap() {
		return optionValuDesMap;
	}
	
	@JsonAnyGetter
	public void setOptionValuDesMap(Map<String, List<OptionValueDescription>> optionValuDesMap) {
		this.optionValuDesMap = optionValuDesMap;
	}
}
