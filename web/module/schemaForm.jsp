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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp" %>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-1.3.2.min.js"></script>

<script src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-ui-1.7.2.custom.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/ui.multiselect.js"
        type="text/javascript"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/scrollTo/jquery.scrollTo-min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/localisation/jquery.localisation-min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/util.js"></script>

<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/redmond/jquery-ui-1.7.2.custom.css"/>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/ui.multiselect.css"/>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/common.css"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('#validFrom').datepicker({
            dateFormat: 'yy/mm/dd',
            showOn: 'both',
            buttonImage: '${pageContext.request.contextPath}/moduleResources/facilitydata/images/calendar.gif',
            buttonImageOnly: true
        });
        $('#validTo').datepicker({
            dateFormat: 'yy/mm/dd',
            showOn: 'both',
            buttonImage: '${pageContext.request.contextPath}/moduleResources/facilitydata/images/calendar.gif',
            buttonImageOnly: true
        });
        $.localise('ui-multiselect', { path: '${pageContext.request.contextPath}/moduleResources/facilitydata/js/locale/'});
        $("#multiselect").multiselect();

    });
    function removeHiddenRows() {
        var rows = document.getElementsByTagName("tr");
        var i = 0;
        while (i < rows.length) {
            if (rows[i].style.display == "none") {
                rows[i].parentNode.removeChild(rows[i]);
            }
            else {
                i++;
            }
        }
    }

    function remove(btn) {
        var parent = btn.parentNode;
        while (parent.tagName.toLowerCase() != "tr")
            parent = parent.parentNode;
        parent.style.display = 'none';
        updateRowColors();
    }

    function addQuestion() {
        var tbody = document.getElementById("sectionList");
        var tmpQ = document.getElementById("newSection");
        var newQ = tmpQ.cloneNode(true);
        newQ.style.display = '';
        newQ.id = '';

        //var inputs = newProp.getElementsByTagName("input");
        //for (var i=0; i< inputs.length; i++)
        //	if (inputs[i].type == "text")
        //		inputs[i].value = "";

        tbody.appendChild(newQ);

        updateRowColors();
    }

    function updateRowColors() {
        var tbody = document.getElementById('sectionList');
        var alternator = 1;
        for (var i = 0; i < tbody.rows.length; i++) {
            var qRow = tbody.rows[i++];
            if (qRow.style.display != "none") { // skip deleted rows
                qRow.className = alternator < 0 ? "oddRow" : "evenRow";
                alternator *= -1;
            }
        }
    }

</script>

<p>
    <spring:message code="facilitydata.schema.info"/>
</p>
<a href="schema.form"><spring:message code="facilitydata.add-schema"/></a>
<br/><br/>
<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message
        code="facilitydata.schema.form"/></div>
<form:form commandName="schema" cssClass="box" method="post" onsubmit="removeHiddenRows();">
    <table>
        <tr>
            <td><spring:message code="facilitydata.display-name"/></td>
            <td>
                <form:input path="name" size="50"/>
                <spring:message code="facilitydata.required"/>
                <form:errors path="name" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.schema.frequency"/></td>
            <td>
                <form:select path="frequency" multiple="false">
                    <form:option value=""><spring:message code="facilitydata.select-frequency"/></form:option>
                    <form:options items="${frequencies}"/>
                </form:select>
                <spring:message code="facilitydata.required"/>
                <form:errors cssClass="error" path="frequency"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-from"/></td>
            <td><form:input path="validFrom" id="validFrom"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-to"/></td>
            <td><form:input path="validTo" id="validTo"/></td>
        </tr>
        <tr>
            <td><spring:message code="general.description"/></td>
            <td><form:textarea path="description" cols="70" rows="2"/></td>
        </tr>        
        <tr>
            <td><spring:message code="facilitydata.section"/></td>
            <td>
                <form:select path="formSections" id="multiselect" cssClass="multiselect" multiple="multiple">
                    <form:options items="${sections}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>
    </table>


    <br/><br/>

    <c:if test="${param.id != null}">
        <table>
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

    <tr id="buttonsAtBottom">
        <td><input name="action" type="submit" value="<spring:message code="facilitydata.save-schema"/>"/></td>
        <td><input type="button" value="<spring:message code="general.cancel"/>"
            onclick="document.location.href='schema.list';"/>
        </td>
    </tr>
    </table>
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>