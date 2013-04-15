package org.esup.portlet.intranet.services.auth;

import org.esup.portlet.intranet.domain.beans.User;

public class MockAuthenticator implements Authenticator{
	
	String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public User getUser() throws Exception {
		User user = new User();
		user.setLogin(userId);
		return user;
	}

}
