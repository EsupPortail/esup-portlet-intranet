<%@ include file="/WEB-INF/jsp/include.jsp"%>
<portlet:actionURL var="editUrl">
	<portlet:param name="action" value="edit" />
</portlet:actionURL>

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
