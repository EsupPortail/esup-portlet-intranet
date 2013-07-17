package org.esup.portlet.intranet.domain.nuxeo;

import java.io.InputStream;

public class FileDownloadAttr {
	private InputStream inStream;
	private int fileLenth;
	private String mimeType;
	private String fileName;
	private boolean hasContent = true;
	public InputStream getInStream() {
		return inStream;
	}
	public void setInStream(InputStream inStream) {
		this.inStream = inStream;
	}
	public int getFileLenth() {
		return fileLenth;
	}
	public void setFileLenth(int fileLenth) {
		this.fileLenth = fileLenth;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean hasContent() {
		return hasContent;
	}
	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}
	
}
