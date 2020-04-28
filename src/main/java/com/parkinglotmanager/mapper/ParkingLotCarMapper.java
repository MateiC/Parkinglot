package com.parkinglotmanager.mapper;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkinglotmanager.CarType;
import com.parkinglotmanager.ParkingLotEntry;
import com.parkinglotmanager.ParkingLotExit;
import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.mapper.validator.BusinessValidator;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.InternalCarTypeEnum;

/**
 * 
 * Maps requests for the service layer or service responses to outputs
 * 
 * @author Mat
 *
 */
@Component
public class ParkingLotCarMapper {

	@Autowired
	BusinessValidator validator;

	// Create
	public CarBom mapToCarBomCreate(String parkingLotCode, String carID, CarType carType) {
		validator.testParkingLotCode(parkingLotCode);
		validator.testCarPlate(carID);
		return CarBom	.builder()
						.parkingLotCode(parkingLotCode)
						.carEntity(mapToCarEntityWithArrivalTime(carID, carType))
						.build();
	}

	private CarEntity mapToCarEntityWithArrivalTime(String carID, CarType carType) {
		return CarEntity.builder()
						.plate(carID)
						.type(CarTypeEnumToInternalCarTypeEnum(carType))
						.arrivalTime(Date.from(Instant.now()))
						.build();
	}

	public ParkingLotEntry mapToParkingLotEntry(CarBom carBom) {
		CarEntity carEntity = carBom.getCarEntity();
		return new ParkingLotEntry().carID(carEntity.getPlate())
									.assignedSlot(carBom.getParkingSpaceCode())
									.entryTime(carEntity.getArrivalTime()
														.toString());
	}

	// Delete
	public CarBom mapToCarBomDelete(String parkingLotCode, String carID) {
		validator.testParkingLotCode(parkingLotCode);
		validator.testCarPlate(carID);
		return CarBom	.builder()
						.parkingLotCode(parkingLotCode)
						.carEntity(CarEntity.builder()
											.plate(carID)
											.build())
						.build();
	}

	public ParkingLotExit mapToParkingLotExit(CarBom carBom) {
		return new ParkingLotExit()	.carID(carBom	.getCarEntity()
													.getPlate())
									.assignedSlot(carBom.getParkingSpaceCode())
									.sumToPay(carBom.getPriceToPay());
	}

	private String CarTypeEnumToInternalCarTypeEnum(CarType carType) {
		switch (carType) {
		case GASOLINE:
			return InternalCarTypeEnum.GASOLINE.toString();
		case _20KW:
			return InternalCarTypeEnum.SMALLKW.toString();
		case _50KW:
			return InternalCarTypeEnum.BIGKW.toString();
		default:
			throw new ParkingLotManagerException(ErrorsEnum.UNKNOWN_CAR_TYPE);
		}
	}

}
