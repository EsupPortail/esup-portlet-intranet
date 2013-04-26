package org.esup.portlet.intranet.web.springmvc;

import java.util.List;

import javax.portlet.MimeResponse;
import javax.portlet.PortalContext;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.springframework.web.portlet.DispatcherPortlet;
import org.w3c.dom.Element;

public class IntranetDispatcherPortlet extends DispatcherPortlet {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void doHeaders(RenderRequest request, RenderResponse response) {
		super.doHeaders(request, response);
		if(request.getPortalContext().getProperty(PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT) != null){
			//add css en js to portal Header
			@SuppressWarnings("unchecked")
			List<HeadElement> headElements = (List<HeadElement>) ApplicationContextHolder.getContext().getBean("headElements");
			for (HeadElement headElement : headElements) {
				Element element = response.createElement(headElement.getName());
				String type = headElement.getType();
				if(type.equals("text/css")){
					element.setAttribute("href", response.encodeURL(request.getContextPath() + headElement.getHref()));
					element.setAttribute("rel", headElement.getHref());
					element.setAttribute("type", headElement.getType());
					response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
				}else if(type.equals("text/javascript")){
					element.setAttribute("src", response.encodeURL(request.getContextPath() + headElement.getSrc()));
					element.setAttribute("type", headElement.getType());
					response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
				}
			}
		}
	}
}