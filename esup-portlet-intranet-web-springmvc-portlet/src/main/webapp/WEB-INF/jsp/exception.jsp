<%@ include file="/WEB-INF/jsp/include.jsp"%>

<div class="portlet-title">
  <h2>
    <spring:message code="exception.title"/>
  </h2>
</div>

<div class="portlet-section">
    <p class="text-error">
        <spring:message code="preferences.incorrect"/><br/>
        <spring:message code="exception.contact.admin"/>
    </p>  
    <span class="exceptionMessage">
        <spring:message code="exception.details"/> : ${exceptionMessage}
    </span>
</div>

