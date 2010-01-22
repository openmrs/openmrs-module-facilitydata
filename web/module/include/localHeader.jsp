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

<table>
    <tr>
        <%-- the hasPrivilege check is for the page to view/enter the reports --%>
        <openmrs:hasPrivilege privilege="Manage Facility Data Reports">
            <td>
                <a href="schema.list"><spring:message code="facilitydata.manage-form-schema"/> </a> |
                <a href="question.list"><spring:message code="facilitydata.manage-question"/> </a> |
                <a href="section.list"><spring:message code="facilitydata.manage-sections"/></a> |
                <a href="manage.form"><spring:message code="facilitydata.view-enter"/> </a>
            </td>
        </openmrs:hasPrivilege>
    </tr>
</table>
<br/><br/>