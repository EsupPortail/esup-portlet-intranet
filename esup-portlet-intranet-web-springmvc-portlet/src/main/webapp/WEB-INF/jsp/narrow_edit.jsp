<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >

<table style="width: 100%">
     <tr>
       <th colspan="2">Preferences Form</th>
     </tr>
     
     <c:if test="${not nuxeoHost_readOnly}">
	     <tr>
	       <td><label>nuxeoHost</label></td>
	       <td><input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required /></td>
	     </tr>
     </c:if>
     <c:if test="${not intranetPath_readOnly}">
	     <tr>
	       <td><label>intranetPath</label></td>
	       <td><input type="text" name="intranetPath" value='${intranetPath}' class="input-xxlarge" style="width: 533px" required /></td>
	     </tr>
     </c:if>
     <tr>
       <td colspan="2"><center><button type="submit" class="btn btn-small btn-success" >Submit</button></center></td>
     </tr>
</table>
</form:form>
