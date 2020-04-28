package com.parkinglotmanager.controller;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotCarControllerTest extends AbstractSpringBootTest {

	@Test
	public void A_testCreateParkingLot() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"description\": \"Nice Centre\",\r\n"
							+ "  \"numberOfStandardParkingSlots\": 1,\r\n" 
							+ "  \"numberOf20KWParkingSlots\": 0,\r\n"
							+ "  \"numberOf50KWParkingSlots\": 1,\r\n" 
							+ "  \"pricingPolicy\": {\r\n"
							+ "    \"policy\": \"perHour\",\r\n" 
							+ "    \"basePrice\": 1,\r\n"
							+ "    \"fixedAmmount\": 2\r\n" 
							+ "  }\r\n" 
							+ "}")
					// @formatter:on
					.post("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200)
					.body("version", Matchers.equalTo(0))
					.body("numberOfStandardParkingSlots", Matchers.equalTo(1))
					.body("numberOf20KWParkingSlots", Matchers.equalTo(0))
					.body("numberOf50KWParkingSlots", Matchers.equalTo(1))
					.body("pricingPolicy.policy", Matchers.equalTo("perHour"))
					.body("pricingPolicy.basePrice", Matchers.equalTo(1.0F))
					.body("pricingPolicy.fixedAmmount", Matchers.equalTo(2.0F));
	}

	@Test
	public void B_testCreateParkingLotEntry() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ?carType=GASOLINE")
					.then()
					.assertThat()
					.statusCode(200)
					.body("carID", Matchers.equalTo("BB333EQ"))
					.body("assignedSlot", Matchers.equalTo("G0"));
	}

	@Test
	public void C_testCreateParkingLotEntryWithSamePlate() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ?carType=GASOLINE")
					.then()
					.assertThat()
					.statusCode(500);
	}

	@Test
	public void D_testCreateParkingLotEntryWithDifferentPlateButSameType() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ?carType=GASOLINE")
					.then()
					.assertThat()
					.statusCode(500);
	}

	@Test
	public void E_testCreateParkingLotEntryWithSamePlateAndDifferentType() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ?carType=_20KW")
					.then()
					.assertThat()
					.statusCode(500);
	}

	@Test
	public void F_testCreateParkingLotEntryWithDifferentPlateAndDifferentType() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB111EQ?carType=_50KW")
					.then()
					.assertThat()
					.statusCode(200)
					.body("carID", Matchers.equalTo("BB111EQ"))
					.body("assignedSlot", Matchers.equalTo("B0"));
	}

	@Test
	public void G_testDeleteParkingLotEntry() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void H_testDeleteParkingLot() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES?version=0")
					.then()
					.assertThat()
					.statusCode(200);
	}
}
