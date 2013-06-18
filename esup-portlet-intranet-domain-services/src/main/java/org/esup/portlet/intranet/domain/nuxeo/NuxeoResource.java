package org.esup.portlet.intranet.domain.nuxeo;

import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;

public class NuxeoResource {
	private String intranetPath;
	private String rootPath;
	private Session session;

	public Session getSession() {
		if(session == null)
			throw new IllegalAccessError("No session available.");
		return session;
	}
	
	public boolean hasSession(){
		return (session != null);
	}

	public void makeSession(String nuxeoHost, String ssoUserId, String ssoUserPw) throws Exception{
		if(session != null){
			closeSession();
		}
		HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
		client.setRequestInterceptor(new PortalSSOAuthInterceptor(ssoUserPw, ssoUserId));
		this.session = client.getSession();
	}

	public void closeSession(){
		if(session != null)
			session.getClient().shutdown();
		session = null;
	}
	
	///////////////////////////////////////////////////////////////////
	
	public String getIntranetPath() {
		return intranetPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setIntranetPath(String intranetPath) {
		this.intranetPath = intranetPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
}
