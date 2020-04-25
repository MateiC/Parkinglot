package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractBaseEntityWithCode extends AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, insertable = false, updatable = false)
	protected Integer id;

	@Version
	@Column(name = "version", nullable = false)
	protected Integer version;

	@Column
	private String code;
}
