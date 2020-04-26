package com.parkinglotmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

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
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotCarServiceImplTest {

	@InjectMocks
	private final ParkingLotCarServiceImpl service = new ParkingLotCarServiceImpl();

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
	private static final String PARKINGSPACE1 = "PS1";
	private static final String PLATE1 = "PL1";
	private static final String GAS = "G";

	private ParkingLotEntity parkingLotEntity;

	private PricingPolicyEntity pricingPolicyEntity;

	private CarEntity carEntityWithNoSpace;

	private CarEntity carEntityWithAssignedSpace;

	private CarBom carBom;

	private ParkingSpaceEntity parkingSpaceEntity;

	private ParkingSpaceEntity assignedParkingSpaceEntity;

	@Before
	public void setup() {

		pricingPolicyEntity = PricingPolicyEntity	.builder()
													.basePrice(2F)
													.fixedAmmount(3F)
													.type(InternalPricingPolicyEnum.PER_HOUR.toString())
													.build();

		parkingLotEntity = ParkingLotEntity	.builder()
											.code(PARKINGLOTCODE)
											.pricingPolicy(pricingPolicyEntity)
											.build();

		carEntityWithNoSpace = CarEntity.builder()
										.plate(PLATE1)
										.type(GAS)
										.build();

		parkingSpaceEntity = ParkingSpaceEntity	.builder()
												.code(PARKINGSPACE1)
												.isOccupied(false)
												.type(InternalCarTypeEnum.GASOLINE.toString())
												.build();

		assignedParkingSpaceEntity = ParkingSpaceEntity	.builder()
														.code(PARKINGSPACE1)
														.isOccupied(true)
														.type(InternalCarTypeEnum.GASOLINE.toString())
														.build();

		carEntityWithAssignedSpace = CarEntity	.builder()
												.plate(PLATE1)
												.type(GAS)
												.arrivalTime(Date.from(Instant	.now()
																				.minusSeconds(90)))
												.parkingSpace(assignedParkingSpaceEntity)
												.build();
		assignedParkingSpaceEntity.setCar(carEntityWithAssignedSpace);

		carBom = CarBom	.builder()
						.carEntity(carEntityWithNoSpace)
						.parkingLotCode(PARKINGLOTCODE)
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

//	--- CREATE ---

	@Test
	public void testCreateWithParkingLotNotExist() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(null);

		expectedError(ErrorsEnum.PARKING_LOT_NOT_FOUND);

		service.create(carBom);
	}

	@Test
	public void testCreateWithEntityAlreadyExists() {

		// parking lot exists
		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(new ParkingLotEntity());

		// car already exists
		Mockito	.when(carDao.countByPlate(carEntityWithNoSpace.getPlate()))
				.thenReturn(1);

		expectedError(ErrorsEnum.CAR_ALREADY_EXISTS);

		service.create(carBom);
	}

	@Test
	public void testCreateWithAssignFreeSpaceDoesNotExists() {

		// parking lot exists
		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(new ParkingLotEntity());

		// no free space
		Mockito	.when(parkingSpaceDao.countFreeSlotsByTypeWithParkingLotCode(carEntityWithNoSpace.getType(),
						carBom.getParkingLotCode()))
				.thenReturn(0);

		expectedError(ErrorsEnum.NOT_FREE_SLOT_FOR_TYPE);

		service.create(carBom);
	}

	@Test
	public void testCreateWithAssignFreeSpaceExists() {

		// parking lot exists
		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(new ParkingLotEntity());

		// there are free spaces
		Mockito	.when(parkingSpaceDao.countFreeSlotsByTypeWithParkingLotCode(carEntityWithNoSpace.getType(),
						carBom.getParkingLotCode()))
				.thenReturn(1);

		// there are free spaces
		Mockito	.when(parkingSpaceDao.findFreeSlotsByTypeWithParkingLotCode(carEntityWithNoSpace.getType(),
						carBom.getParkingLotCode()))
				.thenReturn(Arrays.asList(parkingSpaceEntity));

		Mockito	.when(carDao.saveAndFlush(carBom.getCarEntity()))
				.thenReturn(carEntityWithNoSpace);

		CarBom persistedCarBom = service.create(carBom);

		assertEquals(carBom, persistedCarBom);
		assertEquals(carEntityWithNoSpace, persistedCarBom.getCarEntity());
		assertTrue(parkingSpaceEntity.getIsOccupied());
		assertEquals(parkingSpaceEntity, carEntityWithNoSpace.getParkingSpace());
	}

	// --- DELETE ---
	@Test
	public void testDeleteWithParkingLotNotExist() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(null);

		expectedError(ErrorsEnum.PARKING_LOT_NOT_FOUND);

		service.delete(carBom);
	}

	@Test
	public void testDeleteWithEntityDoesNotExist() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(new ParkingLotEntity());

		Mockito	.when(carDao.findByPlate(carBom	.getCarEntity()
												.getPlate()))
				.thenReturn(null);

		expectedError(ErrorsEnum.CAR_NOT_FOUND);

		service.delete(carBom);
	}

	@Test
	public void testDeleteWithEntityExistsAndTimeLowerThanOneHour() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(parkingLotEntity);

		Mockito	.when(carDao.findByPlate(carBom	.getCarEntity()
												.getPlate()))
				.thenReturn(carEntityWithAssignedSpace);

		service.delete(carBom);

		assertNull(carBom	.getCarEntity()
							.getParkingSpace());
		assertEquals(Float.valueOf(0), carBom.getPriceToPay());
	}

	@Test
	public void testDeleteWithEntityExistsAndTimeBiggerThanOneHour() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(parkingLotEntity);

		carEntityWithAssignedSpace.setArrivalTime(Date.from(Instant	.now()
																	.minusSeconds(3601)));

		Mockito	.when(carDao.findByPlate(carBom	.getCarEntity()
												.getPlate()))
				.thenReturn(carEntityWithAssignedSpace);

		service.delete(carBom);

		assertNull(carBom	.getCarEntity()
							.getParkingSpace());
		assertEquals(pricingPolicyEntity.getBasePrice(), carBom.getPriceToPay());
	}

	@Test
	public void testDeleteWithEntityExistsAndTimeBiggerThanOneHourAndBaseAmmount() {

		Mockito	.when(parkingLotDao.findByCode(carBom.getParkingLotCode()))
				.thenReturn(parkingLotEntity);

		carEntityWithAssignedSpace.setArrivalTime(Date.from(Instant	.now()
																	.minusSeconds(3601)));

		pricingPolicyEntity.setType(InternalPricingPolicyEnum.PER_HOUR_WITH_FIXED_AMMOUNT.toString());

		Mockito	.when(carDao.findByPlate(carBom	.getCarEntity()
												.getPlate()))
				.thenReturn(carEntityWithAssignedSpace);

		service.delete(carBom);

		assertNull(carBom	.getCarEntity()
							.getParkingSpace());
		float due = pricingPolicyEntity.getBasePrice() + pricingPolicyEntity.getFixedAmmount();
		assertEquals(due, carBom.getPriceToPay()
								.floatValue(),
				0);
	}

}
