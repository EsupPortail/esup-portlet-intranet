package org.esup.portlet.intranet.web.springmvc;

import org.esup.portlet.intranet.BaseTestCase;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.portlet.ModelAndView;

public class WebControllerTest extends BaseTestCase{

	@Test
	public void testShowAddBookForm() {
		
		try {
			MockRenderRequest request = new MockRenderRequest();
			request.setPreferences(prefs);
			request.setAttribute("prefs", prefs);
			request.setAttribute("key", "jed");
			MockRenderResponse response = new MockRenderResponse();
			webController.init(request, response);
			ModelAndView mv = webController.getList(request, response);
			printDocs("searchDocs", (Documents)mv.getModel().get("docs"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
