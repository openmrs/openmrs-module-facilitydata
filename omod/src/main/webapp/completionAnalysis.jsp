<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<script>
	$j = jQuery;
</script>

<style>
	table.questionForm td {padding:5px; font-size:small;}
</style>

<openmrs:require privilege="View Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/exportData.form"/>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<spring:message code="facilitydata.view-data-completion"/>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.view-data-completion"/></b>
<div class="box">
	<frm:form method="get" commandName="query">
		<br/>
		<table class="facilityDataTable">
			<tr>
				<th><spring:message code="facilitydata.schema"/>:</th>
	            <td>
	                <frm:select path="schema" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.choose"/>...</frm:option>
	                    <frm:options items="${schemas}" itemValue="id" itemLabel="name"/>
	                </frm:select>
	                <frm:errors cssClass="error" path="schema"/>
	            </td>
				<th><spring:message code="facilitydata.from-date"/>:</th>
	            <td>
	            	<frm:input path="fromDate" id="fromDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="fromDate"/>
	            </td>
	            <td style="padding-left:20px;"><input type="submit" value="<spring:message code="general.search"/>"/></td>
	        </tr>
	        <tr>
	        	<th><spring:message code="facilitydata.site"/>:</th>
	            <td>
					<openmrs_tag:locationField formFieldName="facility" initialValue="${query.facility}" />
	            </td>
				<th><spring:message code="facilitydata.to-date"/>:</th>
	            <td>
	            	<frm:input path="toDate" id="toDate" onclick="showCalendar(this);"/>
	            	<frm:errors cssClass="error" path="toDate"/>
	            </td>
	            <td></td>
			</tr>
		</table>
	</frm:form>
	<hr/>
	<c:choose>
		<c:when test="${query.schema == null}"><spring:message code="facilitydata.please-choose-a-schema"/></c:when>
		<c:otherwise>
			<table width="100%" class="facilityDataTable">
				<tr>
					<th style="padding-left:20px; white-space:nowrap;">#</th>
					<th style="white-space:nowrap;"><spring:message code="facilitydata.question"/></th>
					<th style="white-space:nowrap;"><spring:message code="facilitydata.percent-complete"/></th>
					<th style="white-space:nowrap;"><spring:message code="facilitydata.aggregate-total"/></th>
				</tr>
				<c:forEach items="${query.schema.sections}" var="section">
					<tr><th colspan="4" style="background-color:lightgrey;">${section.name}</th></tr>
					<c:set var="oddEven" value="odd"/>
					<c:forEach items="${section.questions}" var="question">
						<c:set var="oddEven" value="${oddEven == 'odd' ? 'even' : 'odd'}"/>
						<tr>
							<td class="${oddEven}" style="padding-left:20px; white-space:nowrap;">${question.questionNumber}</td>
							<td class="${oddEven}" style="white-space:nowrap;">${question.name}</td>
							<td class="${oddEven}" style="white-space:nowrap;">${numValuesByQuestion[question] == null ? 0 : numValuesByQuestion[question]} / ${numExpected}
							<td class="${oddEven}" style="width:100%">
								<c:choose>
									<c:when test="${question.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
										<c:forEach items="${question.question.questionType.options}" var="option" varStatus="optionStatus">
											${option.name}: ${codedTotals[question][option] == null ? 0 : codedTotals[question][option]}
											<c:if test="${!optionStatus.last}">, </c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										${numericTotals[question] == null ? 0 : numericTotals[question]}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>