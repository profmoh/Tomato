
package com.datazord.json.tomato.pojo.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "success", "error", "data" })
public class Token {

	@JsonProperty("success")
	private Integer success;

	@JsonProperty("error")
	private List<Object> error = null;

	@JsonProperty("data")
	private Data data;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Token() {
	}

	/**
	 * 
	 * @param error
	 * @param data
	 * @param success
	 */
	public Token(Integer success, List<Object> error, Data data) {
		super();

		this.success = success;
		this.error = error;
		this.data = data;
	}

	@JsonProperty("success")
	public Integer getSuccess() {
		return success;
	}

	@JsonProperty("success")
	public void setSuccess(Integer success) {
		this.success = success;
	}

	@JsonProperty("error")
	public List<Object> getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(List<Object> error) {
		this.error = error;
	}

	@JsonProperty("data")
	public Data getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(Data data) {
		this.data = data;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
