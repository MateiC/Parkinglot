package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRICING_POLICY")
public class PricingPolicyEntity extends AbstractBaseEntity {

	@Column
	private String name;

	@Column
	private String type;
}
