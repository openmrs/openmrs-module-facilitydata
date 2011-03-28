<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp" %>

<%@ attribute name="question" required="true" type="org.openmrs.module.facilitydata.model.BooleanCodedQuestion" %>
<%@ attribute name="formQuestion" required="true" type="org.openmrs.module.facilitydata.model.FacilityDataFormQuestion" %>
<%@ attribute name="value" required="false" type="org.openmrs.module.facilitydata.model.FacilityDataValue" %>
<%@ attribute name="editable" type="java.lang.Boolean" required="false" description="Denotes whether or not the report should be editable." %>

<tr align=left>
    <td width=50% align=right>${formQuestion.name}</td>
    <c:if test="${editable or editable eq null}">
        <td width=50% align=left>
            <c:forEach items="${question.codedOptions}" var="option">
                <input type="radio" name="question.${question.uuid}" value="${option}" ${(value != null && value.valueText == option) ? "checked=checked" : ""}/>
                ${option}
            </c:forEach>
        </td>
    </c:if>
    <c:if test="${editable != null && !editable}">
        <td style="color:#f11;font-weight:bold;" width="50%" align="left">
            <c:choose>
                <c:when test="${value != null && fn:length(value.valueText) > 0}">
                    ${value.valueText}
                </c:when>
                <c:otherwise>
                    <spring:message code="facilitydata.no-answer"/>
                </c:otherwise>
            </c:choose>
        </td>
    </c:if>
</tr>