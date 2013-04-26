<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link type="text/css" rel="stylesheet" href="${jqueryMobileCSS }" />
<link type="text/css" rel="stylesheet" href="${esupIntranetCSS }" />

<%if(!request.getAttribute("javax.portlet.request").toString().contains("org.jasig.portal.portlet.container")){%>
	<script type="text/javascript" src="${jqueryJS}"/></script>
	<script type="text/javascript" src="${jqueryMobileJS}"/></script>
	<div data-role="page">
<%}%>

<div data-role="content"><!-- content/ -->
	<div class="ui-body-d ui-body" ><!-- container/ -->
	<h3>Nuxeo Intranet Documents</h3>
    <div data-role="navbar" class="nav-glyphish">
    	<ul>
       	 	<c:choose>
        		<c:when test="${mode eq 'list'}">
					<li><a href="${homeUrl}" data-icon="custom" id="macbook" class="ui-btn-active">Intranet</a></li>
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>		
				</c:when>
				<c:when test="${mode eq 'new'}">
					<li><a href="${homeUrl}" data-icon="custom" id="macbook">Intranet</a></li>
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical" class="ui-btn-active"><spring:message code="menu.new" /></a></li>		
				</c:when>
				<c:otherwise>
					<li><a href="${homeUrl}" data-icon="custom" id="macbook">Intranet</a></li>
					<li><a href="${searchFormUrl}" data-icon="custom" id="magnify" class="ui-btn-active"><spring:message code="button.search" /></a></li>
					<li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>
				</c:otherwise>
			</c:choose>
		</ul>	
    </div><!-- /navbar -->