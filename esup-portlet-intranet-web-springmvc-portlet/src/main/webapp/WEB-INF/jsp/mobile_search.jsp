<%@ include file="/WEB-INF/jsp/mobile_header.jsp"%>
    
    <form:form name="searchForm" method="post" action="${searchUrl}" >
		<input type="text" name="key"  />
		<button type="submit"  ><spring:message code="button.search" /></button>
	</form:form>
    
    <c:if test="${not empty docs}">
		<ul data-role="listview" data-inset="true">
			<c:forEach var="doc" items="${docs}" varStatus="stat" begin="0" end="${rowCnt-1}">
				<li data-icon="false"><a href="
						<portlet:resourceURL >
							<portlet:param name="action" value="file" />
							<portlet:param name="filePath" value="${doc.path}" />
						</portlet:resourceURL>
					"><img src="<%=request.getContextPath()%>/img/filetype-icons/${esup:getImgFileName(doc.title)}" alt="pdf" 
						class="ui-li-icon ui-corner-none">${doc.title}</a></li>
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
							<li data-icon="false"><a href="
							<portlet:resourceURL >
								<portlet:param name="action" value="file" />
								<portlet:param name="filePath" value="${doc.path}" />
							</portlet:resourceURL>
						"><img src="<%=request.getContextPath()%>/img/filetype-icons/${esup:getImgFileName(doc.title)}" alt="pdf" 
							class="ui-li-icon ui-corner-none">${doc.title}</a></li>
						</c:forEach>
					</ul>
		        </p>
		    </div>
		</div>
	</c:if>

<%@ include file="/WEB-INF/jsp/mobile_footer.jsp"%>