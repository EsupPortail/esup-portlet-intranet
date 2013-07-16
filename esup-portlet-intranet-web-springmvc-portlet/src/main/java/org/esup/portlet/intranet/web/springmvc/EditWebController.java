package org.esup.portlet.intranet.web.springmvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.portlet.intranet.domain.nuxeo.NuxeoResource;
import org.esup.portlet.intranet.services.auth.Authenticator;
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
public class EditWebController extends AbastractBaseController{
	
	@Autowired
	private Authenticator authenticator;
    @Autowired
    private ViewSelectorDefault viewSelector;
    
	@RenderMapping
    public ModelAndView editPreferences(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	PortletPreferences prefs = request.getPreferences();
    	
    	model.put(NUXEO_HOST + "_readOnly", prefs.isReadOnly(NUXEO_HOST));
		model.put(NUXEO_HOST,prefs.getValue(NUXEO_HOST, null));
		
		model.put(INTRANET_PATH + "_readOnly", prefs.isReadOnly(INTRANET_PATH));
		model.put(INTRANET_PATH,prefs.getValue(INTRANET_PATH, null));
		
    	return new ModelAndView(viewSelector.getViewName(request, "edit"), model);
    }
	
	/**
	 * Store preferences values.
     * If 'nuxeoHost' in the preferences is not readOnly then recreate the nuxeo session with the 'nuxeoHost' passed.
     * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ActionMapping(params="action=edit")
	public void editPreferences(ActionRequest request, ActionResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		
		NuxeoResource nuxeoResource = getNuxeoResourceFromPortletSession(request);
		
		if(!prefs.isReadOnly("nuxeoHost")){
			prefs.setValue("nuxeoHost", request.getParameter("nuxeoHost").trim());
			prefs.store();
			makeNuxeoSession(request, nuxeoResource);
    	}
    	if(!prefs.isReadOnly("intranetPath")){
    		prefs.setValue("intranetPath", request.getParameter("intranetPath").trim());
    	}
		prefs.store();
		
		response.setPortletMode(PortletMode.VIEW);
	}
}

