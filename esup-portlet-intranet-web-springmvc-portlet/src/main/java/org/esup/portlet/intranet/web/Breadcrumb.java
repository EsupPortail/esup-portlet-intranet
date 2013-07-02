package org.esup.portlet.intranet.web;

import java.util.ArrayList;
import java.util.List;

import org.esup.portlet.intranet.domain.nuxeo.NuxeoResource;

public class Breadcrumb {
	private List<HtmlCode> pathList = new ArrayList<HtmlCode>();
	
	public List<HtmlCode> getPathList() {
		return pathList;
	}

	public void setBreadcrumb(NuxeoResource nuxeoResource) {
		
		pathList.clear();
		
		String rootPath = nuxeoResource.getRootPath();
		String intranetpath = nuxeoResource.getIntranetPath();
		
		int lastSlashIndex = rootPath.lastIndexOf('/');
		int rootPathLenth = rootPath.length() - 1;
		
		if(lastSlashIndex > 0 && lastSlashIndex == rootPathLenth){
			rootPath = rootPath.substring(0, rootPathLenth);
			lastSlashIndex = rootPath.lastIndexOf('/');
		}
		
		String firstPath = rootPath.substring(lastSlashIndex + 1);
		pathList.add(new HtmlCode(firstPath, null));
		
		if(!intranetpath.equals(rootPath)){
			
			String pathForAdd = intranetpath.substring(rootPath.length()+1);
			String[] tmp = pathForAdd.split("/");
			if(tmp != null){
				String basePath = rootPath;
				for(String lastFolderName : tmp){
					basePath +=  "/" + lastFolderName;
					pathList.add(new HtmlCode(nuxeoResource.getDocTitle(), basePath));
				}
			}
		}
	}
	
	public class HtmlCode{
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
