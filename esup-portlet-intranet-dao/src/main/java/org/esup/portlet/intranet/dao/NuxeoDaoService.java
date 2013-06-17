package org.esup.portlet.intranet.dao;

import org.esup.portlet.intranet.NuxeoResource;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;


public interface NuxeoDaoService {
	public Documents getList(NuxeoResource nuxeoResource) throws Exception;
	public Documents getList(NuxeoResource nuxeoResource, String intranetPath) throws Exception;
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception;
	public Documents search(NuxeoResource nuxeoResource, String key) throws Exception;
	public Documents getNews(NuxeoResource nuxeoResource) throws Exception;
}
