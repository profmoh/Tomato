package com.datazord.form;

import java.io.Serializable;

public class BaseForm implements Serializable {

	private static final long serialVersionUID = 8173865200329466349L;

	private Long errorCode;

	private String errorMessage;

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
