package com.datazord.api.reply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class API_Reply implements Serializable {

	private static final long serialVersionUID = -6842865603083567229L;

	@JsonProperty("success")
	private Integer success;

	@JsonProperty("error")
	private Object error ;

	@JsonProperty("data")
	private com.datazord.api.reply.Data data ;
}
