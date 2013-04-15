package org.esup.portlet.intranet.web.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Timer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.esup.portlet.intranet.nuxeo.NuxeoService;
import org.esup.portlet.intranet.services.auth.Authenticator;
import org.esup.portlet.intranet.web.Breadcrumb;
import org.esup.portlet.intranet.web.UserSession;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;
import org.nuxeo.ecm.automation.client.jaxrs.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class WebController extends AbastractExceptionController{
	
    @Autowired
    private NuxeoService nuxeoService;
	public void setNuxeoService(NuxeoService nuxeoService) {
		this.nuxeoService = nuxeoService;
	}
	@Autowired
	private Authenticator authenticator;	
	@Autowired
	private UserSession userSession;
	
    @RenderMapping
    public ModelAndView init(RenderRequest request, RenderResponse response) throws Exception {
    	if(!userSession.isInitialized()){
    		userSession.init(request, authenticator);
    	}
    	return getList(request, response);
    }
    
    @RenderMapping(params="action=list")
    public ModelAndView getList(RenderRequest request, RenderResponse response) throws Exception {  	
    	ModelMap model = new ModelMap();
    	String intranetPath = request.getParameter("intranetPath");
    	model.put("docs", nuxeoService.getList(userSession, intranetPath));
    	model.put("mode", request.getParameter("action"));
    	model.put("uid", userSession.getUid());
    	setBreadcrumb(model,intranetPath);
        return new ModelAndView("view", model);
    }
    
    @ActionMapping(params="action=search")
	public void searchDocs(ActionRequest request, ActionResponse response) throws Exception {
    	response.setRenderParameter("key", request.getParameter("key"));
    	response.setRenderParameter("action","search");
	}
	@RenderMapping(params="action=search")
	public ModelAndView searchDocs(@RequestParam(required=false) String key, RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model =  new ModelMap();   	
    	model.put("docs", nuxeoService.search(userSession, key));
    	model.put("mode", request.getParameter("action"));
    	setBreadcrumb(model);
        return new ModelAndView("view", model);
    }	
    
    @RenderMapping(params="action=new")
    public ModelAndView getNew(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
     	model.put("docs", nuxeoService.getNews(userSession));
     	model.put("mode", request.getParameter("action"));
        return new ModelAndView("view", model);
    }
    
    @ResourceMapping
    public void fileDown(ResourceRequest request, ResourceResponse response) throws Exception {
    	String filePath = request.getParameter("filePath");
    	FileBlob f = nuxeoService.getFile(userSession, filePath);
    	File file = f.getFile();
    	String fileName = f.getFileName();
    	
    	OutputStream outStream = response.getPortletOutputStream();
		if (!file.exists() || !file.canRead()) {
			outStream.write("<i>Unable to find the specified file</i>".getBytes());
		} else {
			FileInputStream inStream = new FileInputStream(file);
			String mimetype = f.getMimeType();
			response.setContentType(mimetype);
			f.setMimeType(mimetype);
			response.setProperty("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentLength((int) file.length());
			
			IOUtils.copy(inStream, response.getPortletOutputStream());
		    response.flushBuffer();		
		}
		outStream.flush();
		outStream.close();
    }
    
	private void setBreadcrumb(ModelMap model){
    	setBreadcrumb(model, userSession.getIntranetPath());
    }
    private void setBreadcrumb(ModelMap model, String intranetPath){
    	if(intranetPath == null)
    		intranetPath = userSession.getIntranetPath();
    	Breadcrumb b = new Breadcrumb();
		b.setBreadcrumb(userSession.getRootPath(), intranetPath);
		model.put("breadcrumb", b.getPathList());
    }
}
