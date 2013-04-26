package org.esup.portlet.intranet.nuxeo;

import org.esup.portlet.intranet.web.NuxeoResource;
import org.nuxeo.ecm.automation.client.jaxrs.model.Documents;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;

public interface NuxeoService {
	public Documents getList(NuxeoResource nuxeoResource) throws Exception;
	public Documents getList(NuxeoResource nuxeoResource, String intranetPath) throws Exception;
	public FileBlob getFile(NuxeoResource nuxeoResource, String filePath) throws Exception;
	public Documents search(NuxeoResource nuxeoResource, String key) throws Exception;
	public Documents search(boolean isMobile, NuxeoResource nuxeoResource, String key) throws Exception;
	public Documents getNews(NuxeoResource nuxeoResource) throws Exception;
}
