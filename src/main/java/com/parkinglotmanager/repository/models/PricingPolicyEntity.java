package com.parkinglotmanager.repository.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRICING_POLICY", uniqueConstraints = { @UniqueConstraint(columnNames = { "type" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricingPolicyEntity extends AbstractBaseEntity {

	@Column
	private String type;

	@Column
	private Float basePrice;

	@Column
	private Float fixedAmmount;

	@Builder.Default
	@OneToMany(mappedBy = "pricingPolicy", orphanRemoval = true)
	private List<ParkingLotEntity> parkingLotEntities = new ArrayList<ParkingLotEntity>();
}
