<%@ include file="/WEB-INF/template/header.jsp" %>
<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("schema") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/facilitydata/schema.list">
			<spring:message code="facilitydata.manage-form-schema"/>
		</a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("question") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/facilitydata/question.list">
			<spring:message code="facilitydata.manage-question"/>
		</a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("manage") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/facilitydata/manage.form">
			<spring:message code="facilitydata.view-enter"/>
		</a>
	</li>
</ul>