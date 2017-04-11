package com.retailio.backendchallenge.managers;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.retailio.backendchallenge.dao.job.JobDao;
import com.retailio.backendchallenge.entity.githubjob.GithubJob;
import com.retailio.backendchallenge.entity.githubjob.JobFrequency;
import com.retailio.backendchallenge.entity.githubjob.JobPojo;
import com.retailio.backendchallenge.entity.githubjob.JobType;
import com.retailio.backendchallenge.utils.LogFactory;

@Service
public class JobManager {

	Gson gson = new Gson();

	Logger logger = LogFactory.getLogger(JobManager.class);
	
	@Autowired
	JobDao jobDao;

	public JobPojo addJob(JobType jobType, JobFrequency jobFrequency, String jobMetadata) throws Exception {

		if(jobFrequency==null){
		 jobFrequency = JobFrequency.DAILY;
		}

		switch (jobType) {
		case GITHUB_POLL:
			GithubJob githubJob = gson.fromJson(jobMetadata, GithubJob.class);
			githubJob.validate();
			break;
		default:
			throw new IllegalArgumentException("JobType not supported");
		}
		try {
			JobPojo jobPojo = new JobPojo(jobType, jobMetadata, jobFrequency);
			jobDao.create(jobPojo);
			logger.info("Add Job response:" + jobPojo.toString());
			return jobPojo;
		} catch (Exception e) {
			throw e;
		}
	}

	public JobPojo addGithubJob(GithubJob githubJob) throws Exception {
		logger.info("Request received for adding githubJob" + githubJob.toString());
		try {
			JobPojo jobPojo = new JobPojo(JobType.GITHUB_POLL,
					gson.toJson(githubJob), JobFrequency.DAILY);
			jobDao.create(jobPojo);
			logger.info("Add Job response:" + jobPojo.toString());
			return jobPojo;
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteJob(String id) throws Exception {
		logger.info("Delete Job request:" + id);
		try {
			jobDao.deleteById(id);
			logger.info("JobId:" + id + " deleted");
		} catch (Exception e) {
			logger.error("Error while deleting job id:" + id);
			throw e;
		}
	}

	public List<JobPojo> listJobs(String id, JobType jobType,
			JobFrequency jobFrequency, Long fromTime, Long toTime,
			Integer start, Integer limit) throws Exception {
		logger.info("list Job request");
		try {
			return jobDao.getJobs(id, jobType, jobFrequency, fromTime, toTime,
					start, limit);
		} catch (Exception e) {
			throw e;
		}
	}
}
