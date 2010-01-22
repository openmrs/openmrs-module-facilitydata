<%--
  The contents of this file are subject to the OpenMRS Public License
  Version 1.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://license.openmrs.org

  Software distributed under the License is distributed on an "AS IS"
  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  License for the specific language governing rights and limitations
  under the License.

  Copyright (C) OpenMRS, LLC.  All Rights Reserved.

--%>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ attribute name="question" required="true" type="org.openmrs.module.facilitydata.model.NumericQuestion" %>
<%@ attribute name="formQuestion" required="true"
              type="org.openmrs.module.facilitydata.model.FacilityDataFormQuestion" %>
<%@ attribute name="value" required="false" type="org.openmrs.module.facilitydata.model.FacilityDataValue" %>
<%@ attribute name="editable" type="java.lang.Boolean" required="false"
              description="Denotes whether or not the report should be editable." %>
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