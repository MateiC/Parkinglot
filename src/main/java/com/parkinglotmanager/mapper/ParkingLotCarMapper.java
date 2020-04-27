package com.parkinglotmanager.mapper;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkinglotmanager.CarType;
import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;
import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.mapper.validator.BusinessValidator;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.InternalCarTypeEnum;

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

	public ParkingLotEntryRS mapToParkingLotEntryRS(CarBom carBom) {
		CarEntity carEntity = carBom.getCarEntity();
		return new ParkingLotEntryRS()	.carID(carEntity.getPlate())
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

	public ParkingLotExitRS mapToParkingLotExitRS(CarBom carBom) {
		return new ParkingLotExitRS()	.carID(carBom	.getCarEntity()
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
