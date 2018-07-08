package com.datazord.dto;

import com.datazord.enums.ReconciliationResult;

public class ReconciliationHolder {

	public String id;
	public String name;
	public String mainId;
	public String language;
	public ReconciliationResult reconciliationResult;

	public ReconciliationHolder(String id, String name) {
		super();

		this.id = id;
		this.name = name;
	}

	public ReconciliationHolder(String id, String name, String language) {
		super();

		this.id = id;
		this.name = name;
		this.language = language;
	}
}
