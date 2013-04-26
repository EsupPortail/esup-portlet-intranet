package org.esup.portlet.intranet.web.springmvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.portlet.intranet.services.auth.Authenticator;
import org.esup.portlet.intranet.web.NuxeoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Scope("session")
@Controller
@RequestMapping(value = "EDIT")
public class EditWebController extends AbastractExceptionController{
	
	@Autowired
	private NuxeoResource userSession;
	@Autowired
	private Authenticator authenticator;
    @Autowired
    private ViewSelectorDefault viewSelector;
    
	@RenderMapping
    public ModelAndView editPreferences(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	PortletPreferences prefs = request.getPreferences();
    	if(!prefs.isReadOnly("nuxeoHost")){
    		model.put("nuxeoHost",prefs.getValue("nuxeoHost", null));
    	}
    	if(!prefs.isReadOnly("intranetPath")){
    		model.put("intranetPath",prefs.getValue("intranetPath", null));
    	}
    	return new ModelAndView(viewSelector.getViewName(request, "edit"), model);
    }
	
	@ActionMapping(params="action=edit")
	public void editPreferences(ActionRequest request, ActionResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		if(!prefs.isReadOnly("nuxeoHost")){
			prefs.setValue("nuxeoHost", request.getParameter("nuxeoHost"));
    	}
    	if(!prefs.isReadOnly("intranetPath")){
    		prefs.setValue("intranetPath", request.getParameter("intranetPath"));
    	}
		prefs.store();
		userSession.expireSession();
		response.setPortletMode(PortletMode.VIEW);
	}
}

