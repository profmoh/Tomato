package com.datazord.enums;

public enum MappingFlag {
	category(1), size(2), color(3), productPath(4);

	private int value;

	private MappingFlag(int value) {
		this.value = value;
	}

	public static MappingFlag valueOf(int value) {
		switch (value) {
		case 1:
			return category;
		case 2:
			return size;
		case 3:
			return color;
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
