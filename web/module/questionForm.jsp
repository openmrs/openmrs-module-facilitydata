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
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp" %>
<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm"
                 redirect="/module/facilitydata/question.form"/>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-1.3.2.min.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/util.js"></script>

<spring:message code="facilitydata.question.info"/>
<br/><br/>
<a href="question.form"><spring:message code="facilitydata.add-question"/></a>
<br/><br/>
<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message code="facilitydata.question-form"/></div>
<form:form commandName="command" method="post" cssClass="box">    
    <table>
        <tr>
            <td><spring:message code="general.name"/></td>
            <td>
                <form:input path="name" size="50"/>
                <spring:message code="facilitydata.required"/>
                <form:errors path="name" cssClass="error"/>
            </td>
        </tr>

        <tr>
            <td><spring:message code="general.description"/></td>
            <td><form:textarea path="description" cols="70" rows="2"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.type"/></td>
            <td>
                <form:select path="type" onchange="checkType();">
                    <form:option value="" label=""/>
                    <form:options items="${command.types}"/>
                </form:select>
                <spring:message code="facilitydata.required"/>
                <form:errors path="type" cssClass="error"/>
            </td>

        </tr>
        <tr>
            <td><spring:message code="facilitydata.method"/></td>
            <td>
                <form:select path="method">
                    <form:option value="" label=""/>
                    <form:options items="${command.methods}"/>
                </form:select>
                <spring:message code="facilitydata.required"/>
                <form:errors path="method" cssClass="error"/>
            </td>
        </tr>
        <c:if test="${param.id != null}">
            <tr>
                <td><spring:message code="general.retired"/></td>
                <td><form:checkbox path="retired" id="retired" onchange="checkRetired();"/></td>
            </tr>
            <tr style="display:none;" id="reason">
                <td><spring:message code="general.retiredReason"/></td>
                <td>
                    <form:input path="retireReason"/>
                    <form:errors path="retireReason" cssClass="error"/>
                </td>
            </tr>
        </c:if>
    </table>
    <table id="numericQuestion" style="display:none;">
        <tr>
            <td><spring:message code="facilitydata.minValue"/></td>
            <td><form:input path="minValue" id="min"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.maxValue"/></td>
            <td><form:input path="maxValue" id="max"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.allowDecimals"/></td>
            <td><form:checkbox path="allowDecimals"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td><input type="submit" value="<spring:message code="facilitydata.save-question"/>"/></td>
            <td><input type="button" onclick="document.location.href='question.list'" value="<spring:message code="general.cancel" />"/></td>
        </tr>
    </table>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp" %>