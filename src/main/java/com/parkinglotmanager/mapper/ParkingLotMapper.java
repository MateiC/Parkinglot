package com.parkinglotmanager.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.parkinglotmanager.ParkingLot;
import com.parkinglotmanager.ParkingLotCreate;
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLotsBasic;
import com.parkinglotmanager.ParkingLotsBasicElement;
import com.parkinglotmanager.PricingPolicy;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;

@Component
public class ParkingLotMapper {

	public ParkingLotsBasic mapToRetrieveAll(List<ParkingLotEntity> retrieveAll) {
		ParkingLotsBasic parkingLotBasic = new ParkingLotsBasic();
		parkingLotBasic.addAll(retrieveAll	.stream()
											.map(this::mapToRetrieveAllParkingLotsInner)
											.collect(Collectors.toList()));
		return parkingLotBasic;
	}

	public ParkingLotsBasicElement mapToRetrieveAllParkingLotsInner(ParkingLotEntity parkingLot) {
		return new ParkingLotsBasicElement().code(parkingLot.getCode())
											.description(parkingLot.getDescription());

	}

	public ParkingLot mapToParkingLot(ParkingLotEntity parkingLotEntity) {
		return new ParkingLot()	.description(parkingLotEntity.getDescription())
								.numberOf20KWParkingSlots(parkingLotEntity.getSmallKwSlots())
								.numberOf50KWParkingSlots(parkingLotEntity.getBigKwSlots())
								.numberOfStandardParkingSlots(parkingLotEntity.getGasolineSlots())
		/* .pricingPolicy(parkingLotEntity.getPricingStrategy()) */;
	}

	public ParkingLotEntity mapToParkingLotEntityWithCode(String parkingLotCode) {
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.build();
	}

	public ParkingLotEntity mapToParkingLotEntityToCreate(String parkingLotCode,
			ParkingLotCreate parkingLotCreateBody) {
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.description(parkingLotCreateBody.getDescription())
								.smallKwSlots(parkingLotCreateBody.getNumberOf20KWParkingSlots())
								.bigKwSlots(parkingLotCreateBody.getNumberOf50KWParkingSlots())
								.gasolineSlots(parkingLotCreateBody.getNumberOfStandardParkingSlots())
								.pricingPolicy(mapToPricingPolicyEntity(parkingLotCreateBody.getPricingPolicy()))
								.build();
	}

	public ParkingLotEntity mapToParkingLotEntityToUpdate(String parkingLotCode,
			ParkingLotUpdate parkingLotUpdateBody) {
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.pricingPolicy(mapToPricingPolicyEntity(parkingLotUpdateBody.getPricingPolicy()))
								.build();
	}

	private PricingPolicyEntity mapToPricingPolicyEntity(PricingPolicy pricingPolicy) {
		return PricingPolicyEntity	.builder()
									.basePrice(pricingPolicy.getBasePrice())
									.type(pricingPolicy	.getPolicy()
														.toString())
									.build();
	}
}
