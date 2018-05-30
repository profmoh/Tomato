package com.datazord.json.tomato.pojo.ProductOptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "success", "error", "data" })
public class ProductOptions {

	@JsonProperty("success")
	private Integer success;

	@JsonProperty("error")
	private List<Object> error = null;
	
	@JsonProperty("data")
	private Data data;

	
	
	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public List<Object> getError() {
		return error;
	}

	public void setError(List<Object> error) {
		this.error = error;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	
}
