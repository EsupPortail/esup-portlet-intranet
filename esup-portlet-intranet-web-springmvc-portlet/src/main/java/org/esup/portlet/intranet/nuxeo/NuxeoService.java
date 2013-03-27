package org.esup.portlet.intranet.nuxeo;

import javax.portlet.PortletPreferences;

import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;

public interface NuxeoService {
	
	public Documents getList(PortletPreferences prefs);
	public FileBlob getFile(PortletPreferences prefs, String filePath);
	public Documents search(PortletPreferences prefs, String key);
	public Documents search(PortletPreferences prefs, String key, String orderBy);
	public Documents getNews(PortletPreferences prefs);

}
