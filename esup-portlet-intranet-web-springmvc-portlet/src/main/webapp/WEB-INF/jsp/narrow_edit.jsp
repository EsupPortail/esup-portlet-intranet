<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >
<div class="control-group">
	nuxeoHost : <input type="text" name="nuxeoHost" value="${nuxeoHost}" />
</div>
<div class="control-group">
	intranetPath : <input type="text" name="intranetPath" value="${intranetPath}" />
</div>
	<button type="submit" class="btn btn-small">Summit</button>
</form:form>
