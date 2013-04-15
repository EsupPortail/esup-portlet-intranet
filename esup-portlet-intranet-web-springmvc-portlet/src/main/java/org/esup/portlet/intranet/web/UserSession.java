package org.esup.portlet.intranet.web;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.esup.portlet.intranet.services.auth.Authenticator;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserSession {
	private String rootPath;
	private String nuxeoHost;
	private String intranetPath;
	private String nuxeoPortalAuthSecret;
	private Session session;
	private boolean initialized = false;
	private String uid;
	
	@Autowired
	private Authenticator authenticator;
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public boolean isInitialized() {
		return initialized;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getNuxeoHost() {
		return nuxeoHost;
	}
	public void setNuxeoHost(String nuxeoHost) {
		this.nuxeoHost = nuxeoHost;
	}
	public String getIntranetPath() {
		return intranetPath;
	}
	public void setIntranetPath(String intranetPath) {
		this.intranetPath = intranetPath;
	}
	public String getNuxeoPortalAuthSecret() {
		return nuxeoPortalAuthSecret;
	}
	public void setNuxeoPortalAuthSecret(String nuxeoPortalAuthSecret) {
		this.nuxeoPortalAuthSecret = nuxeoPortalAuthSecret;
	}
	
	public void init(PortletPreferences prefs) throws Exception{
		this.nuxeoHost = prefs.getValue("nuxeoHost", null);
		this.intranetPath = prefs.getValue("intranetPath", null);
		this.rootPath = this.intranetPath;
		this.nuxeoPortalAuthSecret = prefs.getValue("nuxeoPortalAuthSecret", null);
		
		try {
			this.uid = authenticator.getUser().getLogin();
		} catch (Exception e) {
			// mode local test with pluto.
			this.uid = "Administrator";
		}
		this.session = getSession();
		this.initialized = true;
	}

	public Session getSession() throws Exception{
		if(session == null){
			HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
			client.setRequestInterceptor(new PortalSSOAuthInterceptor(nuxeoPortalAuthSecret, this.uid));
			this.session = client.getSession();
		}
		return session;
	}
	
	public void expireSession(@SuppressWarnings("rawtypes") Map sessionMap){
		if(session != null)
			session.getClient().shutdown();
		session = null;
		sessionMap.remove("userSession");
	}
}
