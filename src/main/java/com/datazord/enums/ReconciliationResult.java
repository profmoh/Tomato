package com.datazord.enums;

public enum ReconciliationResult {
	identical(1), added(2), removed(3), updated(4);

	private int value;

	private ReconciliationResult(int value) {
		this.value = value;
	}

	public static ReconciliationResult valueOf(int value) {
		switch (value) {
		case 1:
			return identical;
		case 2:
			return added;
		case 3:
			return removed;
		case 4:
			return updated;
		default:
			return null;
		}
	}

	public int getValue() {
		return value;
	}
}
