<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/form.list"/>

<script type="text/javascript">
    $j(document).ready(function() {
        $j('#formList').dataTable({
            "bPaginate": true,
            "bLengthChange": false,
            "bFilter": false,
            "bSort": false,
            "bInfo": false,
            "bAutoWidth": false,
            "bSortable": true
        });
    });
</script>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<spring:message code="facilitydata.manage-form"/>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.manage-form"/></b>
<div class="box">
	<a href="schema.form"><spring:message code="facilitydata.add-form"/></a>
	<br/><br/>
	<table cellpadding="2" cellspacing="1" id="formList" class="facilityDataTable" style="white-space:nowrap;">
	    <thead>
		    <tr>
		        <th><spring:message code="facilitydata.display-name"/></th>
		        <th><spring:message code="facilitydata.schema.frequency"/></th>
		        <th><spring:message code="facilitydata.form-schemas"/></th>
		        <th style="width:100%;"><spring:message code="general.description"/></th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach items="${forms}" var="form">
		        <tr>
	                <td>${form.name}</td>
	                <td>${form.frequency}</td>
	                <td>
	                	<c:forEach items="${form.schemas}" var="schema" varStatus="schemaStatus">
	                		<a href="schema.form?id=${schema.id}">
	                			<c:set var="validFromStr" value="${facilitydata:formatDate(schema.validFrom, 'dd/MMM/yyyy', '')}"/>
	                			<c:set var="validToStr" value="${facilitydata:formatDate(schema.validTo, 'dd/MMM/yyyy', '')}"/>
	                			${schema.name} (
	                			<c:choose>
	                				<c:when test="${schema.validFrom == null && schema.validTo == null}">
	                					<spring:message code="facilitydata.schema.alwaysValid"/>
	                				</c:when>
	                				<c:when test="${schema.validFrom == null && schema.validTo != null}">
	                					<spring:message code="facilitydata.schemaValid.upToDate" arguments="${validToStr}"/>
	                				</c:when>
	                				<c:when test="${schema.validFrom != null && schema.validTo == null}">
	                					<spring:message code="facilitydata.schemaValid.dateToPresent" arguments="${validFromStr}"/>
	                				</c:when>
	                				<c:otherwise>
	                					${validFromStr} - ${validToStr}
	                				</c:otherwise>
	                			</c:choose>
	                			)
	                			<c:if test="${!schemaStatus.last}"><br/></c:if>
	                		</a>
	                	</c:forEach>
	                </td>
	                <td>${form.description}</td>
		        </tr>
		    </c:forEach>
	    </tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>