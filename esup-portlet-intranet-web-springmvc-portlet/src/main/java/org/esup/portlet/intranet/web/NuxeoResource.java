package org.esup.portlet.intranet.web;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.esup.portlet.intranet.services.auth.Authenticator;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

@Scope("session")
@Service
public class NuxeoResource {
	private String rootPath;
	private Session nuxeoSession;
	private boolean initialized = false;
	private PortletPreferences prefs;
	@Autowired SessionRegistry sessionRegistry;
	
	public boolean isInitialized() {
		return initialized;
	}
	public String getRootPath() {
		return rootPath;
	}
	public String getIntranetPath() {
		return prefs.getValue("intranetPath", null);
	}
	public PortletPreferences getPrefs() {
		return prefs;
	}
	public Session getNuxeoSession() throws Exception{
		return nuxeoSession;
	}
	public void init(PortletRequest request,Authenticator authenticator) throws Exception{
		if(!this.initialized){
			this.prefs = request.getPreferences();
			this.rootPath = prefs.getValue("intranetPath",null);
			String uid;
			try {
				uid = authenticator.getUser().getLogin();
			} catch (Exception e) {
				// local test mode with pluto.
				uid = "Administrator";
			}
			
			HttpAutomationClient client = new HttpAutomationClient(prefs.getValue("nuxeoHost", null) + "/site/automation");
			client.setRequestInterceptor(new PortalSSOAuthInterceptor(prefs.getValue("nuxeoPortalAuthSecret", null), uid));
			this.nuxeoSession = client.getSession();
			
			sessionRegistry.registerNewSession(request.getPortletSession().getId(), this);
			this.initialized = true;
		}
	}
	
	public void expireSession(){
		if(nuxeoSession != null)
			nuxeoSession.getClient().shutdown();
		nuxeoSession = null;
		this.initialized = false;
	}
}
