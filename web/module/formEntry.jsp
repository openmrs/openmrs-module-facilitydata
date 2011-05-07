<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Enter Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/formEntry.form"/>

<style>
	.odd { background-color:#DCDCDC; }
	.even { background-color:white; }
	.readOnly { color:blue; font-weight:bold; }
	.noAnswer { color:red; font-weight:bold; }
</style>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<a href="formEntryOverview.form?form=${schema.form.id}">${schema.form.name}</a>
	-&gt;
	${facility.name} - 
	<c:choose>
		<c:when test="${schema.form.frequency == 'DAILY'}">${facilitydata:formatDate(fromDate, 'dd/MMM/yyyy', '')}</c:when>
		<c:otherwise>${facilitydata:formatDate(fromDate, 'MMM yyyy', '')}</c:otherwise>
	</c:choose>
	<hr/>
</div>

<form method="post">
	<input type="hidden" name="toDate" value="<openmrs:formatDate date="${toDate}" type="textbox"/>"/>
	<c:forEach items="${schema.sections}" var="section">
		<c:if test="${!empty section.questions}">
			<fieldset>
				<legend>${section.name}</legend>
				<table width="100%" class="facilityDataTable">
					<tr>
						<th colspan="2"></th>
						<th><spring:message code="facilitydata.value"/></th>
						<th><spring:message code="facilitydata.optional-comments"/></th>
					<tr>
					<c:set var="oddEven" value="odd"/>
					<c:forEach items="${section.questions}" var="q">
						<c:set var="oddEven" value="${oddEven == 'odd' ? 'even' : 'odd'}"/>
						<tr align="left">
						    <td class="${oddEven}" style="white-space:nowrap; vertical-align:middle;">${q.questionNumber}</td>
						    <td class="${oddEven}" style="width:400px; vertical-align:middle;">${q.name}</td>
						    <td class="${oddEven}" style="width:200px; white-space:nowrap;">
				    		    <c:choose>
				    				<c:when test="${q.question.questionType.class.name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
						    			<c:choose>
							    			<c:when test="${!viewOnly}">
								    			<select name="valueCoded.${q.id}">
									                <option value=""></option>
									                <c:forEach items="${q.question.questionType.options}" var="option">
									                    <option value="${option.id}" ${option == report.values[q].valueCoded ? "selected" : ""}>${option.name}</option>
									                </c:forEach>
									            </select>
								        	</c:when>
								        	<c:otherwise>
							        			<c:choose>
				    								<c:when test="${report.values[q].valueCoded != null}">
				    									<span class="readOnly">${report.values[q].valueCoded.name}</span>
				    								</c:when>
				    								<c:otherwise>
				    									<span class="noAnswer"><spring:message code="facilitydata.no-answer"/></span>
				    								</c:otherwise>
					    						</c:choose>
								        	</c:otherwise>
								     	</c:choose>
								     </c:when>
							         <c:when test="${q.question.questionType.class.name == 'org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType'}">
							         	<c:choose>
							    			<c:when test="${!viewOnly}">
							         			<input type="text" size="10" name="valueNumeric.${q.id}" value="<c:out value="${report.values[q].valueNumeric}" default=""/>"/>
							        		</c:when>
							        		<c:otherwise>
							        			<c:choose>
							        				<c:when test="${report.values[q].valueNumeric != null}">
							        					<span class="readOnly">${report.values[q].valueNumeric}</span>
							        				</c:when>
						    						<c:otherwise>
						    							<span class="noAnswer"><spring:message code="facilitydata.no-answer"/></span>
						    						</c:otherwise>
						    						</c:choose>
							        			</span>
							        		</c:otherwise>
							        	</c:choose>
							         </c:when>
							         <c:otherwise>
							         	<spring:message code="facilitydata.invalid-question-type"/>: ${q.question.questionType.class.name}
							         </c:otherwise>
							     </c:choose>
						    </td>
						    <td class="${oddEven}" style="white-space:nowrap;">
						    	<c:choose>
					    			<c:when test="${!viewOnly}">
					         			<input type="text" size="30" name="comments.${q.id}" value="<c:out value="${report.values[q].comments}" default=""/>"/>
					        		</c:when>
					        		<c:otherwise>
					        			<span class="readOnly">${report.values[q].comments}</span>
					        		</c:otherwise>
					        	</c:choose>
						    </td>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
		</c:if>
		<br/>
	</c:forEach>
	<c:if test="${!viewOnly}">
		<input type="submit" value="<spring:message code="general.save"/>" />
		<a href="formEntryOverview.form?form=${schema.form.id}"><input type="button" value="<spring:message code="general.cancel"/>"></a>
	</c:if>
	<c:if test="${viewOnly}">
		<a href="formEntry.form?schema=${schema.id}&facility=${facility.id}&fromDate=<openmrs:formatDate date="${fromDate}" type="textbox"/>">
			<input type="button" value="<spring:message code="general.edit"/>">
		</a>
	</c:if>
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>