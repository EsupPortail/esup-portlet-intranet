<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<portlet:defineObjects/>
<portlet:renderURL var="homeUrl">
	<portlet:param name="action" value="list" />
</portlet:renderURL>
<portlet:actionURL var="searchUrl">
	<portlet:param name="action" value="search" />
</portlet:actionURL>
<portlet:renderURL var="newUrl">
	<portlet:param name="action" value="new" />
</portlet:renderURL>
<portlet:renderURL var="treeUrl">
	<portlet:param name="action" value="tree" />
</portlet:renderURL>

<c:set var="n"><portlet:namespace/></c:set>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<ul class="nav nav-pills">
<c:choose>
	<c:when test="${mode eq 'list' || mode eq 'search' || mode eq null}">
		<li class="active"><a href="${homeUrl}">Home</a></li>
		<li><a href="${newUrl}">New Documents</a></li>
		<li><a href="${treeUrl}">Tree</a></li>
	</c:when>
	<c:when test="${mode eq 'new'}">
		<li><a href="${homeUrl}">Home</a></li>
		<li class="active"><a href="${newUrl}">New Documents</a></li>
		<li><a href="${treeUrl}">Tree</a></li>
	</c:when>
	<c:otherwise>
		<li><a href="${homeUrl}">Home</a></li>
		<li><a href="${newUrl}">New Documents</a></li>
		<li class="active"><a href="${treeUrl}">Tree</a></li>
	</c:otherwise>
</c:choose>
</ul>	