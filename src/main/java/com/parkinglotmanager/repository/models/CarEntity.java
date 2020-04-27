package com.parkinglotmanager.repository.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "CAR", uniqueConstraints = { @UniqueConstraint(columnNames = { "plate" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity extends AbstractBaseEntity {

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	protected Integer id;

	@Column
	private String plate;

	@Column
	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;

	@OneToOne
	@JoinColumn(name = "parkinspace_id")
	private ParkingSpaceEntity parkingSpace;
}
