package com.parkinglotmanager.mapper.validator;

import org.hamcrest.CustomMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;

@RunWith(MockitoJUnitRunner.class)
public class BusinesValidatorTest {

	@InjectMocks
	private BusinessValidator testInstance;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	public void expectedError(ErrorsEnum expectdError) {
		thrown.expect(new CustomMatcher<ParkingLotManagerException>("") {
			@Override
			public boolean matches(final Object item) {
				return ((ParkingLotManagerException) item).getErrorEnum() == expectdError;
			}
		});
	}

	@Test
	public void testCarPlateWithInvalidString() {

		expectedError(ErrorsEnum.INVALID_CAR_ID);

		testInstance.testCarPlate("AA");
		testInstance.testCarPlate("AAA1234AAA");
	}

	@Test
	public void testCarPlateWithValid() {

		testInstance.testCarPlate("AA123AA");
	}

	@Test
	public void testParkingLotCodeWithValid() {

		testInstance.testParkingLotCode("AAA");
		testInstance.testParkingLotCode("AA1");
		testInstance.testParkingLotCode("A11");
	}

	@Test
	public void testParkingLotCodeWithInvalidString() {

		expectedError(ErrorsEnum.INVALID_PARKING_LOT_CODE);

		testInstance.testParkingLotCode("AAAAAAAA");
		testInstance.testParkingLotCode("AA123");
	}

}
