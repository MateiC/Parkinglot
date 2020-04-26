package com.parkinglotmanager.mapper;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.parkinglotmanager.ParkingLotEntryRQ;
import com.parkinglotmanager.ParkingLotEntryRQ.CarTypeEnum;
import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;
import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.InternalCarTypeEnum;

@Component
public class ParkingLotCarMapper {

	public CarBom mapToCarBom(String parkingLotCode, ParkingLotEntryRQ parkingLotEntry) {
		return CarBom	.builder()
						.parkingLotCode(parkingLotCode)
						.carEntity(mapToCarEntity(parkingLotEntry))
						.build();
	}

	public CarBom mapToCarBom(String parkingLotCode, String carID) {
		return CarBom	.builder()
						.parkingLotCode(parkingLotCode)
						.carEntity(CarEntity.builder()
											.plate(carID)
											.build())
						.build();
	}

	private CarEntity mapToCarEntity(ParkingLotEntryRQ parkingLotEntry) {
		return CarEntity.builder()
						.plate(parkingLotEntry.getCarID())
						.type(CarTypeEnumToInternalCarTypeEnum(parkingLotEntry.getCarType()))
						.arrivalTime(Date.from(Instant.now()))
						.build();
	}

	private String CarTypeEnumToInternalCarTypeEnum(CarTypeEnum carType) {
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

	public ParkingLotEntryRS mapToParkingLotEntryRS(CarBom carBom) {
		CarEntity carEntity = carBom.getCarEntity();
		return new ParkingLotEntryRS()	.carID(carEntity.getPlate())
										.assignedSlot(carBom.getParkingSpaceCode())
										.entryTime(carEntity.getArrivalTime()
															.toString());
	}

	public ParkingLotExitRS mapToParkingLotExitRS(CarBom carBom) {
		return new ParkingLotExitRS()	.carID(carBom	.getCarEntity()
														.getPlate())
										.assignedSlot(carBom.getParkingSpaceCode())
										.sumToPay(carBom.getPriceToPay());
	}

}
