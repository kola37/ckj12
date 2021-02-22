package com.example.demo.service;

public class UsernameExistException extends RuntimeException{
	public UsernameExistException(String msg) {
		super(msg);
	}

}
