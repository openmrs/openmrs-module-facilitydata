<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/formQuestion.list"/>

<script type="text/javascript">

    $(document).ready(function() {
        $('#questionList').dataTable({
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
        code="facilitydata.question-form"/></div>
<br/><br/>
<table cellpadding="2" cellspacing="1" id="questionList">
    <thead>
    <tr>
        <th><spring:message code="general.action"/></th>
        <th><spring:message code="general.name"/></th>
        <th><spring:message code="facilitydata.type"/></th>
        <th><spring:message code="general.description"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${questions}" var="question" varStatus="varstatus">
        <tr>
            <form method="post" action="question.list">
                <input type="hidden" value="${question.id}" name="id"/>
                <td style="vertical-align:top;width:1em;text-align:center;"><input type="image"
                                                                                   onclick="return confirm('<spring:message code="
                                                                                   facilitydata.question.delete-warning"/>');"
                    alt="<spring:message code="general.delete"/>" src="${pageContext.request.contextPath}/images/trash.gif"/>
                </td>
                <td style="vertical-align:top;overflow:hidden;width:1em;"><a
                        href="question.form?id=${question.id}">${question.name}</a></td>
                <td style="vertical-align:top;overflow:hidden;width:1em;">${facilitydata:getDataType(question)}</td>
                <td style="vertical-align:top;">${question.description}</td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="/WEB-INF/template/footer.jsp" %>
