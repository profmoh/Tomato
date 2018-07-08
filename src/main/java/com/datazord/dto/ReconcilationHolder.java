package com.datazord.dto;

import com.datazord.enums.ReconcilationResult;

public class ReconcilationHolder {

	public String id;
	public String name;
	public String mainId;
	public String language;
	public ReconcilationResult reconcilationResult;

	public ReconcilationHolder(String id, String name) {
		super();

		this.id = id;
		this.name = name;
	}

	public ReconcilationHolder(String id, String name, String language) {
		super();

		this.id = id;
		this.name = name;
		this.language = language;
	}
}
