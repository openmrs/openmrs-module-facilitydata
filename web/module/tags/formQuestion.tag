<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp" %>

<%@ attribute name="question" required="true" type="org.openmrs.module.facilitydata.model.FacilityDataFormQuestion" %>
<%@ attribute name="value" required="false" type="org.openmrs.module.facilitydata.model.FacilityDataValue" %>
<%@ attribute name="editable" type="java.lang.Boolean" required="false" description="Denotes whether or not the report should be editable." %>

<tr align="left">
    <td style="white-space:nowrap;">${formQuestion.questionNumber}.</td>
    <td style="width="100%">${formQuestion.name}</td>
    <td style="white-space:nowrap;">
    	<c:choose>
    		<c:when test="${editable || editable == null}">
    		    <c:choose>
    				<c:when test="${question.question.questionType.class == org.openmrs.module.facilitydata.model.FacilityDataCodedQuestionType}">
		    			<select name="question.${question.uuid}">
			                <option value="" ${value == null ? "selected" : ""}></option>
			                <c:forEach items="${question.question.questionType.optionSet.options}" var="option">
			                	<c:set var="optionKey" value="${empty option.key ? option.name : option.key}"/>
			                    <option value="${empty optionKey}" ${(value != null && value.valueText == optionKey) ? "selected" : ""}>${option.name}</option>
			                </c:forEach>
			            </select>
			         </c:when>
			         <c:when test="${question.question.questionType.class == org.openmrs.module.facilitydata.model.FacilityDataNumericQuestionType}">
			         	<input type="text" size="5" name="question.${question.uuid}" value="<c:out value="${value.valueNumeric}" default=""/>"/>
			         </c:when>
			         <c:otherwise>
			         	<spring:message code="facilitydata.invalid-question-type"/>
			         </c:otherwise>
			     </c:choose>
    		</c:when>
    		<c:otherwise>
    			<span style="color:#f11;font-weight:bold;" class="readOnly">
    				<c:choose>
    					<c:when test="${question.question.questionType.class == org.openmrs.module.facilitydata.model.FacilityDataCodedQuestionType}">
    						<c:choose>
    							<c:when test="${value != null && value.valueNumeric != null}">${value.valueNumeric}</c:when>
    							<c:otherwise><spring:message code="facilitydata.no-answer"/></c:otherwise>
    						</c:choose>
    					</c:when>
    					<c:when test="${question.question.questionType.class == org.openmrs.module.facilitydata.model.FacilityDataNumericQuestionType}">
    					   	<c:choose>
    							<c:when test="${value != null && value.valueCoded != null}">${value.valueCoded}</c:when>
    							<c:otherwise><spring:message code="facilitydata.no-answer"/></c:otherwise>
    						</c:choose>
    					</c:when>
    					<c:otherwise>
    						<spring:message code="facilitydata.invalid-question-type"/>
    					</c:otherwise>		
    				</c:choose>
    			</span>
    		</c:otherwise>
    	</c:choose>
    </td>
</tr>