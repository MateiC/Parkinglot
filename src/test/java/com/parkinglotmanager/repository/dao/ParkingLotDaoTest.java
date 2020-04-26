package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.InternalCarTypeEnum;
import com.parkinglotmanager.service.InternalPricingPolicyEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingLotDaoTest {

	@Autowired
	private IParkingLotDao testInstance;

	@Autowired
	private IParkingSpaceDao parkingSpaceDao;

	@Autowired
	private IPricingPolicyDao pricingPolicyDao;

	ParkingSpaceEntity parkingSpace;

	PricingPolicyEntity pricingPolicyEntity;

	ParkingLotEntity parkingLotEntity;

	@Before()
	public void setup() {
		parkingSpace = ParkingSpaceEntity	.builder()
											.code("PS1")
											.isOccupied(true)
											.type(InternalCarTypeEnum.GASOLINE.toString())
											.build();
		pricingPolicyEntity = PricingPolicyEntity	.builder()
													.basePrice(1.5F)
													.fixedAmmount(2F)
													.type(InternalPricingPolicyEnum.PER_HOUR.toString())
													.build();
		parkingLotEntity = ParkingLotEntity	.builder()
											.code("PKG")
											.description("Parking lot")
											.gasolineSlots(1)
											.smallKwSlots(2)
											.bigKwSlots(3)
											.parkingSpaces(Arrays.asList(parkingSpace))
											.pricingPolicy(pricingPolicyEntity)
											.build();

		testInstance.saveAndFlush(parkingLotEntity);
	}

	@Test
	public void testSaveAndFlushAndFindOneById() {
		Optional<ParkingLotEntity> persistedEntityById = testInstance.findById(parkingLotEntity.getId());
		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testSaveWithParkingSpaceAndPricingPolicy() {
		assertNotNull(parkingLotEntity);
		assertEquals(1, parkingLotEntity.getParkingSpaces()
										.size());
		assertNotNull(parkingLotEntity.getPricingPolicy());
	}

	@Test
	public void testFindAndCountByCode() {
		assertNotNull(testInstance.findByCode(parkingLotEntity.getCode()));
		assertEquals(1, testInstance.countByCode(parkingLotEntity.getCode()));
	}

	@Test
	public void testDeleteByCodeWithCascade() {
		testInstance.deleteByCode(parkingLotEntity.getCode());
		assertEquals(0, testInstance.count());
		assertEquals(0, parkingSpaceDao.count());
		assertEquals(0, pricingPolicyDao.count());
	}

}
