<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link type="text/css" rel="stylesheet" href="${jqueryMobileCSS}" />
<link type="text/css" rel="stylesheet" href="${esupIntranetCSS}" />

<%if(!request.getAttribute("javax.portlet.request").toString().contains("org.jasig.portal.portlet.container")){%>
	<script type="text/javascript" src="${jqueryJS}"/></script>
	<script type="text/javascript" src="${jqueryMobileJS}"/></script>
	<div data-role="page">
<%}%>

<div data-role="content" data-position="inline"><!-- content/ -->
	<div class="ui-body-d ui-body" ><!-- container/ -->
	<h3>Portlet Preferences are empty.</h3>
	<p class="text-info">Please go to '<a href="${editPreferencesUrl}">EDIT</a>' mode</p>
	</div>
</div>

<div data-role="footer" class="ui-bar" data-position="fixed">
    <a href="${editPreferencesUrl}" data-icon="plus">EDIT mode</a>
</div>

<%if(!request.getAttribute("javax.portlet.request").toString().contains("org.jasig.portal.portlet.container")){%>
	</div>
<%}%>