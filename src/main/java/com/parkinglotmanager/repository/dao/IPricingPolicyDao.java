package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.PricingPolicyEntity;

/**
 * The DAO for the Parking policy entity
 * 
 * @author Mat
 *
 */
@Repository
public interface IPricingPolicyDao extends JpaRepository<PricingPolicyEntity, Integer> {

	PricingPolicyEntity findByTypeAndBasePriceAndFixedAmmount(String type, Float basePrice, Float fixedAmmount);
}
