package com.retailio.backendchallenge.entity;

import org.springframework.data.annotation.Id;

/**
 * @author parag
 *
 *         Abstract entity for adding and getting data from Mongo.
 */
public abstract class AbstractEntity {
	@Id
	private String id;
	private Long creationTime;
	private String createdBy;
	private String lastUpdatedBy;
	private Long lastUpdatedTime;
	private boolean enabled = true;

	public AbstractEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreationTime() {
		return creationTime;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static class Constants {
		public static final String ID = "id";
		public static final String CREATION_TIME = "creationTime";
		public static final String LAST_UPDATED_BY = "lastUpdatedBy";
		public static final String CREATED_BY = "createdBy";
		public static final String LAST_UPDATED_TIME = "lastUpdatedTime";
		public static final String ENABLED = "enabled";
	}
}