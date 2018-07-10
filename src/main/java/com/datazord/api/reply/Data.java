package com.datazord.api.reply;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data implements Serializable {

	private static final long serialVersionUID = -7497939293442583065L;

	@JsonProperty("id")
	private Long id;
}
