package com.datazord.enums;

public enum Language {
	en(1), arabic(2), turkey(3);

	private int value;

	private Language(int value) {
		this.value = value;
	}

	public static Language valueOf(int value) {
		switch (value) {
		case 1:
			return en;
		case 2:
			return arabic;
		case 3:
			return turkey;
		default:
			return null;
		}
	}

	public int getValue() {
		return value;
	}
}
