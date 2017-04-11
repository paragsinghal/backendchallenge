package com.retailio.backendchallenge.entity.githubjob;

import com.retailio.backendchallenge.entity.AbstractEntity;

public class JobPojo extends AbstractEntity {

	JobType jobType;
	String jobMetaData;
	JobFrequency jobFrequency;

	public JobPojo() {
		super();
	}

	public JobPojo(JobType jobType, String jobMetaData,
			JobFrequency jobFrequency) {
		super();
		this.jobType = jobType;
		this.jobMetaData = jobMetaData;
		this.jobFrequency = jobFrequency;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public String getJobMetaData() {
		return jobMetaData;
	}

	public void setJobMetaData(String jobMetaData) {
		this.jobMetaData = jobMetaData;
	}

	public JobFrequency getJobFrequency() {
		return jobFrequency;
	}

	public void setJobFrequency(JobFrequency jobFrequency) {
		this.jobFrequency = jobFrequency;
	}

	@Override
	public String toString() {
		return "JobPojo [jobType=" + jobType + ", jobMetaData=" + jobMetaData
				+ ", jobFrequency=" + jobFrequency + "]";
	}
	
	public static class Constants {
		public static final String JOB_TYPE = "jobType";
		public static final String JOB_FREQUENCY = "jobFrequency";
	}

}
