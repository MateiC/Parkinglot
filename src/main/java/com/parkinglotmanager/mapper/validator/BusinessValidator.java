package com.parkinglotmanager.mapper.validator;

import org.springframework.stereotype.Component;

import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;

@Component
public class BusinessValidator {

	private static final String ALPHA_NUM = "^[a-zA-Z0-9]";

	private static final String REGEX_PARKING_LOT_CODE = ALPHA_NUM + "{3}$";

	private static final String CAR_PLATE = "^[A-Z]{2}\\d{3}[A-Z]{2}$";

	public void testParkingLotCode(String parkingLotCode) {
		if (parkingLotCode == null || !parkingLotCode.matches(REGEX_PARKING_LOT_CODE)) {
			throw new ParkingLotManagerException(ErrorsEnum.INVALID_PARKING_LOT_CODE);
		}
	}

	public void testCarPlate(String carPlate) {
		if (carPlate == null || !carPlate.matches(CAR_PLATE)) {
			throw new ParkingLotManagerException(ErrorsEnum.INVALID_CAR_ID);
		}
	}
}