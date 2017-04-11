package com.retailio.backendchallenge.authentication;

import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.retailio.backendchallenge.dao.authentication.BasicUserDetailsDao;
import com.retailio.backendchallenge.utils.LogFactory;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CustomUserDetailsSecurityConfig extends WebSecurityConfigurerAdapter {
   
	Logger logger = LogFactory.getLogger(CustomUserDetailsSecurityConfig.class);
	
	@Bean
    public BasicUserDetailsDao mongoUserDetails() {
        return new BasicUserDetailsDao();
    }
	
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth.userDetailsService(userDetailsService);
        logger.info("Authentication Service Initialized");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
		.antMatchers("/job/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
		.and().formLogin().and().logout().permitAll();
	}
}
