<c:choose>
    <c:when test="${mode eq 'list'}">
        <c:if test="${not empty breadcrumb}">
            <ul class="breadcrumb">
                <c:forEach var="b" items="${breadcrumb}"
                    varStatus="status">
                    <c:choose>
                        <c:when test="${not status.last}">
                            <li><a href="<portlet:renderURL>
                                        <portlet:param name="action" value="list" />
                                        <portlet:param name="intranetPath" value="${b.path}" />
                                        </portlet:renderURL>">
                                    <c:out value="${b.title}" />
                            </a>
                            <span class="divider">></span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="active">
                                <c:out value="${b.title}" />
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </c:if>
    </c:when>
</c:choose>