package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.InternalPricingPolicyEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PricingPolicyDaoTest {

	@Autowired
	private IPricingPolicyDao testInstance;

	PricingPolicyEntity pricingPolicyEntity;

	@Before
	public void setup() {
		pricingPolicyEntity = PricingPolicyEntity	.builder()
													.basePrice(1.5F)
													.fixedAmmount(2F)
													.type(InternalPricingPolicyEnum.PER_HOUR.toString())
													.build();
		testInstance.saveAndFlush(pricingPolicyEntity);
	}

	@Test
	public void testSaveAndFlushAndFindOneById() {
		Optional<PricingPolicyEntity> persistedEntityById = testInstance.findById(pricingPolicyEntity.getId());
		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testCountByTypeWithParkingLotCode() {
		assertNotNull(testInstance.findByTypeAndBasePriceAndFixedAmmount(InternalPricingPolicyEnum.PER_HOUR.toString(),
				1.5F, 2F));
	}
}
