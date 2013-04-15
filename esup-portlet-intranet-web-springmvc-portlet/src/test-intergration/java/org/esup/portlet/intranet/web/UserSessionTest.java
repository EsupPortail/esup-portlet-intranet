package org.esup.portlet.intranet.web;

import org.esup.portlet.intranet.BaseTestCase;
import org.esup.portlet.intranet.services.auth.MockAuthenticator;
import org.junit.Test;
import org.springframework.mock.web.portlet.MockRenderRequest;

public class UserSessionTest extends BaseTestCase{

	@Test
	public void test() throws Exception{
		UserSession userSession = new UserSession();
		MockAuthenticator authenticator = new MockAuthenticator();
		authenticator.setUserId("loginUser");
		MockRenderRequest request = new MockRenderRequest();
		request.setPreferences(prefs);
		request.getPortletSession(true).setMaxInactiveInterval(10);
		userSession.init(request, authenticator);
		System.out.println("test");
	}

}
