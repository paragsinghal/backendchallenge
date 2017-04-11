package com.retailio.backendchallenge.entity.authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = -8468659881721045572L;
	private String username;
	private String password;
	private List<GrantedAuthority> grantedAuthorities;
	private Boolean enabled = false;
	
	public CustomUserDetails() {
		super();
	}
	
	public CustomUserDetails(BasicUserDetails basicUserDetails) {
		this.username= basicUserDetails.getUsername();
		this.password=basicUserDetails.getPassword();
		this.grantedAuthorities=AuthorityUtils
				.createAuthorityList(basicUserDetails.getGrantedAuthorities());
		this.enabled = true;
	}	

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

}
