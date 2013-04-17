<%@ include file="/WEB-INF/jsp/include.jsp"%>
<portlet:actionURL var="editUrl">
	<portlet:param name="action" value="edit" />
</portlet:actionURL>

<p class="text-info">${message}</p>

<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >
<div class="control-group">
	nuxeoHost : <input type="text" name="nuxeoHost" value="${nuxeoHost}" />
</div>
<div class="control-group">
	intranetPath : <input type="text" name="intranetPath" value="${intranetPath}" />
</div>
<div class="control-group">
	nuxeoPortalAuthSecret : <input type="text" name="nuxeoPortalAuthSecret" value="${nuxeoPortalAuthSecret}" /><br/>
</div>
	<button type="submit" class="btn btn-small">Summit</button>
</form:form>
