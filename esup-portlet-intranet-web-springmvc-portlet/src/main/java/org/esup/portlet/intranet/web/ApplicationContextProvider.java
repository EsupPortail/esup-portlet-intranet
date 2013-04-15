package org.esup.portlet.intranet.web;

import java.lang.ref.WeakReference;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	private static WeakReference<ApplicationContext> CTX;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CTX = new WeakReference<ApplicationContext>(applicationContext);
	}

	public static ApplicationContext getAppContext() {
	    return CTX.get();
	}
}
