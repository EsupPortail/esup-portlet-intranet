<c:choose>
    <c:when test="${mode eq 'list'}">
        <c:if test="${not empty breadcrumb}">
            <ul class="breadcrumb">
                <c:forEach var="b" items="${breadcrumb}" varStatus="status">
                
                	<li><a href="<portlet:renderURL>
                                    <portlet:param name="action" value="list" />
                                    <portlet:param name="intranetPath" value="${b.path}" />
                                    </portlet:renderURL>">
                        <c:out value="${b.title}" /></a>
                                    
                        <c:if test="${not status.last}">
                        <span class="divider">></span>
                        </c:if>
                   </li>
                </c:forEach>
            </ul>
        </c:if>
    </c:when>
</c:choose>