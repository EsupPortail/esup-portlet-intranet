package org.esup.portlet.intranet.web;

import java.util.ArrayList;
import java.util.List;

public class Breadcrumb {
	private List<HtmlCode> pathList = new ArrayList<HtmlCode>();
	
	public List<HtmlCode> getPathList() {
		return pathList;
	}

	public void setBreadcrumb(String rootPath, String intranetpath) {
		int slashIndex = rootPath.lastIndexOf('/');
		int totalIndex = rootPath.length() - 1;
		
		if(slashIndex > 0 && slashIndex == totalIndex){
			rootPath = rootPath.substring(0, totalIndex);
			slashIndex = rootPath.lastIndexOf('/');
		}
		String child = rootPath.substring(slashIndex + 1);
		pathList.add(new HtmlCode(child, null));
		
		if(!intranetpath.equals(rootPath)){
			
			String rest = intranetpath.substring(rootPath.length()+1);
			String[] tmp = rest.split("/");
			if(tmp != null){
				String curPath = rootPath;
				for(String s : tmp){
					curPath +=  "/" + s;
					pathList.add(new HtmlCode(s, curPath));
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
