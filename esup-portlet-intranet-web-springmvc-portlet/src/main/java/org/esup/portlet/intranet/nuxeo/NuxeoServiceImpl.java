package org.esup.portlet.intranet.nuxeo;

import org.esup.portlet.intranet.web.UserSession;
import org.nuxeo.ecm.automation.client.jaxrs.Constants;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.jaxrs.model.Document;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;
import org.nuxeo.ecm.automation.client.jaxrs.model.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class NuxeoServiceImpl implements NuxeoService{
    
	
	private Documents queryList(UserSession userSession, String query) throws Exception{
		Session session = userSession.getSession();
		Documents docs = (Documents) session.newRequest(DocumentService.Query).set("query", query).execute();
		return docs;
	}
	
	@Override
	public Documents getList(UserSession userSession) throws Exception{
		return getList(userSession, userSession.getIntranetPath());
	}

	@Override
	public Documents getList(UserSession userSession, String intranetPath) throws Exception{
		if (intranetPath == null)
			intranetPath = userSession.getIntranetPath();
		Session session = userSession.getSession();
		Document root = (Document) session.newRequest(DocumentService.FetchDocument).set("value", intranetPath).execute();
		if(root != null){
			String query = "SELECT * FROM Document WHERE ecm:parentId = '"+root.getId()+"'";
			Documents docs = (Documents) session.newRequest("Document.Query").setHeader(
			        Constants.HEADER_NX_SCHEMAS, "*").set("query", query).execute();
			return docs;
		}
		return null;
	}
	
	@Override
	public FileBlob getFile(UserSession userSession, String filePath) throws Exception{
		Session session = userSession.getSession();
		// Get the file document where blob was attached
		Document doc = (Document) session.newRequest("Document.Fetch").setHeader(
		        Constants.HEADER_NX_SCHEMAS, "*").set("value", filePath).execute();
		// get the file content property
		PropertyMap map = doc.getProperties().getMap("file:content");
		// get the data URL
		String path = map.getString("data");
//		String lastContributor = doc.getProperties().getString("dc:lastContributor");
//		String modified = doc.getProperties().getString("dc:lastContributor");
		 
		// download the file from its remote location
		FileBlob blob = (FileBlob) session.getFile(path);
		return blob;
	}

	@Override
	public Documents getNews(UserSession userSession) throws Exception{
		String query = "SELECT * FROM Document WHERE (ecm:path STARTSWITH \"" + userSession.getIntranetPath() + "\")"
				+ " AND (ecm:primaryType = 'File') ORDER BY dc:modified DESC ";
		return queryList(userSession, query);
	}

	@Override
	public Documents search(UserSession userSession, String key) throws Exception{
		String query = "SELECT * FROM Document WHERE (ecm:fulltext = \"" + key
				+ "\") AND (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" + userSession.getIntranetPath() + "\") ORDER BY dc:modified DESC";
		return queryList(userSession, query);
	}
	
	@Override
	public Documents getTree(UserSession userSession) throws Exception {
		Session session = userSession.getSession();
		Document root = (Document) session.newRequest(DocumentService.FetchDocument).set("value", userSession.getIntranetPath()).execute();
		if(root != null){
			Documents docs = (Documents) session.newRequest(DocumentService.GetDocumentChildren).setInput(root).execute();
			return docs;
		}
		return null;
	}

}
