<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Enter Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/formEntryOverview.form"/>

<style>
	.reportSummaryTable th,td { width:10px; font-size:small; white-space:nowrap; padding:5px; }
	.missing { background-color:red; color:white; }
	.partial { background-color:yellow; color:black; }
	.complete { background-color:green; color:white; }
</style>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<spring:message code="facilitydata.enter-data"/>
	-&gt;
	${schema.name}
	<hr/>
</div>

<table><tr><td>
	<table border="1" class="reportSummaryTable">
		<tr>
			<th></th>
			<c:forEach items="${yearCols}" var="yearEntry" varStatus="yearStatus">
				<th colspan="${yearEntry.value}">
					<c:if test="${yearStatus.first}">
						<a href="formEntryOverview.form?schema=${schema.id}&yearIncrement=${yearIncrement == null ? -1 : yearIncrement-1}&monthIncrement=${monthIncrement}">
							&lt;&lt;
						</a>
					</c:if>
					&nbsp;${yearEntry.key}&nbsp;
					<c:if test="${yearStatus.last}">
						<a href="formEntryOverview.form?schema=${schema.id}&yearIncrement=${yearIncrement == null ? 1 : yearIncrement+1}&monthIncrement=${monthIncrement}">
							&gt;&gt;
						</a>
					</c:if>
				</th>
			</c:forEach>
		</tr>
		<tr>
			<th></th>
			<c:forEach items="${monthCols}" var="monthEntry" varStatus="monthStatus">
				<th colspan="${monthEntry.value}">
					<c:if test="${monthStatus.first && schema.frequency == 'DAILY'}">
						<a href="formEntryOverview.form?schema=${schema.id}&monthIncrement=${monthIncrement == null ? -1 : monthIncrement-1}&yearIncrement=${yearIncrement}">
							&lt;&lt;
						</a>
					</c:if>
					&nbsp;${displayKeys[monthEntry.key]}&nbsp;
					<c:if test="${monthStatus.last && schema.frequency == 'DAILY'}">
						<a href="formEntryOverview.form?schema=${schema.id}&monthIncrement=${monthIncrement == null ? 1 : monthIncrement+1}&yearIncrement=${yearIncrement}">
							&gt;&gt;
						</a>
					</c:if>
				</th>
			</c:forEach>
		</tr>
		<c:if test="${schema.frequency == 'DAILY'}">
			<tr>
				<th></th>
				<c:forEach items="${dayCols}" var="day">
					<th style="text-align:center;">${displayKeys[day.key]}</th>
				</c:forEach>
			</tr>
		</c:if>
		<c:forEach items="${locations}" var="location">
			<c:set var="locationEntry" value="${dayData[location.locationId]}"/>
			<tr>
				<th>${location.name}</th>
				<c:forEach items="${dayCols}" var="dayEntry">
					<c:set var="numEntries" value="${locationEntry[dayEntry.key]}"/>
					<c:set var="cellStyle" value="${numEntries == 0 || numEntries == null ? 'missing' : numEntries == numQuestions ? 'complete' : 'partial'}"/>
					<td class="${cellStyle}" style="text-align:center;">
						<a href="#" style="color:white;">
							<spring:message code="facilitydata.enter.short"/>
						</a>
					</td>
				</c:forEach>
			</tr>	
		</c:forEach>
	</table>
</td>
<td style="vertical-align:top;">
	<table>
		<tr><td class="complete">&nbsp;&nbsp;</td><td><spring:message code="facilitydata.complete"/></td></tr>
		<tr><td class="partial">&nbsp;&nbsp;</td><td><spring:message code="facilitydata.partially-entered"/></td></tr>
		<tr><td class="missing">&nbsp;&nbsp;</td><td><spring:message code="facilitydata.not-entered"/></td></tr>
	</table>
</td>
</tr></table>

<%@ include file="/WEB-INF/template/footer.jsp" %>