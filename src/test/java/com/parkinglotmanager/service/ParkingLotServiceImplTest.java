package com.parkinglotmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.hamcrest.CustomMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.repository.dao.ICarDao;
import com.parkinglotmanager.repository.dao.IParkingLotDao;
import com.parkinglotmanager.repository.dao.IParkingSpaceDao;
import com.parkinglotmanager.repository.dao.IPricingPolicyDao;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotServiceImplTest {

	@InjectMocks
	private final ParkingLotServiceImpl service = new ParkingLotServiceImpl();

	@Mock
	ICarDao carDao;

	@Mock
	IParkingLotDao parkingLotDao;

	@Mock
	IParkingSpaceDao parkingSpaceDao;

	@Mock
	IPricingPolicyDao pricingPolicyDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final String PARKINGLOTCODE = "P1";

	private ParkingLotEntity parkingLotEntity;

	private PricingPolicyEntity pricingPolicyEntity;

	private ParkingLotEntity persistedParkingLotEntity;

	private PricingPolicyEntity persistedPricingPolicyEntity;

	@Before
	public void setup() {

		pricingPolicyEntity = PricingPolicyEntity	.builder()
													.basePrice(2F)
													.fixedAmmount(3F)
													.type(InternalPricingPolicyEnum.PER_HOUR.toString())
													.build();

		parkingLotEntity = ParkingLotEntity	.builder()
											.code(PARKINGLOTCODE)
											.description("Parking lot")
											.gasolineSlots(1)
											.smallKwSlots(2)
											.bigKwSlots(3)
											.pricingPolicy(pricingPolicyEntity)
											.version(0)
											.build();

		persistedPricingPolicyEntity = PricingPolicyEntity	.builder()
															.basePrice(1F)
															.fixedAmmount(2F)
															.type(InternalPricingPolicyEnum.PER_HOUR_WITH_FIXED_AMMOUNT.toString())
															.build();

		persistedParkingLotEntity = ParkingLotEntity.builder()
													.code(PARKINGLOTCODE)
													.description("Parking lot")
													.gasolineSlots(1)
													.smallKwSlots(2)
													.bigKwSlots(3)
													.pricingPolicy(persistedPricingPolicyEntity)
													.version(0)
													.build();

	}

	public void expectedError(ErrorsEnum expectdError) {
		thrown.expect(new CustomMatcher<ParkingLotManagerException>("") {
			@Override
			public boolean matches(final Object item) {
				return ((ParkingLotManagerException) item).getErrorEnum() == expectdError;
			}
		});
	}

	// --- CREATE ---
	@Test
	public void testCreateWithParkingAleadyExists() {

		Mockito	.when(parkingLotDao.countByCode(parkingLotEntity.getCode()))
				.thenReturn(1);

		expectedError(ErrorsEnum.PARKING_LOT_ALREADY_EXISTS);

		service.create(parkingLotEntity);
	}

	@Test
	public void testCreate() {

		Mockito	.when(parkingLotDao.countByCode(parkingLotEntity.getCode()))
				.thenReturn(0);

		Mockito	.when(parkingLotDao.save(parkingLotEntity))
				.thenReturn(parkingLotEntity);

		ParkingLotEntity response = service.create(parkingLotEntity);
		assertNotNull(response);
		assertEquals(
				parkingLotEntity.getBigKwSlots() + parkingLotEntity.getGasolineSlots()
						+ parkingLotEntity.getSmallKwSlots(),
				response.getParkingSpaces()
						.size());
	}

	// --- RETRIEVE ---

	@Test
	public void testRetrieveWithParkingNotExists() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(null);

		expectedError(ErrorsEnum.PARKING_LOT_NOT_FOUND);

		service.retrieve(parkingLotEntity);
	}

	@Test
	public void testRetrieve() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(parkingLotEntity);

		assertNotNull(service.retrieve(parkingLotEntity));
	}

	@Test
	public void testRetrieveAll() {

		Mockito	.when(parkingLotDao.findAll())
				.thenReturn(Arrays.asList(parkingLotEntity));

		assertEquals(1, service	.retrieveAll()
								.size());
	}

	// ---UPDATE---

	@Test
	public void testUpdateWithParkingNotExists() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(null);

		expectedError(ErrorsEnum.PARKING_LOT_NOT_FOUND);

		service.update(parkingLotEntity);
	}

	@Test
	public void testUpdateWithDifferentVersion() {

		persistedParkingLotEntity.setVersion(1);

		expectedError(ErrorsEnum.OPTIMISTIC_LOCK);

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(persistedParkingLotEntity);

		service.update(parkingLotEntity);
	}

	@Test
	public void testUpdate() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(persistedParkingLotEntity);

		Mockito	.when(parkingLotDao.save(persistedParkingLotEntity))
				.thenReturn(persistedParkingLotEntity);

		ParkingLotEntity response = service.update(parkingLotEntity);

		assertEquals(pricingPolicyEntity, response.getPricingPolicy());
	}

	// --- DELETE ---

	@Test
	public void testDeleteWithParkingNotExists() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(null);

		expectedError(ErrorsEnum.PARKING_LOT_NOT_FOUND);

		service.delete(parkingLotEntity);
	}

	@Test
	public void testDeleteWithDifferentVersion() {

		persistedParkingLotEntity.setVersion(1);

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(persistedParkingLotEntity);

		expectedError(ErrorsEnum.OPTIMISTIC_LOCK);

		service.delete(parkingLotEntity);
	}

	@Test
	public void testDelete() {

		Mockito	.when(parkingLotDao.findByCode(parkingLotEntity.getCode()))
				.thenReturn(persistedParkingLotEntity);

		service.delete(parkingLotEntity);
	}
}
