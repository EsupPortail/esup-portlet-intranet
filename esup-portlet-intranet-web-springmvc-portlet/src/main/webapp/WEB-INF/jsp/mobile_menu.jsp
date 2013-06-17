<div data-role="navbar" class="nav-glyphish">
    <ul>
        <c:choose>
            <c:when test="${mode eq 'list'}">
                <li><a href="${homeUrl}" data-icon="custom" id="macbook" class="ui-btn-active"><spring:message code="menu.home" /></a></li>
                <li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>     
                <li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
            </c:when>
            <c:when test="${mode eq 'new'}">
                <li><a href="${homeUrl}" data-icon="custom" id="macbook"><spring:message code="menu.home" /></a></li>
                <li><a href="${newUrl}" data-icon="custom" id="medical" class="ui-btn-active"><spring:message code="menu.new" /></a></li>       
                <li><a href="${searchFormUrl}" data-icon="custom" id="magnify"><spring:message code="button.search" /></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${homeUrl}" data-icon="custom" id="macbook"><spring:message code="menu.home" /></a></li>
                <li><a href="${newUrl}" data-icon="custom" id="medical"><spring:message code="menu.new" /></a></li>
                <li><a href="${searchFormUrl}" data-icon="custom" id="magnify" class="ui-btn-active">
                        <spring:message code="button.search" /></a></li>
            </c:otherwise>
        </c:choose>
    </ul>   
</div><!-- /navbar -->