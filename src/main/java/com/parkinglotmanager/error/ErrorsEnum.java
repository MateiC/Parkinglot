package com.parkinglotmanager.error;

import org.springframework.http.HttpStatus;

/**
 * Stores all the possible values for errors
 * 
 * @author Mat
 */
public enum ErrorsEnum {
	PARKING_LOT_NOT_FOUND("The parking lot with the given code was not found", HttpStatus.NOT_FOUND),
	PARKING_LOT_ALREADY_EXISTS("The parking lot with the given code already exists", HttpStatus.FORBIDDEN),
	CAR_NOT_FOUND("The car with the given plate was not found", HttpStatus.NOT_FOUND),
	CAR_ALREADY_EXISTS("The car with the given plate already exists or is in another parking lot",
			HttpStatus.FORBIDDEN),
	NOT_FREE_SLOT_FOR_TYPE("There are not free slots for this car type", HttpStatus.FORBIDDEN),
	UNKNOWN_CAR_TYPE("Unknown type of car", HttpStatus.BAD_REQUEST),
	UNKNOWN_PRICING_POLICY_TYPE("Unknown pricing policy", HttpStatus.BAD_REQUEST),
	OPTIMISTIC_LOCK("The version provided is not equal to the persisted version", HttpStatus.FORBIDDEN),
	INVALID_PARKING_LOT_CODE("Invalid Parking lot code", HttpStatus.BAD_REQUEST),
	INVALID_CAR_ID("Invalid car plate", HttpStatus.BAD_REQUEST);

	private final String value;

	private HttpStatus httpStatus;

	private ErrorsEnum(String value, HttpStatus httpStatus) {
		this.value = value;
		this.httpStatus = httpStatus;
	}

	public static ErrorsEnum fromString(String text) {
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

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
