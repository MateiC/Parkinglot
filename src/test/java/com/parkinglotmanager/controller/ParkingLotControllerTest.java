package com.parkinglotmanager.controller;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotControllerTest extends AbstractSpringBootTest {

	@Test
	public void A_testRetrieveAllParkingLots() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglots")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test

	public void B_testCreateParkingLot() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"description\": \"Nice Centre\",\r\n"
							+ "  \"numberOfStandardParkingSlots\": 1,\r\n" 
							+ "  \"numberOf20KWParkingSlots\": 0,\r\n"
							+ "  \"numberOf50KWParkingSlots\": 1,\r\n" 
							+ "  \"pricingPolicy\": {\r\n"
							+ "    \"policy\": \"perHour\",\r\n" 
							+ "    \"basePrice\": 0,\r\n"
							+ "    \"fixedAmmount\": 0\r\n" 
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
					.body("pricingPolicy.basePrice", Matchers.equalTo(0.0F))
					.body("pricingPolicy.fixedAmmount", Matchers.equalTo(0.0F));
	}

	public void B_testCreateParkingWithSameCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"description\": \"Nice Centre\",\r\n"
							+ "  \"numberOfStandardParkingSlots\": 0,\r\n" 
							+ "  \"numberOf20KWParkingSlots\": 0,\r\n"
							+ "  \"numberOf50KWParkingSlots\": 0,\r\n" 
							+ "  \"pricingPolicy\": {\r\n"
							+ "    \"policy\": \"perHour\",\r\n" 
							+ "    \"basePrice\": 0,\r\n"
							+ "    \"fixedAmmount\": 0\r\n" 
							+ "  }\r\n" 
							+ "}")
					// @formatter:on
					.post("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(500);
	}

	@Test
	public void C_testRetrieveParkingLot() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200)
					.body("version", Matchers.equalTo(0))
					.body("numberOfStandardParkingSlots", Matchers.equalTo(1))
					.body("numberOf20KWParkingSlots", Matchers.equalTo(0))
					.body("numberOf50KWParkingSlots", Matchers.equalTo(1))
					.body("pricingPolicy.policy", Matchers.equalTo("perHour"))
					.body("pricingPolicy.basePrice", Matchers.equalTo(0.0F))
					.body("pricingPolicy.fixedAmmount", Matchers.equalTo(0.0F));
	}

	@Test
	public void D_testRetrieveParkingLotByCodeWithInvalid() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglot/TE1")
					.then()
					.assertThat()
					.statusCode(404);
	}

	@Test
	public void E_testUpdateParkingLot() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" 
							+ "  \"pricingPolicy\": {\r\n" 
							+ "    \"policy\": \"perHour\",\r\n"
							+ "    \"basePrice\": 2,\r\n" 
							+ "    \"fixedAmmount\": 1\r\n" 
							+ "  },\r\n"
							+ "  \"version\": 0\r\n" 
							+ "}")
					// @formatter:on
					.put("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200)
					.body("version", Matchers.equalTo(1))
					.body("pricingPolicy.policy", Matchers.equalTo("perHour"))
					.body("pricingPolicy.basePrice", Matchers.equalTo(2.0F))
					.body("pricingPolicy.fixedAmmount", Matchers.equalTo(1.0F));
	}

	@Test
	public void F_testDeleteParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES?version=1")
					.then()
					.assertThat()
					.statusCode(200);
	}
}
