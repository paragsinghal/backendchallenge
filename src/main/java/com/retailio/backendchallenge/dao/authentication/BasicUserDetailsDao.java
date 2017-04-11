package com.retailio.backendchallenge.dao.authentication;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.retailio.backendchallenge.dao.AbstractDao;
import com.retailio.backendchallenge.entity.authentication.BasicUserDetails;
import com.retailio.backendchallenge.entity.authentication.CustomUserDetails;
import com.retailio.backendchallenge.utils.LogFactory;

public class BasicUserDetailsDao extends AbstractDao implements UserDetailsService{

	Logger logger = LogFactory.getLogger(BasicUserDetailsDao.class);
	
	public CustomUserDetails loadUserByUsername(String username) {
		logger.info("Getting user for permission: " + username);
		try {
			Query query = new Query();
			if (username != null) {
				query.addCriteria(Criteria.where("username").is(username));
			}

			List<BasicUserDetails> result = runQuery(query,
					BasicUserDetails.class);
			
			if (result == null || result.isEmpty()) {
				throw new IllegalAccessException("Not Allowed");
			} else {
				logger.info("User for permission: " + result.get(0).toString());
				return new CustomUserDetails(result.get(0));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//throw e;
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails();
		return customUserDetails;
	}
}
