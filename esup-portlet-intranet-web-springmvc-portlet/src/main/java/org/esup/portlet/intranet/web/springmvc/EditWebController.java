package org.esup.portlet.intranet.web.springmvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Scope("session")
@Controller
@RequestMapping(value = "EDIT")
public class EditWebController extends AbastractExceptionController{
	
	@RenderMapping
    public ModelAndView editPreferences(@RequestParam(defaultValue="",required=false) String message, 
    		RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	PortletPreferences prefs = request.getPreferences();
    	model.put("nuxeoHost",prefs.getValue("nuxeoHost", null));
    	model.put("intranetPath",prefs.getValue("intranetPath", null));
    	model.put("nuxeoPortalAuthSecret",prefs.getValue("nuxeoPortalAuthSecret", null));
    	model.put("message",message);
        return new ModelAndView("edit", model);
    }
	
	@ActionMapping(params="action=edit")
	public void searchDocs(ActionRequest request, ActionResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		prefs.setValue("nuxeoHost", request.getParameter("nuxeoHost"));
		prefs.setValue("intranetPath", request.getParameter("intranetPath"));
		prefs.setValue("nuxeoPortalAuthSecret", request.getParameter("nuxeoPortalAuthSecret"));
		prefs.store();
		response.setRenderParameter("message","editDone");
	}
}

