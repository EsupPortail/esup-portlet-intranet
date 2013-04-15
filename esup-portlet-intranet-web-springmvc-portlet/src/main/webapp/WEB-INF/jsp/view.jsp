<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${mode eq 'list'}">
<c:if test="${not empty breadcrumb}">
	<ul class="breadcrumb">
		<c:forEach var="b" items="${breadcrumb}" varStatus="status">
			<c:if test="${not status.last}">
				<li><a href="
						<portlet:renderURL>
							<portlet:param name="action" value="list" />
							<portlet:param name="intranetPath" value="${b.path}" />
						</portlet:renderURL>"><c:out value="${b.title}" /></a>
						<span class="divider">></span>
				</li>
			</c:if>
			<c:if test="${status.last}">
				<li class="active">${b.title}</li>
			</c:if>
		</c:forEach>
	</ul>
</c:if>	
</c:when>
</c:choose>
<table align="right">
<tr><td>
<!-- List view start -->
<form:form name="catalogForm" method="post" action="${searchUrl}" class="form-inline">
	<input type="text" name="key" class="input-small" />
	<button type="submit" class="btn btn-small">Search</button>
</form:form>
</td>
</tr>
</table>
<table class="table table-hover">
	<thead>
		<tr>
			<th width="10"></th>
			<th>Titre</th>
			<th>Dernière modification</th>
			<th>Auteur</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty docs}">
		<c:forEach var="doc" items="${docs}">
		<c:set var="props"  value="${doc.properties}"/>
		
			<tr>
				<td><c:choose>
						<c:when test="${doc.type eq 'File'}">
							<i class="icon-download"></i>
						</c:when>
						<c:when test="${doc.type eq 'Picture'}">
							<i class="icon-eye-open"></i>
						</c:when>
						<c:when test="${doc.type eq 'Note'}">
							<i class="icon-eye-open"></i>
						</c:when>
						<c:otherwise>
							<i class="icon-folder-close"></i>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${doc.type == 'File' || doc.type == 'Picture'}">
							<a href="
								<portlet:resourceURL >
									<portlet:param name="action" value="file" />
									<portlet:param name="filePath" value="${doc.path}" />
								</portlet:resourceURL>
							">${doc.title}</a>
						</c:when>
						<c:otherwise>
							<a href="
								<portlet:renderURL>
									<portlet:param name="action" value="list" />
									<portlet:param name="intranetPath" value="${doc.path}" />
								</portlet:renderURL>
							">${doc.title}</a>
						</c:otherwise>
					</c:choose></td>
				<td> </td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach>
		</c:if>
	</tbody>
</table>
<!-- List view end -->						
									


