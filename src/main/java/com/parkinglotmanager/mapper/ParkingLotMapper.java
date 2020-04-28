package com.parkinglotmanager.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkinglotmanager.ParkingLot;
import com.parkinglotmanager.ParkingLotCreate;
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLots;
import com.parkinglotmanager.ParkingLotsRS;
import com.parkinglotmanager.PricingPolicy;
import com.parkinglotmanager.PricingPolicy.PolicyEnum;
import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.mapper.validator.BusinessValidator;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.InternalPricingPolicyEnum;

/**
 * 
 * Maps requests for the service layer or service responses to outputs
 * 
 * @author Mat
 *
 */
@Component
public class ParkingLotMapper {

	@Autowired
	BusinessValidator validator;

	// Retrieve All
	public ParkingLotsRS mapToRetrieveAll(List<ParkingLotEntity> retrieveAll) {
		ParkingLotsRS parkingLotBasic = new ParkingLotsRS();
		parkingLotBasic.addAll(retrieveAll	.stream()
											.map(this::mapToRetrieveAllParkingLots)
											.collect(Collectors.toList()));
		return parkingLotBasic;
	}

	private ParkingLots mapToRetrieveAllParkingLots(ParkingLotEntity parkingLot) {
		return new ParkingLots().code(parkingLot.getCode())
								.description(parkingLot.getDescription());

	}

	// Create
	public ParkingLotCreate mapToParkingLotCreate(ParkingLotEntity parkingLotEntity) {
		return new ParkingLotCreate()	.description(parkingLotEntity.getDescription())
										.numberOf20KWParkingSlots(parkingLotEntity.getSmallKwSlots())
										.numberOf50KWParkingSlots(parkingLotEntity.getBigKwSlots())
										.numberOfStandardParkingSlots(parkingLotEntity.getGasolineSlots())
										.pricingPolicy(mapToPricingPolicy(parkingLotEntity.getPricingPolicy()))
										.version(parkingLotEntity.getVersion());
	}

	public ParkingLotEntity mapToParkingLotEntityToCreate(String parkingLotCode,
			ParkingLotCreate parkingLotCreateBody) {
		validator.testParkingLotCode(parkingLotCode);
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.description(parkingLotCreateBody.getDescription())
								.smallKwSlots(parkingLotCreateBody.getNumberOf20KWParkingSlots())
								.bigKwSlots(parkingLotCreateBody.getNumberOf50KWParkingSlots())
								.gasolineSlots(parkingLotCreateBody.getNumberOfStandardParkingSlots())
								.pricingPolicy(mapToPricingPolicyEntity(parkingLotCreateBody.getPricingPolicy()))
								.build();
	}

	// Retrieve
	public ParkingLot mapToParkingLotRetrieve(ParkingLotEntity parkingLotEntity) {
		return new ParkingLot()	.description(parkingLotEntity.getDescription())
								.numberOf20KWParkingSlots(parkingLotEntity.getSmallKwSlots())
								.numberOf50KWParkingSlots(parkingLotEntity.getBigKwSlots())
								.numberOfStandardParkingSlots(parkingLotEntity.getGasolineSlots())
								.pricingPolicy(mapToPricingPolicy(parkingLotEntity.getPricingPolicy()))
								.version(parkingLotEntity.getVersion());
	}

	public ParkingLotEntity mapToParkingLotEntityWithCode(String parkingLotCode) {
		validator.testParkingLotCode(parkingLotCode);
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.build();
	}

	// Update
	public ParkingLotUpdate mapToParkingLotUpdate(ParkingLotEntity parkingLotEntity) {
		return new ParkingLotUpdate()	.pricingPolicy(mapToPricingPolicy(parkingLotEntity.getPricingPolicy()))
										.version(parkingLotEntity.getVersion());
	}

	private PricingPolicy mapToPricingPolicy(PricingPolicyEntity pricingPolicy) {
		return new PricingPolicy()	.basePrice(pricingPolicy.getBasePrice())
									.fixedAmmount(pricingPolicy.getFixedAmmount())
									.policy(mapToPolicyEnum(pricingPolicy.getType()));
	}

	public ParkingLotEntity mapToParkingLotEntityToUpdate(String parkingLotCode,
			ParkingLotUpdate parkingLotUpdateBody) {
		validator.testParkingLotCode(parkingLotCode);
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.pricingPolicy(mapToPricingPolicyEntity(parkingLotUpdateBody.getPricingPolicy()))
								.version(parkingLotUpdateBody.getVersion())
								.build();
	}

	// delete

	private PricingPolicyEntity mapToPricingPolicyEntity(PricingPolicy pricingPolicy) {
		return PricingPolicyEntity	.builder()
									.basePrice(pricingPolicy.getBasePrice())
									.type(policyEnumToInternalPricingPolicyEnum(pricingPolicy.getPolicy()))
									.fixedAmmount(pricingPolicy.getFixedAmmount())
									.build();
	}

	public ParkingLotEntity mapToParkingLotEntityToDelete(String parkingLotCode, Integer version) {
		validator.testParkingLotCode(parkingLotCode);
		return ParkingLotEntity	.builder()
								.code(parkingLotCode)
								.version(version)
								.build();
	}

	private PolicyEnum mapToPolicyEnum(String type) {
		InternalPricingPolicyEnum internalPricingPolicyType = InternalPricingPolicyEnum.fromString(type);
		if (internalPricingPolicyType != null) {
			switch (internalPricingPolicyType) {
			case PER_HOUR:
				return PolicyEnum.PERHOUR;
			case PER_HOUR_WITH_FIXED_AMMOUNT:
				return PolicyEnum.PERHOURWITHBASEPRICE;
			default:
				throw new ParkingLotManagerException(ErrorsEnum.UNKNOWN_PRICING_POLICY_TYPE);
			}
		} else {
			throw new ParkingLotManagerException(ErrorsEnum.UNKNOWN_PRICING_POLICY_TYPE);
		}
	}

	private String policyEnumToInternalPricingPolicyEnum(PolicyEnum policyType) {
		switch (policyType) {
		case PERHOUR:
			return InternalPricingPolicyEnum.PER_HOUR.toString();
		case PERHOURWITHBASEPRICE:
			return InternalPricingPolicyEnum.PER_HOUR_WITH_FIXED_AMMOUNT.toString();
		default:
			throw new ParkingLotManagerException(ErrorsEnum.UNKNOWN_PRICING_POLICY_TYPE);
		}
	}
}
