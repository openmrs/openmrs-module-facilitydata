<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/schema.list"/>



<script type="text/javascript">

    $(document).ready(function() {
        $('#schemaList').dataTable({
            "bPaginate": true,
            "bLengthChange": true,
            "bFilter": false,
            "bSort": false,
            "bInfo": true,
            "bAutoWidth": true,
            "bSortable": true
        });
    });
</script>

<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message
        code="facilitydata.manage-form-schema"/></div>
<br/>
<table cellpadding="2" cellspacing="1" id="schemaList">
    <thead>
    <tr>
        <th><spring:message code="general.action"/></th>
        <th><spring:message code="facilitydata.display-name"/></th>
        <th><spring:message code="facilitydata.schema.frequency"/></th>
        <th><spring:message code="facilitydata.valid-from"/></th>
        <th><spring:message code="facilitydata.valid-to"/></th>
        <th><spring:message code="general.description"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${schemas}" var="schema" varStatus="varstatus">
        <tr>
            <form method="post" action="schema.list">
                <input type="hidden" value="${schema.id}" name="id"/>
                <td style="vertical-align:top;width:1em;text-align:center;"><input type="image"
                                                                                   onclick="return confirm('<spring:message code="facilitydata.schema.delete-warning"/>');"
                    alt="<spring:message code="general.delete"/>" src="${pageContext.request.contextPath}/images/trash.gif"/>
                </td>
                <td style="vertical-align:top;overflow:hidden;width:1em;"><a
                        href="schema.form?id=${schema.id}">${schema.name}</a></td>
                <td style="vertical-align:top;overflow:hidden;width:1em;text-align:center;">${schema.frequency}</td>
                <td style="vertical-align:top;overflow:hidden;width:1em;">${schema.validFrom}</td>
                <td style="vertical-align:top;overflow:hidden;width:1em;">${schema.validTo}</td>
                <td style="vertical-align:top;">${schema.description}</td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="/WEB-INF/template/footer.jsp" %>