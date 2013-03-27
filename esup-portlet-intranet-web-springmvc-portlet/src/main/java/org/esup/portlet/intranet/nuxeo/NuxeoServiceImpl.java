package org.esup.portlet.intranet.nuxeo;

import javax.portlet.PortletPreferences;

import org.esup.portlet.intranet.services.auth.Authenticator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.nuxeo.ecm.automation.client.jaxrs.Constants;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.model.Document;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;
import org.nuxeo.ecm.automation.client.jaxrs.model.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NuxeoServiceImpl implements NuxeoService{
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	private static final String DEFAULT_USERNAME = "JavaWorld";
    private static final String NUXEO_HOST = "nuxeoHost";
    private static final String INTRANET_PATH = "intranetPath";
    private static final String NUXEO_PORTAL_AUTH_SECRET = "nuxeoPortalAuthSecret";
	
    @Autowired
	private Authenticator authenticator; 
    
	private Session getSession(PortletPreferences prefs, HttpAutomationClient client){
		try {
			//client.setRequestInterceptor(new PortalSSOAuthInterceptor(secret, user));
			//Session session = client.getSession();
			
			String nuxeoPortalAuthSecret = prefs.getValue(NUXEO_PORTAL_AUTH_SECRET, DEFAULT_USERNAME);
			Session session = client.getSession(authenticator.getUser().getLogin(), nuxeoPortalAuthSecret);
			
			return session;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	private Documents queryList(PortletPreferences prefs, String query){
		try {
			String nuxeoHost = prefs.getValue(NUXEO_HOST, DEFAULT_USERNAME);
			HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
			Session session = getSession(prefs, client);
			if(session != null){
				Documents docs = (Documents) session.newRequest("Document.Query").set("query", query).execute();
				client.shutdown();
				return docs;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Documents getList(PortletPreferences prefs) {
		String intranetPath = prefs.getValue(INTRANET_PATH, DEFAULT_USERNAME);
		String query = "SELECT * FROM Document WHERE (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" + intranetPath + "\")" ;
		Documents docs = queryList(prefs, query);
		return docs;
	}
	
	@Override
	public FileBlob getFile(PortletPreferences prefs, String filePath) {
		try {
			String nuxeoHost = prefs.getValue(NUXEO_HOST, DEFAULT_USERNAME);
			HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
			Session session = getSession(prefs, client);
			if(session != null){
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
				client.shutdown();
				return blob;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public Documents getNews(PortletPreferences prefs) {
		String intranetPath = prefs.getValue(INTRANET_PATH, DEFAULT_USERNAME);
		String query = "SELECT * FROM Document WHERE (ecm:path STARTSWITH \"" + intranetPath + "\")"
				+ " AND (ecm:primaryType = 'File') ORDER BY dc:modified DESC ";
		return queryList(prefs, query);
	}
	

	@Override
	public Documents search(PortletPreferences prefs, String key) {
		return search(prefs, key, null);
	}
	@Override
	public Documents search(PortletPreferences prefs, String key, String orderBy) {
		String intranetPath = prefs.getValue(INTRANET_PATH, DEFAULT_USERNAME);
		String orderClause = "";
		if (orderBy != null) {
			orderClause = " ORDER BY " + orderBy;
		}
		String query = "SELECT * FROM Document WHERE (ecm:fulltext = \"" + key
				+ "\") AND (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" + intranetPath + "\")" + orderClause;
		return queryList(prefs, query);
	}

}
