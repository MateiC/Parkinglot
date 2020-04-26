package com.parkinglotmanager.error;

public enum ErrorsEnum {
	PARKING_LOT_NOT_FOUND("The parking lot with the given code was not found"),
	PARKING_LOT_ALREADY_EXISTS("The parking lot with the given code already exists"),
	CAR_NOT_FOUND("The car with the given plate was not found"),
	CAR_ALREADY_EXISTS("The car with the given plate already exists"), // This one is funny but necessary
	NOT_FREE_SLOT_FOR_TYPE("There are not free slots for this car type"), 
	UNKNOWN_CAR_TYPE("Unknown type of car"),
	UNKNOWN_PRICING_POLICY_TYPE("Unknown pricing policy");

	private final String value;

	private ErrorsEnum(final String value) {
		this.value = value;
	}

	public static ErrorsEnum fromString(final String text) {
		for (ErrorsEnum element : ErrorsEnum.values()) {
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
