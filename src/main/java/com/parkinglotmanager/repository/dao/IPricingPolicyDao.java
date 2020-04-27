package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.PricingPolicyEntity;

@Repository
public interface IPricingPolicyDao extends JpaRepository<PricingPolicyEntity, Integer> {

	PricingPolicyEntity findByTypeAndBasePriceAndFixedAmmount(String type, Float basePrice, Float fixedAmmount);
}
