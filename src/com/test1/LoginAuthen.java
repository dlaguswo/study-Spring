package com.test1;

public class LoginAuthen implements Authenticator{

	@Override
	public void authen(String userId, String userPwd) throws UserException {
		if(!userId.equals("inna") || !userPwd.equals("23")) {
			throw new UserException("invalid id: " + userId);
		}
	}
	
	
}
