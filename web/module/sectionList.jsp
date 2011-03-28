<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/section.list"/>
<script type="text/javascript">

    $(document).ready(function() {
        $('#sectionList').dataTable({
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
        code="facilitydata.manage-sections"/></div>
<table cellpadding="2" cellspacing="1" id="sectionList">
    <thead>
    <tr>
        <th><spring:message code="general.action"/></th>
        <th><spring:message code="facilitydata.display-name"/></th>
        <th><spring:message code="general.description"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sections}" var="section" varStatus="varstatus">
        <tr>
            <form method="post" action="section.list">
                <input type="hidden" value="${section.id}" name="id"/>
                <td style="vertical-align:top;width:1em;text-align:center;"><input type="image"
                                                                                   onclick="return confirm('<spring:message code="
                                                                                   facilitydata.section.delete-warning"/>');"
                    alt="<spring:message code="general.delete"/>" src="${pageContext.request.contextPath}/images/trash.gif"/>
                </td>
                <td style="vertical-align:top;overflow:hidden;width:1em;"><a
                        href="section.form?id=${section.id}">${section.name}</a></td>
                <td style="vertical-align:top;">${section.description}</td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>