<c:choose>
    <c:when test="${mode eq 'new'}">
        <a href="${homeUrl}" class="btn btn-small disabled"><spring:message code="menu.home" /></a>
        <a href="${newUrl}" class="btn btn-small btn-primary disabled"><spring:message code="menu.new" /></a>       
    </c:when>
    <c:otherwise>
        <a href="${homeUrl}" class="btn btn-small btn-primary disabled"><spring:message code="menu.home" /></a>
        <a href="${newUrl}" class="btn btn-small disabled"><spring:message code="menu.new" /></a>
    </c:otherwise>
</c:choose>

<table align="right">
    <tr>
        <td>
            <form:form name="searchForm" method="post" action="${searchUrl}" class="form-inline">
                <input type="text" name="key" class="input" value="${key}"/>
                <button type="submit" class="btn btn-small"><spring:message code="button.search" /></button>
            </form:form>
        </td>
    </tr>
</table>