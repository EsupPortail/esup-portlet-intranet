package org.esup.portlet.intranet.nuxeo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.client.jaxrs.Constants;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.model.Document;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;
import org.nuxeo.ecm.automation.client.jaxrs.model.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class NuxeoServiceTest {
	
	@Value("${nuxeoHost}")
	String nuxeoHost;
	@Value("${nuxeoPortalAuthSecret}")
	String nuxeoPortalAuthSecret;
	@Value("${intranetPath}")
	String intranetPath;
	@Value("${userId}")
	String userId;
	
	String key = "jed";
	private Session session = null;
	
	private void getSession(HttpAutomationClient client){
		Session session = client.getSession(userId, nuxeoPortalAuthSecret);
		assertNotNull(session);
		this.session = session;
	}
	
	private void queryList(String queryTitle, String query) throws Exception{
		Documents docs = (Documents) session.newRequest("Document.Query").set("query", query).execute();
		System.out.println("**** "+queryTitle+" ****");
		System.out.println("");
		for (Document document : docs) {
			System.out.println("Type : " +document.getType());
			System.out.println("Title : " +document.getTitle());
			System.out.println("Path : " +document.getPath());
			System.out.println("");
		}
	}
	
	@Test
	public void testAll() throws Exception{
		HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
		getSession(client);
		
		testGetAllFiles();
		testGetList();
		testSearch(key,null);
		testNews();
		testGetFile();
		
		client.shutdown();
	}
	
	
	
	public void testGetAllFiles() throws Exception{
		String query = "SELECT * FROM Document WHERE ecm:primaryType='File'";
		queryList("testGetAllFiles", query);
		
	}
	
	public void testGetList() throws Exception{
		String query = "SELECT * FROM Document WHERE (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" + intranetPath + "\")" ;
		queryList("testGetList", query);
	}
	
	public void testSearch(String key, String orderClause) throws Exception{

		String query = "SELECT * FROM Document WHERE (ecm:fulltext = \"" + key
				+ "\") AND (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" + intranetPath + "\")";
		if(orderClause != null)
			query += orderClause;
		
		queryList("testSearch : key = "+key, query);
	}
	
	public void testNews() throws Exception{
		String query = "SELECT * FROM Document WHERE (ecm:path STARTSWITH \"" + intranetPath + "\")"
				+ " AND (ecm:primaryType = 'File') ORDER BY dc:modified DESC ";
		
		queryList("testNews", query);
	}
	
	public void testGetFile() throws Exception{
		String filePath = "/default-domain/workspaces/devq/jed.pdf";
		
		System.out.println("**** testGetFile ****");

		// Get the file document where blob was attached
		Document doc = (Document) session.newRequest(
		        "Document.Fetch").setHeader(
		        Constants.HEADER_NX_SCHEMAS, "*").set("value", filePath).execute();
		// get the file content property
		PropertyMap map = doc.getProperties().getMap("file:content");
		// get the data URL
		String path = map.getString("data");
		 
		// download the file from its remote location
		FileBlob blob = (FileBlob) session.getFile(path);
		System.out.println(blob);
	}
}
