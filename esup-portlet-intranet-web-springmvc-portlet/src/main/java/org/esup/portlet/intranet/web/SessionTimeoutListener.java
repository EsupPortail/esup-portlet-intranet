package org.esup.portlet.intranet.web;

import javax.servlet.http.HttpSessionEvent;

import org.esup.portlet.intranet.domain.nuxeo.NuxeoResource;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class SessionTimeoutListener extends HttpSessionEventPublisher{

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		SessionRegistry sessionRegistry = (SessionRegistry) ApplicationContextHolder.getContext().getBean("sessionRegistry");
	    
	    SessionInformation sessionInfo = (sessionRegistry != null ? 
	    		sessionRegistry.getSessionInformation(event.getSession().getId()) : null);
	    NuxeoResource userSession = null;
	    if (sessionInfo != null) {
	    	userSession = (NuxeoResource) sessionInfo.getPrincipal();
	    }
	    if (userSession != null) {
	    	userSession.closeSession();
	    }
	    super.sessionDestroyed(event);
	}
	
}
