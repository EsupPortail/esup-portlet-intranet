<div class="row-fluid">
    <div class="span6">
        <ul class="nav nav-pills">
            <c:choose>
                <c:when test="${mode eq 'new'}">
                    <li>
                        <a href="${homeUrl}"><spring:message code="menu.home" /></a>
                    </li>
                    <li class="active">
                        <a href="${newUrl}"><spring:message code="menu.new" /></a>
                    </li>       
                </c:when>
                <c:otherwise>
                    <li class="active">
                        <a href="${homeUrl}"><spring:message code="menu.home" /></a>
                    </li>
                    <li>
                        <a href="${newUrl}"><spring:message code="menu.new" /></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <div class="span6">
        <div class="pull-right">
            <form:form name="searchForm" method="post" action="${searchUrl}" class="form-search">
                <div class="input-append">
                    <input type="text" name="key" class="span8 search-query" value="${key}"/>
                    <button type="submit" class="btn"><spring:message code="button.search" /></button>
                </div>
            </form:form>
        </div>
    </div>
</div>