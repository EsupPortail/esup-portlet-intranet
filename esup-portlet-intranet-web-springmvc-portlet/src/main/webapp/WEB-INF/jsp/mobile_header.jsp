<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="java.nio.charset.Charset"%>
<link type="text/css" rel="stylesheet" href="${jqueryMobileCSS }" />
<link type="text/css" rel="stylesheet" href="${esupIntranetCSS }" />
<c:if test="${not isuPortal}">
	<script type="text/javascript" src="${jqueryJS}"/></script>
	<script type="text/javascript" src="${jqueryMobileJS}"/></script>
	<div data-role="page">
</c:if>
<div data-role="content"><!-- content/ -->

	<div class="ui-body-d ui-body" ><!-- container/ -->
    <div data-role="navbar" class="nav-glyphish">
    	<ul>
       	 	<c:choose>
        		<c:when test="${mode eq 'list'}">
					<li><a href="${homeUrl}" data-icon="custom" id="macbook" class="ui-btn-active"><spring:message code="menu.home" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>		
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
				</c:when>
				<c:when test="${mode eq 'new'}">
					<li><a href="${homeUrl}" data-icon="custom" id="macbook"><spring:message code="menu.home" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical" class="ui-btn-active"><spring:message code="menu.new" /></a></li>		
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${homeUrl}" data-icon="custom" id="macbook"><spring:message code="menu.home" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify" class="ui-btn-active"><spring:message code="button.search" /></a></li>
				</c:otherwise>
			</c:choose>
		</ul>	
    </div><!-- /navbar -->