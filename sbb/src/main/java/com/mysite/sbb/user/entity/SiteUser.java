package com.mysite.sbb.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;

	public SiteUser() {

	}

	public SiteUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public static SiteUser dtoToUserEntity(String username, String password, String email) {
		return new SiteUser(username, password, email);
	}
}
