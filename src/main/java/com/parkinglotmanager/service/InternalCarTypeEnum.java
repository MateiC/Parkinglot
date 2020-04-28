package com.parkinglotmanager.service;

/**
 * Internal enum to map various types of engines/car types.
 * Created in order to not depend on the API definitions
 * 
 * @author Mat
 *
 */
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