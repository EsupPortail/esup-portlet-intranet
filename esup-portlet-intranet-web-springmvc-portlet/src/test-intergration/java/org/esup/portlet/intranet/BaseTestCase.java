package org.esup.portlet.intranet;

import org.esup.portlet.intranet.nuxeo.NuxeoServiceImpl;
import org.esup.portlet.intranet.web.springmvc.WebController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.client.jaxrs.model.Document;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.portlet.MockPortletPreferences;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class BaseTestCase {
	@Value("${nuxeoHost}")
	String nuxeoHost;
	@Value("${nuxeoPortalAuthSecret}")
	String nuxeoPortalAuthSecret;
	@Value("${intranetPath}")
	String intranetPath;
	@Value("${userId}")
	String userId;
	
	@Autowired
	protected NuxeoServiceImpl nuxeoService;	
	
	@Autowired
	protected WebController webController;
	String key = "jed";
	
	protected MockPortletPreferences prefs;
	@Before
	public void init() throws Exception{
		//nuxeoService.setAuthenticator(authenticator);
		prefs = new MockPortletPreferences();
		prefs.setValue("nuxeoHost", nuxeoHost);
		prefs.setValue("intranetPath", intranetPath);
		prefs.setValue("nuxeoPortalAuthSecret", nuxeoPortalAuthSecret);
		webController.setNuxeoService(nuxeoService);
	}
	
	protected void printDocs(String queryTitle, Documents docs){
		System.out.println("**** "+queryTitle+" ****");
		System.out.println("");
		if(docs != null)
		for (Document document : docs) {
			System.out.println("Type : " +document.getType());
			System.out.println("Title : " +document.getTitle());
			System.out.println("Path : " +document.getPath());
			System.out.println("");
		}
	}
	
	@Test
	public void doNothing(){}
}
