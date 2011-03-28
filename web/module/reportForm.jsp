<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Enter Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/report.form"/>



<a href="manage.form?site=${param.site}&schema=${param.id}&month=${facilitydata:getMonth(param.startDate)}&year=${facilitydata:getYear(param.startDate)}"><spring:message code="facilitydata.manage"/></a>
<c:if test="${param.editable == null}">
    | <a
        href="report.form?id=${param.id}&startDate=${param.startDate}&endDate=${param.endDate}&site=${param.site}&editable=true">
    <spring:message code="general.edit"/></a>
</c:if>
<br>
<frm:form commandName="schema" method="post" action="report.form">
    <%-- Validation errors will display above the form itself. --%>
    <frm:errors path="*" cssClass="error"/>
    <frm:hidden path="frequency"/>
    <input type="hidden" name="startDate" value=${param.startDate}/>
    <input type="hidden" name="endDate" value="${param.endDate}"/>
    <input type="hidden" name="site" value="${param.site}"/>
    <frm:hidden path="id"/>

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
                        <facilitydataTag:numericQuestion question="${formQuestion.question}"
                                                formQuestion="${formQuestion}"
                                                value="${values[formQuestion.question.uuid]}"
                                                editable="${param.editable}"/>
                    </c:when>

                    <c:when test="${facilitydata:isBooleanCodedQuestion(formQuestion.question)}">
                        <facilitydataTag:booleanCodedQuestion question="${formQuestion.question}"
                                                     formQuestion="${formQuestion}"
                                                     value="${values[formQuestion.question.uuid]}"
                                                     editable="${param.editable}"/>
                        <td></td>
                    </c:when>

                    <c:when test="${facilitydata:isStockQuestion(formQuestion.question)}">
                        <facilitydataTag:stockQuestion question="${formQuestion.question}"
                                              formQuestion="${formQuestion}"
                                              value="${values[formQuestion.question.uuid]}"
                                              editable="${param.editable}"/>
                    </c:when>
                </c:choose>
            </c:forEach>
        </tr>
    </c:forEach>
    <br/><br/>
    <facilitydataTag:buttons editable="${param.editable}"/>
</frm:form>
</table>
</br></br>
<%@ include file="/WEB-INF/template/footer.jsp" %>
