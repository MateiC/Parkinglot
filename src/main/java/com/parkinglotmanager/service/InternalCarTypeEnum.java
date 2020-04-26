package com.parkinglotmanager.service;

public enum InternalCarTypeEnum {
	GASOLINE("G"), SMALLKW("S"), BIGKW("B");

	private final String value;

	private InternalCarTypeEnum(final String value) {
		this.value = value;
	}

	public static InternalCarTypeEnum fromString(final String text) {
		for (InternalCarTypeEnum element : InternalCarTypeEnum.values()) {
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