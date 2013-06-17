<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form class="form-horizontal" name="editForm" method="post" action="${editUrl}" >

<table style="width: 100%">
     <tr>
       <th colspan="2"><legend>Preferences Form</legend></th>
     </tr>
     <tr>
       <td><label>nuxeoHost<c:if test="${nuxeoHost_readOnly eq 'disabled'}"><br/>(readonly)</c:if></label></td>
       <td><input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required ${nuxeoHost_readOnly}/></td>
     </tr>
     <tr>
       <td><label>intranetPath<c:if test="${intranetPath_readOnly eq 'disabled'}"><br/>(readonly)</c:if></label></td>
       <td><input type="text" name="intranetPath" value='${intranetPath}' 
       class="input-xxlarge" style="width: 533px" required ${intranetPath_readOnly}/></td>
     </tr>
     <tr>
       <td colspan="2"><center><button type="submit" class="btn btn-small btn-success" >Submit</button></center></td>
     </tr>
</table>
</form:form>
