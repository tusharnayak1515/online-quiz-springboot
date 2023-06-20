package com.onlinequizapp.dto;

public class UserResponse {
	private int id;
	private String name;
	private String email;
	private String role;
	
	public UserResponse() {
		super();
	}
	
	public UserResponse(int id, String name, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "]";
	}
}
