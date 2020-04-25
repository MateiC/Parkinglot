package com.parkinglotmanager.error;

public enum Errors {
	NOT_FOUND("Entity not found"), ALREADY_EXISTS("Entity already exists");

	private final String value;

	private Errors(final String value) {
		this.value = value;
	}

	public static Errors fromString(final String text) {
		for (Errors element : Errors.values()) {
			if (element.value.equalsIgnoreCase(text)) {
				return element;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
