package com.parkinglotmanager.error;

public class ParkingLotManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParkingLotManagerException(Errors parkingLotErrorEnum) {
		super(parkingLotErrorEnum.toString());
	}
}
