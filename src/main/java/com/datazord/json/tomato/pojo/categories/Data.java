
package com.datazord.json.tomato.pojo.categories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

	@JsonIgnore
	private Map<String, List<Category>> categoriesMap = new HashMap<String, List<Category>>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Data() {
	}

	public Data(Map<String, List<Category>> categoriesMap) {
		super();

		this.categoriesMap = categoriesMap;
	}

	@JsonAnyGetter
	public Map<String, List<Category>> getCategoriesMap() {
		return categoriesMap;
	}

	@JsonAnySetter
	public void setCategoriesMap(String name, List<Category> value) {
		this.categoriesMap.put(name, value);
	}
}
