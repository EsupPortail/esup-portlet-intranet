<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link type="text/css" rel="stylesheet" href="${jqueryMobileCSS }" />
<link type="text/css" rel="stylesheet" href="${esupIntranetCSS }" />

<c:if test="${not isuPortal}">
	<script type="text/javascript" src="${jqueryJS}"/></script>
	<script type="text/javascript" src="${jqueryMobileJS}"/></script>
	<div data-role="page">
</c:if>

<div data-role="content"><!-- content/ -->
<div class="ui-body-d ui-body" ><!-- container/ -->
