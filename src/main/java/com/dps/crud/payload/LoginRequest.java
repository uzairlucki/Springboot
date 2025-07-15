package com.dps.crud.payload;


import lombok.Data;

@Data // Lombok for getters, setters, etc.
public class LoginRequest {
	private String username;
	private String password;
}