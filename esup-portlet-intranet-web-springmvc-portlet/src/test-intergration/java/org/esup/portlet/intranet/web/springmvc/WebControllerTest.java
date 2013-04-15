package org.esup.portlet.intranet.web.springmvc;

import org.esup.portlet.intranet.BaseTestCase;
import org.esup.portlet.intranet.services.auth.MockAuthenticator;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.portlet.ModelAndView;

public class WebControllerTest extends BaseTestCase{

	@Test
	public void testWebController() {
		
		try {
			MockRenderRequest request = new MockRenderRequest();
			request.getPortletSession().setMaxInactiveInterval(10);
			request.setPreferences(prefs);
			request.setAttribute("prefs", prefs);
			request.setAttribute("key", "jed");
			MockRenderResponse response = new MockRenderResponse();
			ModelAndView mv = webController.getList(request, response);
			System.out.println("test");
			//printDocs("searchDocs", (Documents)mv.getModel().get("docs"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sessionTimeoutTest(){
		try {
			MockRenderRequest request = new MockRenderRequest();
			request.setPreferences(prefs);
			request.setAttribute("prefs", prefs);
			MockRenderResponse response = new MockRenderResponse();
			request.getPortletSession().setMaxInactiveInterval(10);
			webController.getUserSession().init(request, this.authenticator);
			
			
			System.out.println("test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
