
package com.datazord.json.tomato.pojo.categories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datazord.deserializers.CustomDataDeserializer;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = CustomDataDeserializer.class)
public class Data {

	@JsonIgnore
	private Map<Long, List<Category>> categoriesMap = new HashMap<Long, List<Category>>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Data() {
	}

	public Data(Map<Long, List<Category>> categoriesMap) {
		super();

		this.categoriesMap = categoriesMap;
	}

	@JsonAnyGetter
	public Map<Long, List<Category>> getCategoriesMap() {
		return categoriesMap;
	}

	@JsonAnySetter
	public void setCategoriesMap(Long key, List<Category> value) {
		this.categoriesMap.put(key, value);
	}
}
