<%@ include file="/WEB-INF/jsp/mobile_header.jsp"%>
<%@ include file="/WEB-INF/jsp/mobile_menu.jsp"%>    

    <form name="searchForm" method="post" action="${searchUrl}" >
		<input type="text" name="key" value="${key}"  />
		<button type="submit"><spring:message code="button.search" /></button>
	</form>
    
    <c:if test="${not empty docs}">
		<ul data-role="listview" data-inset="true">
			<c:forEach var="doc" items="${docs}" varStatus="stat" begin="0" end="${rowCnt-1}">
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
	<c:if test="${leftCnt > 0}">
		<div data-role="collapsible-set" data-theme="c" data-content-theme="d" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d" >
		    <div data-role="collapsible">
		        <h3>Show all (+ ${leftCnt})</h3>
		        <p>
					<ul data-role="listview" data-inset="false">
						<c:forEach var="doc" items="${docs}" varStatus="stat" begin="${rowCnt}">
							<c:choose>
								<c:when test="${esup:isFolder(doc)}">
									<li><a href="
										<portlet:renderURL>
											<portlet:param name="action" value="list" />
											<portlet:param name="intranetPath" value="${doc.path}" />
										</portlet:renderURL>
									"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" 
									      class="ui-li-icon ui-corner-none">${doc.title}</a></li>
								</c:when>
								<c:otherwise>
									
									<c:choose>
										<c:when test="${esup:hasFicher(doc.properties)}">
											<li data-icon="false"><a href="
												<portlet:resourceURL >
													<portlet:param name="action" value="file" />
													<portlet:param name="uid" value="${doc.id}" />
												</portlet:resourceURL>
											"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" 
											      class="ui-li-icon ui-corner-none">${doc.title}</a></li>
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
		        </p>
		    </div>
		</div>
	</c:if>

<%@ include file="/WEB-INF/jsp/mobile_footer.jsp"%>