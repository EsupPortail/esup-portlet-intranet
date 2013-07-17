package org.esup.portlet.intranet.domain.nuxeo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.zip.ZipOutputStream;

import org.nuxeo.common.utils.ZipUtils;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Blob;
import org.nuxeo.ecm.automation.client.model.Blobs;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.nuxeo.ecm.automation.client.model.PropertyList;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.springframework.stereotype.Service;
@Service
public class NuxeoServiceImpl implements NuxeoService{
	
	private String defaultCondition = "AND (ecm:mixinType != 'HiddenInNavigation') AND (ecm:currentLifeCycleState != 'deleted') ";
	
	@Override
	public Documents getList(NuxeoResource nuxeoResource) throws Exception{
		Session session = nuxeoResource.getSession();
		Document root = (Document) session
							.newRequest(DocumentService.FetchDocument)
							.set("value", nuxeoResource.getIntranetPath())
							.execute();
		if(root != null){
			nuxeoResource.setDocTitle(root.getTitle());
			String query = "SELECT * FROM Document WHERE ecm:parentId = '"+root.getId()+"'" + defaultCondition + " ORDER BY dc:title";
			return queryList(nuxeoResource, query);
		}
		return null;
	}
	
	@Override
	public PaginableDocuments news(NuxeoResource nuxeoResource, int pageSize) throws Exception{
		String query = "SELECT * FROM Document WHERE (ecm:primaryType <> 'Folder') AND (ecm:path STARTSWITH \"" 
				+ nuxeoResource.getIntranetPath() + "\")"
				+ defaultCondition +" ORDER BY dc:modified DESC ";
		Session session = nuxeoResource.getSession();
		return (PaginableDocuments)session
				.newRequest("Document.PageProvider")
				.setHeader(Constants.HEADER_NX_SCHEMAS, "*")
		        .set("page", 0)
		        .set("pageSize",pageSize)
		        .set("query", query)
		        .execute();
	}
	
	@Override
	public Documents search(NuxeoResource nuxeoResource, String key) throws Exception {
		String query = "SELECT * FROM Document WHERE (ecm:fulltext = \"" + key
				+ "\") AND (ecm:isCheckedInVersion = 0) AND (ecm:path STARTSWITH \"" 
				+ nuxeoResource.getIntranetPath() + "\") "+ defaultCondition +" ORDER BY dc:modified DESC";
		return queryList(nuxeoResource, query);
	}
	
	private Documents queryList(NuxeoResource nuxeoResource, String query) throws Exception{
		Session session = nuxeoResource.getSession();
		Documents docs = (Documents) session
							.newRequest(DocumentService.Query)
							.setHeader(Constants.HEADER_NX_SCHEMAS, "*")
							.set("query", query)
							.execute();
		return docs;
	}
	
	@Override
	public Document getFileDocument(NuxeoResource nuxeoResource, String uid)
			throws Exception {
		Session session = nuxeoResource.getSession();
		// Get the file document where blob was attached
		Document doc = (Document) session
							.newRequest(DocumentService.FetchDocument)
							.setHeader(Constants.HEADER_NX_SCHEMAS, "*")
							.set("value", uid)
							.execute();
		return doc;
	}
	
	@Override
	public FileDownloadAttr fileDownload(NuxeoResource nuxeoResource, String uid) throws Exception {
		Session session = nuxeoResource.getSession();
		// Get the file document where blob was attached
		Document doc = (Document) session
							.newRequest(DocumentService.FetchDocument)
							.setHeader(Constants.HEADER_NX_SCHEMAS, "*")
							.set("value", uid)
							.execute();
		
		return (doc.getType().equals("Note")) ? noteDown(doc, nuxeoResource) : fileDown(doc, nuxeoResource);
	}
	
	private FileDownloadAttr fileDown(Document doc, NuxeoResource nuxeoResource) throws Exception{
		
    	// get the file content property
		PropertyMap map = doc.getProperties().getMap("file:content");
		// get the data URL
		String path = map.getString("data");		
		 
		// download the file from its remote location
		FileBlob blob = (FileBlob) nuxeoResource.getSession().getFile(path);
    	
		FileDownloadAttr fileAttr = new FileDownloadAttr();
		fileAttr.setMimeType(blob.getMimeType());
		fileAttr.setFileName(blob.getFileName());
		fileAttr.setInStream(new FileInputStream(blob.getFile()));
		fileAttr.setFileLenth(blob.getLength());
		
		return fileAttr;
    }
    
    private FileDownloadAttr noteDown(Document doc, NuxeoResource nuxeoResource) throws Exception{
    	FileDownloadAttr fileAttr = new FileDownloadAttr();
    	
    	String content = (String)doc.getProperties().get("note:note");
		String fileName = (String)doc.getProperties().get("dc:title");
		String mime_type = (String)doc.getProperties().get("note:mime_type");
    	
    	if(doc.getProperties().getList("files:files").size() > 0){
    		
    		Blobs blobs = (Blobs)nuxeoResource.getSession().newRequest("Blob.GetList").setInput(doc).execute();
    		File file = File.createTempFile("nxops-createzip-", ".tmp");
    		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
			try {
				   HashSet<String> names = new HashSet<String>(); // use a set to avoid zipping entries with same names
				   int cnt = 1;
				   for (Blob blob : blobs) {
				       String entry = blob.getFileName();
				       if (!names.add(entry)) {
				           entry = "renamed_"+(cnt++)+"_"+entry;
				       }
				       InputStream in = blob.getStream();
				       try {
				    	   ZipUtils._zip(entry, in, out);
				       } finally {
				           in.close();
				       }
				   }
				   
		    		if(!fileName.contains(".")){
		    			fileName += "." + getNoteFileType(mime_type);
		    		}
		        	
		        	// convert String into InputStream
		        	InputStream in = new ByteArrayInputStream(content.getBytes());
		        	try {
			    	   ZipUtils._zip(fileName, in, out);
			       } finally {
			           in.close();
			       }
			} finally {
				out.finish();
				out.close();
			}
        	fileAttr.setMimeType("application/zip");
    		fileAttr.setFileName(doc.getTitle() + ".zip");
    		fileAttr.setInStream(new FileInputStream(file));
    		fileAttr.setFileLenth((int)file.length());
    		
    	}else{
    		if(!fileName.contains(".")){
    			fileName += "." + getNoteFileType(mime_type);
    		}
    		
        	fileAttr.setMimeType(mime_type);
    		fileAttr.setFileName(fileName);
    		fileAttr.setInStream(new ByteArrayInputStream(content.getBytes()));
    		fileAttr.setFileLenth(content.length());
    	}
    	
    	return fileAttr;
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
}
