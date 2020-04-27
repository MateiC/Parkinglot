package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Data;

@MappedSuperclass
@Data
public class AbstractBaseEntity {

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	protected Integer id;

	@Version
	@Column(name = "version", nullable = false)
	protected Integer version;
}
