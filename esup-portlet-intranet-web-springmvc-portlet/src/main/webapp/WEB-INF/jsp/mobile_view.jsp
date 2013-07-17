<%@ include file="/WEB-INF/jsp/mobile_header.jsp"%>
<%@ include file="/WEB-INF/jsp/mobile_menu.jsp"%>
<%@ include file="/WEB-INF/jsp/breadcrumb.jsp"%>   

<c:if test="${not empty docs}">
	<ul data-role="listview" data-inset="true">
		<c:forEach var="doc" items="${docs}">
			<c:choose>
				<c:when test="${esup:isFolder(doc)}">
					<li><a href="<portlet:renderURL>
									<portlet:param name="action" value="list" />
									<portlet:param name="intranetPath" value="${doc.path}" />
								 </portlet:renderURL>
								"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" class="ui-li-icon ui-corner-none">${doc.title}
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${esup:hasFicher(doc.properties)}">
							<li data-icon="false">
								<a href="<portlet:resourceURL >
											<portlet:param name="action" value="file" />
											<portlet:param name="uid" value="${doc.id}" />
										</portlet:resourceURL>
										"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" class="ui-li-icon ui-corner-none">${doc.title}
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li data-icon="false"> <img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" class="ui-li-icon ui-corner-none">
								 ${doc.title} <small>(<spring:message code="file.no.attachment" />)</small>
							</li>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</c:if>
	
<%@ include file="/WEB-INF/jsp/mobile_footer.jsp"%>
