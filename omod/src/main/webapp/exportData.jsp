<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>
<script>
	$j(document).ready(function() {
		$j("#submitButton").click(function(event) {
			var formVal = $j("#formField").val();
			var questionVal = $j("#questionField").val();
			if (formVal && questionVal) {
				alert("Please choose either a form to export *or* a question to export");
			}
			else {
				$j("#queryForm").submit();
			}
		});
	});
</script>

<style>
	table.questionForm td {padding:5px; font-size:small;}
</style>

<openmrs:require privilege="View Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/exportData.form"/>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<spring:message code="facilitydata.export-data"/>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.export-data"/></b>
<div class="box">
	<frm:form id="queryForm" commandName="query" method="post">
		<br/>
		<table class="facilityDataTable">
			<tr>
				<th><spring:message code="facilitydata.choose-form"/>:</th>
	            <td>
	                <frm:select id="formField" path="form" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.all-forms"/></frm:option>
	                    <frm:options items="${forms}" itemValue="id" itemLabel="name"/>
	                </frm:select>
	                <frm:errors cssClass="error" path="form"/>
	            </td>
			</tr>
			<tr><th colspan="2" valign="middle"><spring:message code="facilitydata.or"/></th></tr>
			<tr>
				<th><spring:message code="facilitydata.choose-question"/>:</th>
	            <td>
	                <frm:select id="questionField" path="question" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.all-questions"/></frm:option>
	                    <frm:options items="${questions}" itemValue="id" itemLabel="name"/>
	                </frm:select>
	                <frm:errors cssClass="error" path="question"/>
	            </td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<th><spring:message code="facilitydata.site"/> (<spring:message code="facilitydata.optional"/>):</th>
	            <td>
					<openmrs_tag:locationField formFieldName="facility" initialValue="${query.facility}" />
	            </td>
			</tr>
			<tr>
				<th><spring:message code="facilitydata.from-date"/> (<spring:message code="facilitydata.optional"/>):</th>
	            <td>
	            	<frm:input path="fromDate" id="fromDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="fromDate"/>
	            </td>
			</tr>
			<tr>
				<th><spring:message code="facilitydata.to-date"/> (<spring:message code="facilitydata.optional"/>):</th>
	            <td>
	            	<frm:input path="toDate" id="toDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="toDate"/>
	            </td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<th><spring:message code="facilitydata.entered-from-date"/> (<spring:message code="facilitydata.optional"/>):</th>
	            <td>
	            	<frm:input path="enteredFromDate" id="enteredFromDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="enteredFromDate"/>
	            </td>
			</tr>
			<tr>
				<th><spring:message code="facilitydata.entered-to-date"/> (<spring:message code="facilitydata.optional"/>):</th>
	            <td>
	            	<frm:input path="enteredToDate" id="enteredToDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="enteredToDate"/>
	            </td>
			</tr>
		</table>
		<br/>
		<input id="submitButton" type="button" value="<spring:message code="facilitydata.export-data"/>"/>
	</frm:form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>