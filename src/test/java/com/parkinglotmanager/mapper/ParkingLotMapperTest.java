package com.parkinglotmanager.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.parkinglotmanager.ParkingLot;
import com.parkinglotmanager.ParkingLotCreate;
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLotsRS;
import com.parkinglotmanager.PricingPolicy.PolicyEnum;
import com.parkinglotmanager.mapper.validator.BusinessValidator;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.InternalCarTypeEnum;
import com.parkinglotmanager.service.InternalPricingPolicyEnum;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotMapperTest {

	private String parkingLotCode = "PL1";
	private String parkingSpaceCode = "PS1";
	private Integer numberOfStandardParkingSlots = 2;
	private Integer numberOf20KWParkingSlots = 2;
	private Integer numberOf50KWParkingSlots = 2;

	private Float basePrice = 1.5F;
	private Float fixedAmmount = 2F;

	private ParkingLotEntity parkingLotEntity;

	private ParkingSpaceEntity parkingSpace;

	private PricingPolicyEntity pricingPolicyEntity;

	@InjectMocks
	private ParkingLotMapper testInstance;

	@Spy
	BusinessValidator validator;

	@Before
	public void setup() {

		parkingSpace = ParkingSpaceEntity	.builder()
											.code(parkingSpaceCode)
											.isOccupied(true)
											.type(InternalCarTypeEnum.GASOLINE.toString())
											.build();

		pricingPolicyEntity = PricingPolicyEntity	.builder()
													.basePrice(basePrice)
													.fixedAmmount(fixedAmmount)
													.type(InternalPricingPolicyEnum.PER_HOUR.toString())
													.build();

		parkingLotEntity = ParkingLotEntity	.builder()
											.code(parkingLotCode)
											.description("Parking lot")
											.gasolineSlots(numberOfStandardParkingSlots)
											.smallKwSlots(numberOf20KWParkingSlots)
											.bigKwSlots(numberOf50KWParkingSlots)
											.parkingSpaces(Arrays.asList(parkingSpace))
											.pricingPolicy(pricingPolicyEntity)
											.version(2)
											.build();
	}

	@Test
	public void testMapToRetrieveAll() {
		ParkingLotsRS response = testInstance.mapToRetrieveAll(Arrays.asList(parkingLotEntity));

		assertEquals(parkingLotEntity.getCode(), response	.get(0)
															.getCode());
		assertEquals(parkingLotEntity.getDescription(), response.get(0)
																.getDescription());
	}

	@Test
	public void testMapToRetrieve() {
		ParkingLot response = testInstance.mapToParkingLotRetrieve(parkingLotEntity);

		assertEquals(parkingLotEntity.getDescription(), response.getDescription());
		assertEquals(parkingLotEntity.getSmallKwSlots(), response.getNumberOf20KWParkingSlots());
		assertEquals(parkingLotEntity.getBigKwSlots(), response.getNumberOf50KWParkingSlots());
		assertEquals(parkingLotEntity.getGasolineSlots(), response.getNumberOfStandardParkingSlots());
		assertEquals(parkingLotEntity.getVersion(), response.getVersion());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getBasePrice(),
				response.getPricingPolicy()
						.getBasePrice());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getFixedAmmount(),
				response.getPricingPolicy()
						.getFixedAmmount());
		assertEquals(PolicyEnum.PERHOUR, response	.getPricingPolicy()
													.getPolicy());
	}

	@Test
	public void testMapToCreate() {

		ParkingLotCreate response = testInstance.mapToParkingLotCreate(parkingLotEntity);

		assertEquals(parkingLotEntity.getDescription(), response.getDescription());
		assertEquals(parkingLotEntity.getSmallKwSlots(), response.getNumberOf20KWParkingSlots());
		assertEquals(parkingLotEntity.getBigKwSlots(), response.getNumberOf50KWParkingSlots());
		assertEquals(parkingLotEntity.getGasolineSlots(), response.getNumberOfStandardParkingSlots());
		assertEquals(parkingLotEntity.getVersion(), response.getVersion());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getBasePrice(),
				response.getPricingPolicy()
						.getBasePrice());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getFixedAmmount(),
				response.getPricingPolicy()
						.getFixedAmmount());
		assertEquals(PolicyEnum.PERHOUR, response	.getPricingPolicy()
													.getPolicy());
	}

	@Test
	public void testMapToUpdate() {

		ParkingLotUpdate response = testInstance.mapToParkingLotUpdate(parkingLotEntity);

		assertEquals(parkingLotEntity.getVersion(), response.getVersion());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getBasePrice(),
				response.getPricingPolicy()
						.getBasePrice());
		assertEquals(parkingLotEntity	.getPricingPolicy()
										.getFixedAmmount(),
				response.getPricingPolicy()
						.getFixedAmmount());
		assertEquals(PolicyEnum.PERHOUR, response	.getPricingPolicy()
													.getPolicy());
	}

	@Test
	public void testmapToParkingLotEntityToDelete() {

		ParkingLotEntity response = testInstance.mapToParkingLotEntityToDelete(parkingLotCode, 2);
		assertEquals(parkingLotEntity.getVersion(), response.getVersion());
		assertEquals(parkingLotEntity.getCode(), response.getCode());
	}
}
