package org.esup.portlet.intranet.domain.nuxeo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.impl.Log4JLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PathRef;
import org.nuxeo.ecm.automation.core.operations.document.CreateDocument;
import org.nuxeo.ecm.automation.core.operations.document.DeleteDocument;
import org.nuxeo.ecm.automation.core.operations.document.FetchDocument;
import org.nuxeo.ecm.automation.core.operations.document.GetDocumentChildren;
import org.nuxeo.ecm.automation.core.operations.document.Query;


public class NuxeoServiceImplTest {

	Log4JLogger logger = new Log4JLogger(this.getClass().getName());
	NuxeoResource nuxeoResource;
	NuxeoService nuxeoService = new NuxeoServiceImpl();
	
	private String nuxeoHost = "http://localhost:8080/nuxeo";
	private String rootPath = "/default-domain/workspaces/my-workspace";
	private String testFolder = "automation-test-folder";
	private String testPath = rootPath + "/" + testFolder;
	private String user = "test";
	private String pw = "test";
	protected Document automationTestFolder;

	@Before
	public void initNuxeoResource() throws Exception {
		nuxeoResource = new NuxeoResource();
		nuxeoResource.setIntranetPath(rootPath);
		nuxeoResource.makeSession(nuxeoHost, user, pw );
		
		Document root = (Document) nuxeoResource.getSession().newRequest(FetchDocument.ID).set("value", rootPath).execute();
        assertNotNull(root);
        assertEquals(rootPath, root.getPath());
        
        automationTestFolder = (Document) nuxeoResource.getSession().newRequest(CreateDocument.ID)
        				.setInput(root).set("type", "Folder")
        				.set("name",testFolder)
        				.execute();
        assertNotNull(automationTestFolder);
    
	}
	
	@After
	public void closeSession() throws Exception {
		nuxeoResource.getSession().newRequest(DeleteDocument.ID).setInput(automationTestFolder).execute();
		nuxeoResource.closeSession();
	}
	
	@Test
	public void getList() throws Exception{
		printDocs(nuxeoService.getList(nuxeoResource), "getList");
	}
	
	@Test
	public void search() throws Exception{
		String keyword = "oracle";
		printDocs(nuxeoService.search(nuxeoResource, keyword), "search");
	}
	
	@Test
	public void news() throws Exception{
		printDocs(nuxeoService.news(nuxeoResource, 20), "news");
	}
	
	@Test
	public void getChildren() throws Exception{
		Documents testDoc = (Documents) nuxeoResource.getSession()
				.newRequest(DocumentService.GetDocumentChildren)
				.setInput(new PathRef(this.rootPath))
				.setHeader(Constants.HEADER_NX_SCHEMAS, "dublincore,blog")
				.execute();
		printDocs(testDoc, "getChildren");		
	}
	
	@Test
    public void testQuery() throws Exception {
		
		getChildren();	
		
        // create a folder
        Document folder = (Document) nuxeoResource.getSession()
        			.newRequest(CreateDocument.ID)
        			.setInput(automationTestFolder)
        			.set("type", "Folder")
        			.set("name", "queryTest")
        			.set("properties", "dc:title=Query Test")
        			.execute();
        // create 2 files
        nuxeoResource.getSession().newRequest(CreateDocument.ID)
        			.setInput(folder)
        			.set("type", "Note")
        			.set("name", "note1")
        			.set("properties", "dc:title=Note1")
        			.execute();
        
        nuxeoResource.getSession().newRequest(CreateDocument.ID).
        			setInput(folder)
        			.set("type", "Note")
        			.set("name", "note2")
        			.set("properties", "dc:title=Note2")
        			.execute();

        // now query the two files
        Documents docs = (Documents) nuxeoResource.getSession().newRequest(Query.ID)
        			.set("query","SELECT * FROM Note WHERE ecm:path STARTSWITH '"+ testPath +"' ")
        			.execute();        
        
        printDocs(docs, "now query the two files");
        
        assertEquals(2, docs.size());
        String title1 = docs.get(0).getTitle();
        String title2 = docs.get(1).getTitle();
        assertTrue(title1.equals("Note1") && title2.equals("Note2")
                || title1.equals("Note2") && title2.equals("Note1"));

        // now get children of /testQuery
        docs = (Documents) nuxeoResource.getSession().newRequest(GetDocumentChildren.ID).setInput(
                folder).execute();
        
        printDocs(docs, "now get children of /testQuery");
        assertEquals(2, docs.size());

        title1 = docs.get(0).getTitle();
        title2 = docs.get(1).getTitle();
        assertTrue(title1.equals("Note1") && title2.equals("Note2")
                || title1.equals("Note2") && title2.equals("Note1"));
        
        
        printDoc((Document)nuxeoResource.getSession().newRequest(FetchDocument.ID).set("value", testPath).execute());

    }
	
	private void printDocs(Documents docs, String title){
		System.out.println("-------------" + title + " test -------------");
		for(Document doc : docs){
			printDoc(doc);
		}
	}
	
	private void printDoc(Document doc){
		System.out.print("[" + doc.getType() + "] ");
		System.out.print(doc.getFacets()+ "|");
		System.out.print(doc.getTitle()+ "|");
		System.out.print(doc.getPath());
		System.out.print("[" + doc.getState() + "] ");
		System.out.println("");
	}

}
