package com.parkinglotmanager.repository.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING_LOT")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotEntity extends AbstractBaseEntity {

	@Column
	private String code;

	@Column
	private String description;

	@Column
	private Integer gasolineSlots;

	@Column
	private Integer smallKwSlots;

	@Column
	private Integer bigKwSlots;

	@Column
	private Integer pricingStrategy;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ParkingSpaceEntity> parkingSpaces;
}
