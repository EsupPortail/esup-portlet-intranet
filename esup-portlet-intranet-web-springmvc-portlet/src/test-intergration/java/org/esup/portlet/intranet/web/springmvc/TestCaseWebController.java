package org.esup.portlet.intranet.web.springmvc;

import org.esup.portlet.intranet.BaseTestCase;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.portlet.ModelAndView;

public class TestCaseWebController extends BaseTestCase{

	@Test
	public void testWebController() throws Exception{
		MockRenderRequest request = new MockRenderRequest();
		request.setPreferences(prefs);
		request.setAttribute("prefs", prefs);
		request.setAttribute("key", "jed");
		MockRenderResponse response = new MockRenderResponse();
		ModelAndView mv = webController.getList(request, response);
		
		printDocs("searchDocs", (Documents)mv.getModel().get("docs"));
	}

}
