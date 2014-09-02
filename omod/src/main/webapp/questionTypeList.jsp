<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/questionType.list"/>

<script type="text/javascript">
    $j(document).ready(function() {
        $j('#questionTypeList').dataTable({
            "bPaginate": true,
            "bLengthChange": false,
            "bFilter": false,
            "bSort": false,
            "bInfo": false,
            "bAutoWidth": false,
            "bSortable": true
        });
    });
    
    function deleteQuestionType(id) {
    	if (confirm('<spring:message code="facilitydata.questionType.delete-warning"/>')) {
    		document.location.href='deleteQuestionType.form?questionType='+id;
    	}
    }
</script>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<spring:message code="facilitydata.manage-question-type"/>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.manage-question-type"/></b>
<div class="box">
	
	<form action="questionTypeForm.form">
		<spring:message code="facilitydata.add-question-type"/>:
		<select name="dataType">
			<c:forEach items="${questionDataTypes}" var="qdt">
				<option value="${qdt.key.name}"><spring:message code="${qdt.value}"/></option>
			</c:forEach>
		</select>
		<input type="submit" value="<spring:message code="general.add"/>"/>
	</form>
	
	<table cellpadding="2" cellspacing="1" id="questionTypeList" class="facilityDataTable">
	    <thead>
		    <tr>
		        <th style="white-space:nowrap;"><spring:message code="facilitydata.display-name"/></th>
		        <th style="white-space:nowrap;"><spring:message code="facilitydata.question-type"/></th>
		        <th style="white-space:nowrap;"><spring:message code="facilitydata.configuration"/></th>
		        <th style="width:100%;"><spring:message code="general.description"/></th>
		        <th></th>
		    </tr>
	    </thead>
	    <tbody>
		    <c:forEach items="${questionTypes}" var="questionType">
		        <tr>
	                <td style="white-space:nowrap;"><a href="questionTypeForm.form?id=${questionType.id}">${questionType.name}</a></td>
	                <td style="white-space:nowrap;"><spring:message code="${questionDataTypes[questionType['class']]}"/></td>
	                <td style="white-space:nowrap;">
	                	<c:choose>
	                		<c:when test="${questionType['class'].name == 'org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType'}">
			                	<spring:message code="${questionType.allowDecimals ? 'facilitydata.numeric-value' : 'facilitydata.integer-value'}"/>&nbsp;&nbsp;
			                	<c:if test="${questionType.minValue != null}">
			                		&gt;= ${questionType.minValue}&nbsp;&nbsp;
			                	</c:if>
			                	<c:if test="${questionType.maxValue != null}">
			                		&lt;= ${questionType.maxValue}
			                	</c:if>
			                </c:when>
			                <c:when test="${questionType['class'].name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
			                	<spring:message code="facilitydata.possible-values"/>:
			                	<c:forEach items="${questionType.activeOptions}" var="option" varStatus="optionStatus">
			                		${option.name}<c:if test="${!optionStatus.last}">, </c:if>
			                	</c:forEach>
			                </c:when>
			                <c:otherwise></c:otherwise>
			             </c:choose>
	                </td>
	                <td>${questionType.description}</td>
	               	<td style="white-space:nowrap;">
	                	<c:if test="${questionTypeBreakdown[questionType.id] == null || questionTypeBreakdown[questionType.id] == 0}">
		        			<img class="actionImage" src='<c:url value="/images/trash.gif"/>' border="0" onclick="deleteQuestionType('${questionType.id}');"/>
		        		</c:if>
		        	</td>
		        </tr>
		    </c:forEach>
	    </tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>