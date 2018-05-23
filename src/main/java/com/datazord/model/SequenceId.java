package com.datazord.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document(collection = "sequence")
public class SequenceId {

	@Id
	private String id;

	private long seq;
}