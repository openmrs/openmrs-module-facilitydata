<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Enter Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/manage.form"/>

<br/>

<p>

<div class="boxHeader" style="border: solid 1px #000;text-align:center;">
    <h1><spring:message code="facilitydata.report-overview"/></h1>
</div>
</p>
<br/><br/>
<div style="margin: 0 20px 5px 0;float:left;"/>
<div id="leftBox" style="margin: 0 20px 5px 0; float:left;"/>
<div id="optionBox" style="border: 1px #000000 solid;padding: 5; background:#f0f0f0;">
    <form method="get">
        <spring:message code="facilitydata.report"/> <select id="schema" name="schema">
        <c:forEach items="${schemas}" var="schema">
            <option value="${schema.id}" ${param.schema==schema.id ? "selected" : ""}>
                ${schema.name}
            </option>
        </c:forEach>
    </select><br/>
        <spring:message code="facilitydata.site"/> <openmrs_tag:locationField formFieldName="site"
                                                                              initialValue="${param.site != null ? param.site : 1}"/>
        <br/>
        <spring:message code="facilitydata.year"/> <select name="year">
        <c:forEach begin="1900" end="2099" var="y" step="1">
            <c:choose>
                <c:when test="${param.year != null}">
                    <option value="${y}" ${(param.year ==  y) ? "selected" : ""}>${y}</option>
                </c:when>
                <c:otherwise>
                    <option value="${y}" ${facilitydata:getYear(today) == y ? "selected" : ""}>${y}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
        <spring:message code="facilitydata.month"/> <select id="month" name="month" ${param.schema != null && facilitydata:isMonthly(param.schema) ? "disabled" : ""}>
        <c:forEach items="${months}" var="month">
            <c:choose>
                <c:when test="${param.month != null}">
                    <option value="${month}" ${(param.month ==  month) ? "selected" : ""}>${month}</option>
                </c:when>
                <c:otherwise>
                    <option value="${month}" ${facilitydata:getMonth(today) == month ? "selected" : ""}>${month}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
        <br/>
        <input type="submit" value="<spring:message code="facilitydata.view-calendar"/>"/>
    </form>
</div>
</div><%-- so bad stuff doesn't happen; layout oddities--%>
<br/><br/><br/><br/><br/><br/>
<table cellspacing="4" cellpadding="2" border="0">
    <tr>
        <td style="width: 18px; background:#ff0000;  border: 1px #000000 solid;">&nbsp;&nbsp;&nbsp;</td>
        <td>
            = <spring:message code="facilitydata.not-entered"/>
        </td>
    </tr>
    <tr>
        <td style="width: 18px; background: #ffff66; border: 1px #000000 solid;">&nbsp;&nbsp;&nbsp;</td>
        <td>= <spring:message code="facilitydata.partially-entered"/>
        </td>
    </tr>
    <tr>
        <td style="width: 18px; background:#008000; border: 1px #000000 solid;">&nbsp;&nbsp;&nbsp;</td>
        <td>= <spring:message code="facilitydata.complete"/>
        </td>
    </tr>
    <tr>
        <td style="width: 18px; background:#aaa; border: 1px #000000 solid;">&nbsp;&nbsp;&nbsp;</td>
        <td>= <spring:message code="facilitydata.future"/>
        </td>
    </tr>
</table>
</div> <%-- so bad stuff doesn't happen; layout oddities --%>

<c:if test="${(param.schema != null && param.site != null && param.month != null && param.year != null) or (param.site != null && param.year != null && param.schema != null && facilitydata:isMonthly(param.schema))}">
    <facilitydata:calendar schemaId="${param.schema}" locationId="${param.site}" month="${param.month}"
                           year="${param.year}"/>
</c:if>
<%@ include file="/WEB-INF/template/footer.jsp" %>