package com.retailio.backendchallenge.controllers;

import io.swagger.annotations.Api;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.retailio.backendchallenge.entity.githubjob.JobFrequency;
import com.retailio.backendchallenge.entity.githubjob.JobPojo;
import com.retailio.backendchallenge.entity.githubjob.JobType;
import com.retailio.backendchallenge.managers.JobManager;
import com.retailio.backendchallenge.utils.LogFactory;

@RestController
@RequestMapping("/job")
@Api(value = "Job Apis", description = "JOb APIs")
public class JobController {

	@Autowired
	JobManager jobManager;
	
	Logger logger = LogFactory.getLogger(JobController.class);
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JobPojo addJob(@RequestParam(value="jobType", required = true) JobType jobType,
			@RequestParam(value="jobFrequency", required = false) JobFrequency jobFrequency,
			@RequestBody String jobMetadata) throws Exception {
		try{
			logger.info("Request Received for adding A job " + jobType +".Job Metadata: " + jobMetadata);
			return jobManager.addJob(jobType, jobFrequency, jobMetadata);
		}catch(Exception e){
			throw e;
		}
	}

		@RequestMapping(method = RequestMethod.DELETE)
	public void deleteJob(@RequestParam(value="id", required = true) String id) throws Exception {
		try{
			 jobManager.deleteJob(id);
		}catch(Exception e){
			throw e;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<JobPojo> getPermits(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "jobType", required = false) JobType jobType,
			@RequestParam(value = "jobFrequency", required = false) JobFrequency jobFrequency,
			@RequestParam(value = "fromTime", required = false) Long fromTime,
			@RequestParam(value = "toTime", required = false) Long toTime,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit)
			throws Exception {
		List<JobPojo> result = jobManager.listJobs(id, jobType, jobFrequency, fromTime, toTime, start, limit);
		logger.info("result for getting jobs  : " + result.toString());
		return result;
	}
}
