package com.onlinequizapp.dto;

import com.onlinequizapp.entities.User;

public class JwtResponse {
    private boolean success;
    private String token;
    private UserResponse user;

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public JwtResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public JwtResponse() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	@Override
	public String toString() {
		return "JwtResponse [success=" + success + ", token=" + token + ", user=" + user + "]";
	}
}

