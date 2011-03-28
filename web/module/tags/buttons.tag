<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp" %>
<%@ attribute name="editable" type="java.lang.Boolean" rtexprvalue="true" required="false" %>

<c:if test="${editable != null && editable}">
    <table width="100%">
        <tr width="50%">
            <th align="center" colspan="2">
                <input type="submit" value="<spring:message code="general.submit"/>"/>
                <input type="button" value="<spring:message code="general.cancel"/>"
                onclick="document.location.href='report.form?id=${param.id}&startDate=${param.startDate}&endDate=${param.endDate}&site=${param.site}'"/>
            </th>
        </tr>
    </table>
</c:if>