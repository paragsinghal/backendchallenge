package com.retailio.backendchallenge.entity.githubjob;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;

import com.retailio.backendchallenge.interfaces.Job;
import com.retailio.backendchallenge.utils.ConfigUtils;
import com.retailio.backendchallenge.utils.LogFactory;
import com.retailio.backendchallenge.utils.WebUtils;
import com.sun.jersey.api.client.ClientResponse;

public class GithubJob implements Job{

	ConfigUtils configUtils = new ConfigUtils();
	
	Logger logger = LogFactory.getLogger(GithubJob.class);
	
	String repositoryName;
	String repositoryOwner;
	List<Filter> filters;
	
	public GithubJob(String repositoryName, String repositoryOwner,
			List<Filter> filters) {
		super();
		this.repositoryName = repositoryName;
		this.repositoryOwner = repositoryOwner;
		this.filters = filters;
	}

	public void execute(Date date) {
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		
		// configUtils.getStringValue("github.api.baseurl");
		 String githubUrl ="https://api.github.com/search/issues";
		
		String url = githubUrl + "?q=created:" + nowAsISO + "..*+repo:" + repositoryOwner + "/" + repositoryName + "+type:issue";
		
		for(Filter filter:filters){
			switch (filter.getFilterType()) {
			case USER:
				url += "+author:" + filter.getValue();
				break;
			case TITLE:
				url+= "+" + filter.getValue() + "+in:title";
			default:
				break;
			}
		}
		
		ClientResponse clientResponse = WebUtils.INSTANCE.doCall(url, HttpMethod.GET, null); 
		
		String res = clientResponse.getEntity(String.class);

		if (clientResponse.getStatus() != 200) {
			logger.error("call to github failed : " + res);
		}else{
			System.out.print(res);
			logger.info(res);
		}
		
	}

	public void validate() throws IllegalArgumentException {
		List<String> verificationErrors = collectVerificationErrors();
		if (verificationErrors!=null && !verificationErrors.isEmpty()) {
			throw new IllegalArgumentException(verificationErrors.toString());
		}
	}

	protected List<String> collectVerificationErrors() {

		List<String> verificationErrors = new ArrayList<String>();

		if(repositoryName==null || repositoryName.isEmpty()){
			verificationErrors.add("repository Name is mandatory");
		}
		if(repositoryOwner==null || repositoryOwner.isEmpty()){
			verificationErrors.add("repository Owner is mandatory");
		}
		return verificationErrors;
	}
	
}
