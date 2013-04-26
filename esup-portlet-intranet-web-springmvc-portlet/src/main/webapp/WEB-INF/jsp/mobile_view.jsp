<%@ include file="/WEB-INF/jsp/mobile_header.jsp"%>

    <c:choose>
		<c:when test="${mode eq 'list'}">
			<c:if test="${not empty breadcrumb}">
				<ul class="breadcrumb">
					<c:forEach var="b" items="${breadcrumb}" varStatus="status">
						<c:if test="${not status.last}">
							<li><a
								href="<portlet:renderURL>
										<portlet:param name="action" value="list" />
										<portlet:param name="intranetPath" value="${b.path}" />
										</portlet:renderURL>"><c:out
										value="${b.title}" /></a> <span class="divider">></span></li>
						</c:if>
						<c:if test="${status.last}">
							<li class="active">${b.title}</li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</c:when>
	</c:choose>
	
	<c:if test="${not empty docs}">
	<ul data-role="listview" data-inset="true">
		<c:forEach var="doc" items="${docs}">
			<c:choose>
			<c:when test="${doc.type == 'File' || doc.type == 'Picture'}">
				<li data-icon="false"><a href="
					<portlet:resourceURL >
						<portlet:param name="action" value="file" />
						<portlet:param name="filePath" value="${doc.path}" />
					</portlet:resourceURL>
				"><img src="<%=request.getContextPath()%>/img/filetype-icons/${esup:getImgFileName(doc.title)}" alt="pdf" class="ui-li-icon ui-corner-none">${doc.title}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="
					<portlet:renderURL>
						<portlet:param name="action" value="list" />
						<portlet:param name="intranetPath" value="${doc.path}" />
					</portlet:renderURL>
				"><img src="<%=request.getContextPath()%>/img/filetype-icons/folder.gif" alt="pdf" class="ui-li-icon ui-corner-none">${doc.title}</a></li>
			</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
	</c:if>
	
<%@ include file="/WEB-INF/jsp/mobile_footer.jsp"%>
