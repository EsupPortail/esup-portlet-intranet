package org.esup.portlet.intranet.web;

import java.util.ArrayList;
import java.util.List;

import org.esup.portlet.intranet.domain.nuxeo.NuxeoResource;
import org.esup.portlet.intranet.domain.nuxeo.NuxeoService;
import org.nuxeo.ecm.automation.client.model.Document;

public class Breadcrumb {
	private List<HtmlCode> pathList = new ArrayList<HtmlCode>();
	
	public List<HtmlCode> getPathList() {
		return pathList;
	}

	public void setBreadcrumb(NuxeoResource nuxeoResource, NuxeoService nuxeoService) throws Exception {
		
		pathList.clear();
		
		String rootPath = nuxeoResource.getRootPath();
		String intranetpath = nuxeoResource.getIntranetPath();
		
		int lastSlashIndex = rootPath.lastIndexOf('/');
		int rootPathLenth = rootPath.length() - 1;
		
		if(lastSlashIndex > 0 && lastSlashIndex == rootPathLenth) {
			rootPath = rootPath.substring(0, rootPathLenth);
			lastSlashIndex = rootPath.lastIndexOf('/');
		}
		
		final boolean isRoot = rootPath.equals("/");
		final String firstPath = isRoot ? 
				rootPath : rootPath.substring(lastSlashIndex + 1);
		pathList.add(new HtmlCode(firstPath, null));
		
		if(!intranetpath.equals(rootPath)) {
			
			final String pathForAdd = isRoot ? 
					intranetpath.substring(1) : intranetpath.substring(rootPath.length() + 1);
			final String[] tmp = pathForAdd.split("/");
			if(tmp != null) {
				String basePath = isRoot ? "" : rootPath;
				for(String lastFolderName : tmp) {
					basePath +=  "/" + lastFolderName;
					pathList.add(getNxResource(nuxeoService
							.getDocument(basePath, nuxeoResource.getSession())));
				}
			}
		}
	}
		
	private HtmlCode getNxResource(Document document) {
		return new HtmlCode(document.getTitle(), document.getPath());
	}
	
	public class HtmlCode {
		private String title;
		private String path;
		
		public String getTitle() {
			return title;
		}
		public String getPath() {
			return path;
		}
		HtmlCode(String title, String path) {
			this.title = title;
			this.path = path;
		}
	}

}
