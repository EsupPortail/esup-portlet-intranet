<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script type="text/javascript" src="${jqueryJS}"/></script>

<%@ include file="/WEB-INF/jsp/menu.jsp"%>
<%@ include file="/WEB-INF/jsp/breadcrumb.jsp"%>

<table class="table table-hover">
	<tr>
		<th><spring:message code="list.title" /></th>
		<th><spring:message code="list.modified" /></th>
		<th><spring:message code="list.creator" /></th>
		<th><spring:message code="list.desc" /></th>
	</tr>
	<tbody>
		<c:if test="${not empty docs}">
		<c:forEach var="doc" items="${docs}">
			<tr>
				<td>
					<img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" >
					<c:choose>
						<c:when test="${doc.type == 'File' || doc.type == 'Picture' }">
							<a href="
								<portlet:resourceURL >
									<portlet:param name="action" value="file" />
									<portlet:param name="uid" value="${doc.id}" />
								</portlet:resourceURL>
							">${doc.title}</a>
						</c:when>
						<c:otherwise>
							<a href="
								<portlet:renderURL>
									<portlet:param name="action" value="list" />
									<portlet:param name="intranetPath" value="${doc.path}" />
								</portlet:renderURL>
							">${doc.title}</a>
						</c:otherwise>
					</c:choose></td>
				<td> ${esup:getLastModifiedDate(doc.properties)}</td>
				<td>${esup:getValue(doc.properties, 'dc:creator')}</td>
				<td>${esup:getValue(doc.properties, 'dc:description')}</td>
			</tr>
		</c:forEach>
		</c:if>
	</tbody>
</table>
<!-- List view end -->						
