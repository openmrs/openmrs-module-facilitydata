<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp" %>

<%@ attribute name="question" required="true" type="org.openmrs.module.facilitydata.model.NumericQuestion" %>
<%@ attribute name="formQuestion" required="true" type="org.openmrs.module.facilitydata.model.FacilityDataFormQuestion" %>
<%@ attribute name="value" required="false" type="org.openmrs.module.facilitydata.model.FacilityDataValue" %>
<%@ attribute name="editable" type="java.lang.Boolean" required="false" description="Denotes whether or not the report should be editable." %>

<tr align=left>
    <td width=50% align=right>${formQuestion.name}</td>
    <c:if test="${editable or editable eq null}">
        <td width=50% align=left>
            <input type="text" size="5" name="question.${question.uuid}" value="<c:out value="${value.valueNumeric}" default=""/>"/>
        </td>
    </c:if>
    <c:if test="${editable != null && !editable}">
        <td style="color:#f11;font-weight:bold;" width="50%" align="left">
            <c:choose>
                <c:when test="${value != null && value.valueNumeric != null}">
                    <div style="font-weight:bold;">${value.valueNumeric}</div>
                </c:when>
                <c:otherwise>
                    <div style="font-weight:bold;">
                        <spring:message code="facilitydata.no-answer"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </td>
    </c:if>
</tr>