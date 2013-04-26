<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link type="text/css" rel="stylesheet" href="${jqueryMobileCSS }" />
<link type="text/css" rel="stylesheet" href="${esupIntranetCSS }" />
<%if(!request.getAttribute("javax.portlet.request").toString().contains("org.jasig.portal.portlet.container")){%>
	<script type="text/javascript" src="${jqueryJS}"/></script>
	<script type="text/javascript" src="${jqueryMobileJS}"/></script>
	<div data-role="page">
<%}%>
<div data-role="content" data-position="inline">

	<div class="ui-body-d ui-body" >
		<h3>Nuxeo Intranet Documents</h3>
		
		<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >
			<c:if test="${not empty nuxeoHost}">
				<div class="control-group">
					nuxeoHost : <input type="text" name="nuxeoHost" value="${nuxeoHost}" />
				</div>
			</c:if>
			<c:if test="${not empty intranetPath}">
				<div class="control-group">
					intranetPath : <input type="text" name="intranetPath" value="${intranetPath}" />
				</div>
			</c:if>
			<button type="submit" class="btn btn-small">Summit</button>
		</form:form>
	
	</div>
</div>
<div data-role="footer" class="ui-bar" data-position="fixed">
    <a href="<portlet:renderURL portletMode="VIEW"/>" data-icon=home>VIEW mode</a>
</div>
<%if(!request.getAttribute("javax.portlet.request").toString().contains("org.jasig.portal.portlet.container")){%>
	</div>
<%}%>
