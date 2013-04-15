package org.esup.portlet.intranet.web;

import java.util.Timer;
import java.util.TimerTask;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;

import org.esup.portlet.intranet.services.auth.Authenticator;
import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("session")
@Service
public class UserSession {
	private String rootPath;
	private String nuxeoHost;
	private String intranetPath;
	private String nuxeoPortalAuthSecret;
	private Session nuxeoSession;
	private boolean initialized = false;
	private String uid;
	Timer timer;
	private RemindTask myTask;
	PortletSession portletSession;
	long lastAccessedTime;
	boolean isNuxeoSession = false;
	
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
	
	public void init(RenderRequest request,Authenticator authenticator) throws Exception{
		this.portletSession = request.getPortletSession();
		PortletPreferences prefs = request.getPreferences();
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
		timer = new Timer();
		
		this.nuxeoSession = getNuxeoSession();
		
		myTask = new RemindTask();
		timer.schedule(myTask, 0, this.portletSession.getMaxInactiveInterval());
		
		lastAccessedTime = this.portletSession.getLastAccessedTime();
		this.initialized = true;
	}

	public Session getNuxeoSession() throws Exception{
		if(nuxeoSession == null){
			HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
			client.setRequestInterceptor(new PortalSSOAuthInterceptor(nuxeoPortalAuthSecret, this.uid));
			//this.nuxeoSession = client.getSession();
			isNuxeoSession = true;
		}
		this.lastAccessedTime = this.portletSession.getLastAccessedTime();
		return nuxeoSession;
	}
	
	public void expireSession(){
		if(nuxeoSession != null)
			nuxeoSession.getClient().shutdown();
		nuxeoSession = null;
		this.portletSession.removeAttribute("userSession");
		this.timer.cancel();
		this.timer = null;
		this.initialized = false;
	}
	
	class RemindTask extends TimerTask {
        public void run() {
        	long current = System.currentTimeMillis();
        	long interval = portletSession.getMaxInactiveInterval();
        	long restTime = current - lastAccessedTime;
        	
        	if(restTime > interval)
        		expireSession();
        }
    }
	
}
