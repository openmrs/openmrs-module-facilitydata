<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/question.form"/>

<spring:message code="facilitydata.question.info"/>
<br/><br/>
<a href="question.form"><spring:message code="facilitydata.add-question"/></a>
<br/><br/>
<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message code="facilitydata.question-form"/></div>
<frm:form commandName="command" method="post" cssClass="box">    
    <table>
        <tr>
            <td><spring:message code="general.name"/></td>
            <td>
                <frm:input path="name" size="50"/>
                <spring:message code="facilitydata.required"/>
                <frm:errors path="name" cssClass="error"/>
            </td>
        </tr>

        <tr>
            <td><spring:message code="general.description"/></td>
            <td><frm:textarea path="description" cols="70" rows="2"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.type"/></td>
            <td>
                <frm:select path="type" onchange="checkType();">
                    <frm:option value="" label=""/>
                    <frm:options items="${command.types}"/>
                </frm:select>
                <spring:message code="facilitydata.required"/>
                <frm:errors path="type" cssClass="error"/>
            </td>

        </tr>
        <tr>
            <td><spring:message code="facilitydata.method"/></td>
            <td>
                <frm:select path="method">
                    <frm:option value="" label=""/>
                    <frm:options items="${command.methods}"/>
                </frm:select>
                <spring:message code="facilitydata.required"/>
                <frm:errors path="method" cssClass="error"/>
            </td>
        </tr>
        <c:if test="${param.id != null}">
            <tr>
                <td><spring:message code="general.retired"/></td>
                <td><frm:checkbox path="retired" id="retired" onchange="checkRetired();"/></td>
            </tr>
            <tr style="display:none;" id="reason">
                <td><spring:message code="general.retiredReason"/></td>
                <td>
                    <frm:input path="retireReason"/>
                    <frm:errors path="retireReason" cssClass="error"/>
                </td>
            </tr>
        </c:if>
    </table>
    <table id="numericQuestion" style="display:none;">
        <tr>
            <td><spring:message code="facilitydata.minValue"/></td>
            <td><frm:input path="minValue" id="min"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.maxValue"/></td>
            <td><frm:input path="maxValue" id="max"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.allowDecimals"/></td>
            <td><frm:checkbox path="allowDecimals"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td><input type="submit" value="<spring:message code="facilitydata.save-question"/>"/></td>
            <td><input type="button" onclick="document.location.href='question.list'" value="<spring:message code="general.cancel" />"/></td>
        </tr>
    </table>
</frm:form>

<%@ include file="/WEB-INF/template/footer.jsp" %>