package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING_SPACE", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceEntity extends AbstractBaseEntity {

	@Column
	private String code;

	@Column
	private String type;

//	@Column
//	private Boolean isOccupied;

	@OneToOne(mappedBy = "parkingspace")
	private CarEntity car;
}
