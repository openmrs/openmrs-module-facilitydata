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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ taglib prefix="facilitydata" uri="/WEB-INF/view/module/facilitydata/facilitydata.tld" %>
<%@ taglib prefix="render" tagdir="/WEB-INF/tags/module/facilitydata" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<openmrs:require privilege="Enter Facility Data Reports" otherwise="/login.htm"
                 redirect="/module/facilitydata/report.form"/>
<a href="manage.form?site=${param.site}&schema=${param.id}&month=${facilitydata:getMonth(param.startDate)}&year=${facilitydata:getYear(param.startDate)}"><spring:message code="facilitydata.manage"/></a>
<c:if test="${param.editable == null}">
    | <a
        href="report.form?id=${param.id}&startDate=${param.startDate}&endDate=${param.endDate}&site=${param.site}&editable=true">
    <spring:message code="general.edit"/></a>
</c:if>
<br>
<form:form commandName="schema" method="post" action="report.form">
    <%-- Validation errors will display above the form itself. --%>
    <form:errors path="*" cssClass="error"/>
    <form:hidden path="frequency"/>
    <input type="hidden" name="startDate" value=${param.startDate}/>
    <input type="hidden" name="endDate" value="${param.endDate}"/>
    <input type="hidden" name="site" value="${param.site}"/>
    <form:hidden path="id"/>

    <table width="100%">
        <tr align="center" bgcolor="#8FABC7" style="color:#fff;">
            <th colspan="2"><h1>${schema.name}</h1></th>
        </tr>
    </table>
    <div id="foo"></div>
    <table width="100%">
    <%-- loop over the sections --%>
    <c:forEach items="${schema.formSections}" var="section">
        <tr align="center" bgcolor="#8FABC7" style="color:#fff;">
            <th width="50%" colspan="2">${section.name}</th>
                <%-- now loop over the form questions; checking each data type --%>
            <c:forEach items="${section.questions}" var="formQuestion">
                <c:choose>
                    <c:when test="${facilitydata:isNumericQuestion(formQuestion.question)}">
                        <render:numericQuestion question="${formQuestion.question}"
                                                formQuestion="${formQuestion}"
                                                value="${values[formQuestion.question.uuid]}"
                                                editable="${param.editable}"/>
                    </c:when>

                    <c:when test="${facilitydata:isBooleanCodedQuestion(formQuestion.question)}">
                        <render:booleanCodedQuestion question="${formQuestion.question}"
                                                     formQuestion="${formQuestion}"
                                                     value="${values[formQuestion.question.uuid]}"
                                                     editable="${param.editable}"/>
                        <td></td>
                    </c:when>

                    <c:when test="${facilitydata:isStockQuestion(formQuestion.question)}">
                        <render:stockQuestion question="${formQuestion.question}"
                                              formQuestion="${formQuestion}"
                                              value="${values[formQuestion.question.uuid]}"
                                              editable="${param.editable}"/>
                    </c:when>
                </c:choose>
            </c:forEach>
        </tr>
    </c:forEach>
    <br/><br/>
    <render:buttons editable="${param.editable}"/>
</form:form>
</table>
</br></br>
<%@ include file="/WEB-INF/template/footer.jsp" %>