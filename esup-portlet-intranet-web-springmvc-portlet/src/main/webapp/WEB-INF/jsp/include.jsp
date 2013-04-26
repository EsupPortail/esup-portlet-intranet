<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>

<%@ taglib prefix="esup" uri="/WEB-INF/esup-intranet-taglib.tld" %>
<portlet:defineObjects/>
<c:set var="n"><portlet:namespace/></c:set>

<rs:resourceURL var="jqueryJS" value="/js/jquery-1.9.1.min.js"/>
<rs:resourceURL var="jqueryMobileJS" value="/js/jquery.mobile-1.3.1.min.js"/>
<rs:resourceURL var="jqueryMobileCSS" value="/css/jquery.mobile-1.3.1.min.css"/>
<rs:resourceURL var="bootstrapCSS" value="/css/bootstrap.css"/>
<rs:resourceURL var="esupIntranetCSS" value="/css/esup-intranet.css"/>

<portlet:actionURL var="searchUrl" >
	<portlet:param name="action" value="search" />
</portlet:actionURL>
<portlet:renderURL var="searchFormUrl" >
	<portlet:param name="action" value="search-form" />
</portlet:renderURL>
<portlet:renderURL var="homeUrl" portletMode="VIEW">
	<portlet:param name="action" value="list" />
</portlet:renderURL>
<portlet:renderURL var="newUrl" portletMode="VIEW">
	<portlet:param name="action" value="new" />
</portlet:renderURL>
<portlet:renderURL var="editPreferencesUrl" portletMode="EDIT">
</portlet:renderURL>
<portlet:actionURL var="editUrl">
	<portlet:param name="action" value="edit" />
</portlet:actionURL>

<link type="text/css" rel="stylesheet" href="${bootstrapCSS }" />

