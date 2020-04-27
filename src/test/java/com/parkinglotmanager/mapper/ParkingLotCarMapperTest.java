package com.parkinglotmanager.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.parkinglotmanager.CarType;
import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;
import com.parkinglotmanager.mapper.validator.BusinessValidator;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.InternalCarTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotCarMapperTest {

	@InjectMocks
	private ParkingLotCarMapper testInstance;

	@Spy
	BusinessValidator validator;

	private String parkingLotCode = "PL1";
	private String parkingSpaceCode = "G1";
	private String carID = "NC123QE";
	private Float priceToPay = 2.5F;
	private CarType carType = CarType.GASOLINE;

	private CarBom carBom;

	@Before
	public void setUp() {
		carBom = CarBom	.builder()
						.parkingSpaceCode(parkingSpaceCode)
						.priceToPay(priceToPay)
						.carEntity(CarEntity.builder()
											.plate(carID)
											.arrivalTime(Date.from(Instant.now()))
											.build())
						.build();
	}

	@Test
	public void testMapToCarBomCreate() {
		CarBom carBom = testInstance.mapToCarBomCreate(parkingLotCode, carID, carType);

		assertEquals(parkingLotCode, carBom.getParkingLotCode());
		assertEquals(carID, carBom	.getCarEntity()
									.getPlate());
		assertEquals(InternalCarTypeEnum.GASOLINE.toString(), carBom.getCarEntity()
																	.getType());
		assertNotNull(carBom.getCarEntity()
							.getArrivalTime());
	}

	@Test
	public void testMapToParkingLotEntryRS() {

		ParkingLotEntryRS response = testInstance.mapToParkingLotEntryRS(carBom);

		assertEquals(parkingSpaceCode, response.getAssignedSlot());
		assertEquals(carID, response.getCarID());
		assertNotNull(response.getEntryTime());
	}

	@Test
	public void testMapToCarBomDelete() {
		CarBom carBom = testInstance.mapToCarBomDelete(parkingLotCode, carID);

		assertEquals(parkingLotCode, carBom.getParkingLotCode());
		assertEquals(carID, carBom	.getCarEntity()
									.getPlate());
	}

	@Test
	public void testMapToParkingLotExitRS() {

		ParkingLotExitRS response = testInstance.mapToParkingLotExitRS(carBom);

		assertEquals(parkingSpaceCode, response.getAssignedSlot());
		assertEquals(carID, response.getCarID());
		assertEquals(priceToPay, response.getSumToPay());
	}
}
