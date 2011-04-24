<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/schema.list"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('#schemaList').dataTable({
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

<style>
	table.schemaForm th,td {padding:5px; white-space:nowrap; font-size:small;}
</style>

<b class="boxHeader"><spring:message code="facilitydata.manage-form-schema"/></b>
<br/>
<a href="schema.form"><spring:message code="facilitydata.add-schema"/></a>
<br/><br/>
<table cellpadding="2" cellspacing="1" id="schemaList" class="schemaForm">
    <thead>
	    <tr>
	        <th><spring:message code="facilitydata.display-name"/></th>
	        <th><spring:message code="facilitydata.schema.frequency"/></th>
	        <th><spring:message code="facilitydata.valid-from"/></th>
	        <th><spring:message code="facilitydata.valid-to"/></th>
	        <th style="width:100%;"><spring:message code="general.description"/></th>
	    </tr>
    </thead>
    <tbody>
	    <c:forEach items="${schemas}" var="schema">
	        <tr>
                <td><a href="schema.form?id=${schema.id}">${schema.name}</a></td>
                <td>${schema.frequency}</td>
                <td><openmrs:formatDate date="${schema.validFrom}"/></td>
                <td><openmrs:formatDate date="${schema.validTo}"/></td>
                <td>${schema.description}</td>
	        </tr>
	    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>