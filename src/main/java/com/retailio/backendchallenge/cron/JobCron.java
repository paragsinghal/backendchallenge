package com.retailio.backendchallenge.cron;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.retailio.backendchallenge.dao.job.JobDao;
import com.retailio.backendchallenge.entity.githubjob.GithubJob;
import com.retailio.backendchallenge.entity.githubjob.JobFrequency;
import com.retailio.backendchallenge.entity.githubjob.JobPojo;
import com.retailio.backendchallenge.utils.LogFactory;

/**
 * @author parag
 *
 *         Crons for Job execution. 1 Hourly cron and 1 daily cron
 */
@Component
public class JobCron {

	@Autowired
	JobDao jobDao;

	Gson gson = new Gson();

	Logger logger = LogFactory.getLogger(JobCron.class);

	@Scheduled(cron = "0 0 * * * *")
	public void expiryCheck() {
		Date date = new Date(1481481000000l);
		try {
			List<JobPojo> jobPojos = jobDao.getJobs(null, null,
					JobFrequency.DAILY, null, null, null, null);

			for (JobPojo jobPojo : jobPojos) {
				try {
					switch (jobPojo.getJobType()) {
					case GITHUB_POLL:
						GithubJob githubJob = gson.fromJson(
								jobPojo.getJobMetaData(), GithubJob.class);
						githubJob.execute(date);
						break;
					default:
						break;
					}
				} catch (Exception e) {
					logger.error("JOb ID: " + jobPojo.getId() + " failed"
							+ new Date());
				}
			}
		} catch (Exception e) {
			logger.error("Jobs failed" + new Date());
		}
	}

	/**
	 * 
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void executeDailyJobs() {
		Date date = new Date();
		try {
			List<JobPojo> jobPojos = jobDao.getJobs(null, null,
					JobFrequency.DAILY, null, null, null, null);
			for (JobPojo jobPojo : jobPojos) {
				try {
					switch (jobPojo.getJobType()) {
					case GITHUB_POLL:
						GithubJob githubJob = gson.fromJson(
								jobPojo.getJobMetaData(), GithubJob.class);
						githubJob.execute(date);
						break;
					default:
						break;
					}
				} catch (Exception e) {
					logger.error("Job ID: " + jobPojo.getId() + " failed"
							+ new Date());
				}
			}
		} catch (Throwable e) {
			logger.error("Jobs failed:" + new Date());
		}

	}

}
