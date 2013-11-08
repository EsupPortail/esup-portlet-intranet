<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >
    <fieldset>
    <legend>Preferences</legend>
    <div class="row-fluid">
        <c:if test="${not nuxeoHost_readOnly}">
            <div class="control-group">
                <label class="control-label" for="nuxeoHost">NuxeoHost</label>
                <div class="controls">
                    <input type="text" id="nuxeoHost" name="nuxeoHost" value="${nuxeoHost}" class="span12" required="required" placeholder="Exemple: http://my.nuxeo.server/nuxeo/">
                </div>
            </div>
        </c:if>
        <c:if test="${not intranetPath_readOnly}">
            <div class="control-group">
                <label class="control-label" for="intranetPath">IntranetPath</label>
                <div class="controls">
                    <input type="text" id="intranetPath" name="intranetPath" value='${intranetPath}' class="span12" required="required" placeholder="Exemple: /default-domain/workspaces/something" />
                </div>
            </div>
        </c:if>
        <div class="form-actions" style="padding-left: 0;">
            <center>
                <input type="submit" class="btn btn-success" />
            </center>
        </div>
    </div>
    </fieldset>
</form:form>