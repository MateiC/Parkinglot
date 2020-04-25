package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PARKING_SPACE")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ParkingSpaceEntity extends AbstractBaseEntity {

	@Column
	private String code;

	@Column
	private Integer type;

	@Column
	private Boolean isOccupied;

	@OneToOne(mappedBy = "parkingspace")
	private CarEntity car;
}
