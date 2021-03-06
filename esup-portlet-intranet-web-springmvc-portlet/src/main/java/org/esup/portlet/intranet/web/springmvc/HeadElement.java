package org.esup.portlet.intranet.web.springmvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class HeadElement implements InitializingBean{
	private String name;
	private String href;
	private String rel;
	private String type;
	private String src;


	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}
	/**
	 * @param rel the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(name, "name attribute can't be null!");
		Assert.notNull(type, "type attribute can't be null!");
	}

}
