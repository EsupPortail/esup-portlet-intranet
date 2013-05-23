package org.esup.portlet.intranet.web.springmvc;

import org.esup.portlet.intranet.nuxeo.NuxeoService;
import org.esup.portlet.intranet.services.auth.Authenticator;
import org.esup.portlet.intranet.web.NuxeoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class TestWebController extends WebController{
	@Value("${rowcount}")
	int rowcount;
	
	@Value("${rowcount.mobile}")
	int rowcount_mobile;
	
    @Autowired
    private NuxeoService nuxeoService;
	public void setNuxeoService(NuxeoService nuxeoService) {
		this.nuxeoService = nuxeoService;
	}
	@Autowired
	private Authenticator authenticator;
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	@Autowired
	private NuxeoResource nuxeoResource;
    public void setNuxeoResource(NuxeoResource nuxeoResource) {
		this.nuxeoResource = nuxeoResource;
	}
    
	@Autowired
    private ViewSelectorDefault viewSelector;
    
    
}
