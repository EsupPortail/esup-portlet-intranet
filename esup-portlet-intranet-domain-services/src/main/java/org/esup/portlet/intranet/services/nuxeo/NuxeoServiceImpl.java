package org.esup.portlet.intranet.services.nuxeo;

import org.esup.portlet.intranet.NuxeoResource;
import org.esup.portlet.intranet.dao.NuxeoDaoService;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NuxeoServiceImpl implements NuxeoService{
	
	@Autowired private NuxeoDaoService daoService;
	
	public Documents getList(NuxeoResource nuxeoResource) throws Exception{
		return daoService.getList(nuxeoResource);
	}
	public Documents getList(NuxeoResource nuxeoResource, String intranetPath) throws Exception{
		return daoService.getList(nuxeoResource, intranetPath);
	}
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception{
		return daoService.getFile(nuxeoResource, uid);
	}
	public Documents search(NuxeoResource nuxeoResource, String key) throws Exception{
		return daoService.search(nuxeoResource, key);
	}
	public Documents getNews(NuxeoResource nuxeoResource) throws Exception{
		return daoService.getNews(nuxeoResource);
	}
}
