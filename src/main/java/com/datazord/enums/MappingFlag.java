package com.datazord.enums;

public enum MappingFlag {
	category(1), color(2), size(3), productPath(4);

	private int value;

	private MappingFlag(int value) {
		this.value = value;
	}

	public static MappingFlag valueOf(int value) {
		switch (value) {
		case 1:
			return category;
		case 2:
			return color;
		case 3:
			return size;
		case 4:
			return productPath;
		default:
			return null;
		}
	}

	public int getValue() {
		return value;
	}
}
