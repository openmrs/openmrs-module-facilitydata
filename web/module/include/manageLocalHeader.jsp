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
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-1.3.2.min.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/dataTables/jquery.dataTables.js"></script>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/datatables/custom.css"/>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/datatables/page.css"/>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/datatables/table.css"/>


<table>
    <tr>
        <td>
            <a href="schema.form"><spring:message code="facilitydata.add-schema"/> </a> |
            <a href="question.form"><spring:message code="facilitydata.add-question"/></a> |
            <a href="section.form"><spring:message code="facilitydata.add-section"/></a> |
            <a href="manage.form"><spring:message code="facilitydata.view-enter"/> </a>
        </td>
    </tr>
</table>
<br/><br/>