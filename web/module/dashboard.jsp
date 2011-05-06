<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/form.list"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('#formList').dataTable({
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
	table.dashboardTable th,td {width:50%; white-space:nowrap; font-size:small; vertical-align:top;}
</style>

<div class="facilityDataHeader">
	<spring:message code="facilitydata.dashboard"/>
	<hr/>
</div>

<table width="100%" class="dashboardTable">
	<tr>
		<td>
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.enterData"/></b>
			<div class="box">
				<table cellpadding="2" cellspacing="1" id="formList" class="schemaForm">
				    <thead>
					    <tr>
					        <th><spring:message code="facilitydata.display-name"/></th>
					        <th><spring:message code="facilitydata.schema.frequency"/></th>
					        <th><spring:message code="general.description"/></th>
					    </tr>
				    </thead>
				    <tbody>
					    <c:forEach items="${forms}" var="form">
					        <tr>
				                <td><a href="formEntryOverview.form?form=${form.id}">${form.name}</a></td>
				                <td>${form.frequency}</td>
				                <td>${form.description}</td>
					        </tr>
					    </c:forEach>
				    </tbody>
				</table>
			</div>
		</td>
		<td>
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.manageData"/></b>
			<div class="box">
				<a href="dataEntryActivity.form"><spring:message code="facilitydata.view-data-entry-activity"/></a>
			</div>
			<br/>
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.analyzeData"/></b>
			<div class="box">
				<a href="exportValues.form"><spring:message code="facilitydata.export-values-to-excel"/></a><br/>
				<a href="analyzeValues.form"><spring:message code="facilitydata.analyze-values"/></a>
			</div>
			<br/>
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.administerData"/></b>
			<div class="box">
				<a href="form.list"><spring:message code="facilitydata.manage-form"/></a><br/>
				<a href="question.list"><spring:message code="facilitydata.manage-question"/></a><br/>
				<a href="questionType.list"><spring:message code="facilitydata.manage-question-type"/></a>
			</div>	
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>