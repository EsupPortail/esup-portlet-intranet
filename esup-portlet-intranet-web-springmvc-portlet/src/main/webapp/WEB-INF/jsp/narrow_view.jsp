<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script type="text/javascript" src="${jqueryJS}"/></script>

<%@ include file="/WEB-INF/jsp/menu.jsp"%>
<%@ include file="/WEB-INF/jsp/breadcrumb.jsp"%>

<table class="table table-hover">
    <thead>
    	<tr>
    		<th><spring:message code="list.title" /></th>
    		<th><spring:message code="list.creator" /></th>
    	</tr>
    </thead>
	<tbody>
		<c:if test="${not empty docs}">
		<c:forEach var="doc" items="${docs}">
			<tr>
				<td>
                    <c:set var="iconName" value="${esup:getImgFileName(doc.properties)}" />
                    <c:if test="${iconName != null && iconName != ''}">
					   <img src="<%=request.getContextPath()%>/img${iconName}" >
                    </c:if>
					<c:choose>
						<c:when test="${esup:isFolder(doc)}">
							<a href="
								<portlet:renderURL>
									<portlet:param name="action" value="list" />
									<portlet:param name="intranetPath" value="${doc.path}" />
								</portlet:renderURL>
							">${doc.title}</a>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${esup:hasFicher(doc.properties)}">
									<a href="
										<portlet:resourceURL >
											<portlet:param name="action" value="file" />
											<portlet:param name="uid" value="${doc.id}" />
										</portlet:resourceURL>
									">${doc.title}</a>
								</c:when>
								<c:otherwise>
									${doc.title} <small>(<spring:message code="file.no.attachment" />)</small>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose></td>
				<td>${esup:getValue(doc.properties, 'dc:creator')}</td>
			</tr>
		</c:forEach>
		</c:if>
		<c:if test="${not empty noResultMsg}">
			<tr>
				<td colspan="2">${noResultMsg}</td>
			</tr>
		</c:if>
	</tbody>
</table>
