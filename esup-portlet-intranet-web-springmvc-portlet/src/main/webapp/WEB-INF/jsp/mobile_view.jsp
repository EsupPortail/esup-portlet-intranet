<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/menu.jsp"%>
<%@ include file="/WEB-INF/jsp/breadcrumb.jsp"%>

<table class="table table-hover">
	<tr>
		<th width="10"></th>
		<th><spring:message code="list.title" /></th>
		<th><spring:message code="list.modified" /></th>
		<th><spring:message code="list.creator" /></th>
		<th><spring:message code="list.desc" /></th>
	</tr>
	<tbody>
		<c:if test="${not empty docs}">
		<c:forEach var="doc" items="${docs}">
		<c:set var="props"  value="${doc.properties}"/>
		
			<tr>
				<td><c:choose>
						<c:when test="${doc.type eq 'File'}">
							<i class="icon-download"></i>
						</c:when>
						<c:when test="${doc.type eq 'Picture'}">
							<i class="icon-eye-open"></i>
						</c:when>
						<c:when test="${doc.type eq 'Note'}">
							<i class="icon-eye-open"></i>
						</c:when>
						<c:otherwise>
							<i class="icon-folder-close"></i>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${doc.type == 'File' || doc.type == 'Picture'}">
							<a href="
								<portlet:resourceURL >
									<portlet:param name="action" value="file" />
									<portlet:param name="filePath" value="${doc.path}" />
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
