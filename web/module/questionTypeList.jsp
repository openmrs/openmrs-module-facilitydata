<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/questionType.list"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('#questionTypeList').dataTable({
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

<b class="boxHeader"><spring:message code="facilitydata.manage-question-type"/></b>
<div class="box">
	<form action="questionTypeForm.form">
		<spring:message code="facilitydata.add-question-type"/>:
		<select name="dataType">
			<option value=""></option>
			<c:forEach items="${questionDataTypes}" var="qdt">
				<option value="${qdt.key.name}"><spring:message code="${qdt.value}"/></option>
			</c:forEach>
		</select>
		<input type="submit" value="<spring:message code="general.add"/>"/>
	</form>
	<a href="questionTypeForm.form">
	 </a>
	<br/><br/>
	<table cellpadding="2" cellspacing="1" id="questionTypeList" class="schemaForm">
	    <thead>
		    <tr>
		        <th style="white-space:nowrap;"><spring:message code="facilitydata.display-name"/></th>
		        <th style="width:100%;"><spring:message code="general.description"/></th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach items="${questions}" var="question">
		        <tr>
	                <td style="white-space:nowrap;"><a href="questionType.form?id=${questionType.id}">${questionType.name}</a></td>
	                <td>${questionType.description}</td>
		        </tr>
		    </c:forEach>
	    </tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>