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

