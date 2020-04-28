package com.parkinglotmanager.error;

import lombok.Getter;

/**
 * Custom exception to throw in the service
 * 
 * @author Mat
 */
@Getter
public class ParkingLotManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorsEnum errorEnum;

	public ParkingLotManagerException(ErrorsEnum errorEnum) {
		super(errorEnum.toString());
		this.errorEnum = errorEnum;
	}
}
