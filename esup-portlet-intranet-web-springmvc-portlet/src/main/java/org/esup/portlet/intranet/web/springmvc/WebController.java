package org.esup.portlet.intranet.web.springmvc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.IOUtils;
import org.esup.portlet.intranet.domain.nuxeo.NuxeoResource;
import org.esup.portlet.intranet.domain.nuxeo.NuxeoService;
import org.esup.portlet.intranet.web.Breadcrumb;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class WebController extends AbastractBaseController{
	@Value("${rowcount}")
	int rowcount;
	
	@Value("${rowcount.mobile}")
	int rowcount_mobile;
	
    @Autowired private NuxeoService nuxeoService;
    private Breadcrumb breadCrumb = new Breadcrumb();
    
    
    /**
     * First page.
     * If the preferences of portlet are not fixed (by Administrator), init_${view mode}.jsp will be called.
     * Otherwise, getList method will be called. 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RenderMapping
    public ModelAndView welcome(RenderRequest request, RenderResponse response) throws Exception {
    	if(!checkPerferences(request.getPreferences())){
    		ModelMap model = new ModelMap();
    		model.put("isuPortal", isuPortal(request));
    		return new ModelAndView(viewSelector.getViewName(request, "init"), model);
    	}
    	return getList(request,response);
    }
    
    /**
     * Call the getList method of nuxeoService with intranetPath value(of NuxeoResource) and pass the result to UI.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RenderMapping(params="action=list")
    public ModelAndView getList(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	model.put("isuPortal", isuPortal(request));
    	NuxeoResource nuxeoResource = getNuxeoSource(request);
    	model.put("docs", nuxeoService.getList(nuxeoResource).list());
    	model.put("mode", "list");
    	setBreadcrumb(model,nuxeoResource);
        return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    }
	
	/**
	 * Show searchForm UI (mobile mode only)
	 * @param request
	 * @return
	 */
	@RenderMapping(params="action=search-form")
	public ModelAndView showSearchForm(RenderRequest request) {
		ModelMap model = new ModelMap();
		model.put("isuPortal", isuPortal(request));
		model.put("mode", "search-form");
		return new ModelAndView(viewSelector.getViewName(request, "search"), model);
	}

	/**
	 * Receive the key, action parameters from UI.
	 * Because the action value is search, searchDocs methos will be called.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    @ActionMapping(params="action=search")
	public void searchDocs(ActionRequest request, ActionResponse response) throws Exception {
    	response.setRenderParameter("key", request.getParameter("key"));
    	response.setRenderParameter("action","search");
	}
    
    /**
     * do search with key value and send the result to UI.
     * If the agent of user navigator is mobile, rowCnt and leftCnt value will be necessary. 
     * @param key
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RenderMapping(params="action=search")
	public ModelAndView searchDocs(@RequestParam(required=false) String key, RenderRequest request, RenderResponse response) throws Exception {
		if(key == null)
    		return getList(request, response);
		
		ModelMap model =  new ModelMap(); 
    	model.put("isuPortal", isuPortal(request));
    	NuxeoResource nuxeoResource = getNuxeoSource(request);     	
    	
    	Documents docs = nuxeoService.search(nuxeoResource, key);
    	if(docs.size()==0){
    		String noResultMsg = messageSource.getMessage("list.no.result", null, "", request.getLocale());
    		model.put("noResultMsg", noResultMsg);
    	}
    		
    	model.put("docs", docs.list());
    	model.put("mode", "search");
    	model.put("key", key);
    	
    	if(viewSelector.isMobileAgent(request)){
    		model.put("rowCnt", rowcount_mobile);
    		model.put("leftCnt", docs.size()-rowcount_mobile);
    		return new ModelAndView(viewSelector.getViewName(request, "search"), model);
    	}else{
    		return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    	}
    }	
    
	/**
	 * Get recent documents from Nuxeo. 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RenderMapping(params="action=new")
    public ModelAndView getNew(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	model.put("isuPortal", isuPortal(request));
    	NuxeoResource nuxeoResource = getNuxeoSource(request);
    	int pageSize = viewSelector.isMobileAgent(request) ? rowcount_mobile : rowcount;
    	PaginableDocuments docs = nuxeoService.news(nuxeoResource, pageSize);
    	model.put("docs", docs.list());
     	model.put("mode", "new");
        return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    }
    
    /**
     * file download
     * @param request
     * @param response
     * @throws Exception
     */
    @ResourceMapping
    public void fileDown(ResourceRequest request, ResourceResponse response) throws Exception {
    	NuxeoResource nuxeoResource = getNuxeoSource(request);
    	String uid = request.getParameter("uid");
    	Document doc = nuxeoService.getFileDocument(nuxeoResource, uid);
    	if(doc.getType().equals("Note")){
			noteDown(doc, response);
		}else{
			fileDown(doc,nuxeoResource,request,response);
		}
    }
    
    private void fileDown(Document doc, NuxeoResource nuxeoResource, ResourceRequest request, ResourceResponse response) throws Exception{
    	OutputStream outStream = response.getPortletOutputStream();
    	// get the file content property
		PropertyMap map = doc.getProperties().getMap("file:content");
		// get the data URL
		String path = map.getString("data");		
		 
		// download the file from its remote location
		FileBlob blob = (FileBlob) nuxeoResource.getSession().getFile(path);
    	File file = blob.getFile();
    	String fileName = blob.getFileName();
    	
		if (!file.exists() || !file.canRead()) {
			String message = "<i>"+messageSource.getMessage("exception.file.notfound", null, "", request.getLocale())+"</i>";
			outStream.write(message.getBytes());
		} else {
			FileInputStream inStream = new FileInputStream(file);
			String mimetype = blob.getMimeType();
			response.setContentType(mimetype);
			response.setProperty("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentLength((int) file.length());
			
			IOUtils.copy(inStream, outStream);
		    response.flushBuffer();		
		}
		outStream.flush();
		outStream.close();
    }
    
    private void noteDown(Document doc,  ResourceResponse response) throws IOException{
    	OutputStream outStream = response.getPortletOutputStream();
    	
    	String content = (String)doc.getProperties().get("note:note");
		String fileName = (String)doc.getProperties().get("dc:title");
		String mime_type = (String)doc.getProperties().get("note:mime_type");
		
		if(!fileName.contains(".")){
			fileName += "." + getNoteFileType(mime_type);
		}
    	
    	// convert String into InputStream
    	InputStream inStream = new ByteArrayInputStream(content.getBytes());
		response.setContentType(mime_type);
		response.setProperty("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		response.setContentLength((int) content.length());
		IOUtils.copy(inStream, outStream);
	    response.flushBuffer();	
		outStream.flush();
		outStream.close();
    }
    
    private String getNoteFileType(String mime_type){
		String filetype = "txt";
		if(mime_type.equals("text/xml")){
			filetype = "xml";
		}else if(mime_type.equals("text/x-web-markdown")){
			filetype = "md";
		}else if(mime_type.equals("text/plain")){
			filetype = "txt";
		}else if(mime_type.equals("text/html")){
			filetype = "html";
		}
		return filetype;
	}
    
    /**
     * Make Breadcrumb string and save it on the model obj.
     * @param model
     * @param nuxeoResource
     */
    private void setBreadcrumb(ModelMap model, NuxeoResource nuxeoResource){
    	
    	
    	
    	
    	
		breadCrumb.setBreadcrumb(nuxeoResource);
		model.put("breadcrumb", breadCrumb.getPathList());
    }
    
    /**
     * Check if the preferences values are modified by Administrator of user.
     * The initial values (in portlet.xml) of preferences start with '$'.
     * @param prefs
     * @return
     */
    private boolean checkPerferences(PortletPreferences prefs){
		return (prefs.getValue("nuxeoHost", "").startsWith("$") 
				|| prefs.getValue("intranetPath", "").startsWith("$")) ? false : true;
	}
    
    /**
     * uPortal Server provide '<div data-role="page">' code for mobile pages basically.
     * To avoid the problem of duplication code, we need to know the portlet server's feature.  
     * @param request
     * @return
     */
    private boolean isuPortal(RenderRequest request){
		return request.getPortalContext().getPortalInfo().contains("uPortal");
	}
    
    /**
     * Get NuxeoSource which have nuxeo session, intranetPath, rootPath.
     * 
     * @param request
     * @return
     * @throws Exception
     */
	private NuxeoResource getNuxeoSource(PortletRequest request) throws Exception{
		NuxeoResource nuxeoResource = getNuxeoResourceFromPortletSession(request);
    	if(!nuxeoResource.hasSession()){
			makeNuxeoSession(request,nuxeoResource);
		}
    	
    	PortletPreferences prefs = request.getPreferences();
		nuxeoResource.setRootPath(prefs.getValue(INTRANET_PATH, ""));
		
		String intranetPath = (request.getParameter("intranetPath") == null) ? prefs.getValue(INTRANET_PATH, "") 
				: request.getParameter("intranetPath");
		
    	nuxeoResource.setIntranetPath(intranetPath);
    	return nuxeoResource;
	}
}
