
<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require anyPrivilege="Manage Facility Data Reports,Enter Facility Data Reports,View Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/form.list"/>

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

<style>
	table {font-size: small;}
	.schemaForm {padding:5px; white-space:nowrap; font-size:small;}
	.dashboardTable {width:50%; padding-right:20px; white-space:nowrap; font-size:small; vertical-align:top;}
</style>

<div class="facilityDataHeader">
	<spring:message code="facilitydata.dashboard"/>
	<hr/>
</div>

<table width="100%">
	<tr>
		<td class="dashboardTable">
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.enterData"/></b>
			<div class="box">
				<table cellpadding="2" cellspacing="1" id="formList" class="schemaForm">
				    <thead>
					    <tr>
					        <th class="schemaForm"><spring:message code="facilitydata.display-name"/></th>
					        <th class="schemaForm"><spring:message code="facilitydata.schema.frequency"/></th>
					        <th class="schemaForm"><spring:message code="general.description"/></th>
					    </tr>
				    </thead>
				    <tbody>
					    <c:forEach items="${forms}" var="form">
					        <tr>
								<c:choose >
									<c:when test="${form.frequency == 'RANDOM'}">
										<c:forEach items="${form.schemas}" var="schema">
											<td class="schemaForm"><a href="randomformEntry.form?schema=${schema.schemaId}">${form.name}</a>
											<td class="schemaForm">${form.frequency}</td>
											<td class="schemaForm">${form.description}</td>
										</c:forEach>


										</td>
									</c:when>
									<c:otherwise>
										<td class="schemaForm"><a href="formEntryOverview.form?form=${form.id}">${form.name}</a></td>
										<td class="schemaForm">${form.frequency}</td>
										<td class="schemaForm">${form.description}</td>
									</c:otherwise>

								</c:choose>


					        </tr>
					    </c:forEach>
				    </tbody>
				</table>
			</div>
		</td>
		<td class="dashboardTable">
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.manageData"/></b>
			<div class="box" style="padding-top:10px; padding-bottom:10px;">
				<a href="completionAnalysis.form"><spring:message code="facilitydata.view-data-completion"/></a>
			</div>
			<br/>
			<b class="boxHeader"><spring:message code="facilitydata.dashboard.analyzeData"/></b>
			<div class="box" style="padding-top:10px; padding-bottom:10px;">
				<a href="exportData.form">
					<spring:message code="facilitydata.export-values-to-excel"/>
				</a>
			</div>
			<openmrs:hasPrivilege privilege="Manage Facility Data Reports">
				<br/>
				<b class="boxHeader"><spring:message code="facilitydata.dashboard.administerData"/></b>
				<div class="box" style="padding-top:10px; padding-bottom:10px;">
					<a href="form.list"><spring:message code="facilitydata.manage-form"/></a><br/>
					<a href="question.list"><spring:message code="facilitydata.manage-question"/></a><br/>
					<a href="questionType.list"><spring:message code="facilitydata.manage-question-type"/></a>
				</div>
			</openmrs:hasPrivilege>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>