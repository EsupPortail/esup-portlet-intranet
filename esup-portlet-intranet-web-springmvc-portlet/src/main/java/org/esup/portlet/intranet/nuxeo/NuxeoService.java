package org.esup.portlet.intranet.nuxeo;

import org.esup.portlet.intranet.web.UserSession;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;

public interface NuxeoService {
	public Documents getList(UserSession userSession) throws Exception;
	public Documents getList(UserSession userSession, String intranetPath) throws Exception;
	public FileBlob getFile(UserSession userSession, String filePath) throws Exception;
	public Documents search(UserSession userSession, String key) throws Exception;
	public Documents getNews(UserSession userSession) throws Exception;
	public Documents getTree(UserSession userSession) throws Exception;
}
