package com.restaurant.demo.exception;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private long userId;
	public UserNotFoundException(long userId)
	{
		super(String.format("User %s not found",userId));
		this.userId=userId;
	}
}
