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
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp" %>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-1.3.2.min.js"></script>

<script src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery-ui-1.7.2.custom.min.js"
        type="text/javascript"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/util.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/moduleResources/facilitydata/js/jquery.dataTables.js"></script>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/moduleResources/facilitydata/css/redmond/jquery-ui-1.7.2.custom.css"/>

<style type="text/css">

    td.description {
        padding-top: 0px;
    }

    #buttonsAtBottom {
        padding: 5px;
    }
</style>
<script type="text/javascript">
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
        var tbody = document.getElementById("questionList");
        var tmpQ = document.getElementById("newQuestion");
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

    function descriptionFocus(textarea) {
        textarea.style.borderColor = '#59a';
    }

    function descriptionBlur(textarea) {
        textarea.style.borderColor = 'transparent';
    }

    function updateRowColors() {
        var tbody = document.getElementById('questionList');
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
    <spring:message code="facilitydata.section.info"/>
</p>
<a href="section.form"><spring:message code="facilitydata.add-section"/></a>
<br/><br/>
<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message
        code="facilitydata.section.form"/></div>
<form:form commandName="section" cssClass="box" method="post" onsubmit="removeHiddenRows();">
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
            <td><spring:message code="general.description"/></td>
            <td><form:textarea path="description" cols="70" rows="2"/></td>
        </tr>
    </table>
    <br/><br/><br/>
    <strong class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message
            code="facilitydata.questions"/></strong>
    <table cellpadding="1" cellspacing="0" class="box">
       <thead>
            <th><spring:message code="general.name"/></th>
            <th><spring:message code="facilitydata.question.number"/></th>
            <th><spring:message code="facilitydata.question"/></th>
            <th><spring:message code="general.description"/></th>
        </thead>
        <tbody id="questionList" style="table-layout:auto;text-align:left;">
        <c:forEach var="question" items="${section.questions}" varStatus="status">
            <tr class="${status.index % 2 == 0 ? "evenRow" : "oddRow"}">
                <input type="hidden" name="form_question_id" value="${question.id}"/>
                <td valign="top"><input type="text" name="displayName" value="${question.name}" size="50" maxlength="250"/>
                </td>
                <td valign="top">
                    <input type="text" name="questionNumber" value="${question.questionNumber}" size="30"
                           maxlength="40"/>
                </td>
                <td valign="top" style="width:.7em;">
                    <select name="questionId">
                        <option value=""><spring:message code="facilitydata.select-one"/></option>
                        <c:forEach items="${questions}" var="q">
                            <option value="${q.id}" ${question.question.id == q.id ? "selected" : ""}>${q.name}</option>
                        </c:forEach>
                    </select>
                </td>

                <td valign="top" class="description">
                    <textarea name="desc"
                              rows="2" cols="96">${question.description}</textarea>
                </td>
                <td valign="top">
                    <input type="button" style="font-size:1.2em;" value='<spring:message code="general.remove" />'
                                                    class="closeButton" onclick="remove(this);"/></td>
            </tr>
        </c:forEach>
        <tr id="newQuestion" style="display:none;text-align:left;">
            <td valign="top"><input type="text" name="displayName" value="" size="50" maxlength="250"/></td>
            <td valign="top">
                <input type="text" name="questionNumber" value="" size="30"
                       maxlength="40"/>
            </td>
            <td valign="top">
                <select name="questionId">
                    <option value=""><spring:message code="facilitydata.select-one"/></option>
                    <c:forEach items="${questions}" var="q">
                        <option value="${q.id}">${q.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td valign="top" class="description">
                <textarea name="desc"
                          rows="2" cols="96">${question.description}</textarea>
            </td>
            <td valign="top"><input style="font-size:1.2em;" type="button"
                                    value='<spring:message code="general.remove" />' class="closeButton"
                                    onclick="remove(this);"/></td>
        </tr>
        </tbody>
    </table>
    <br/>
    <div align="left">
        &nbsp;&nbsp;<input class="ui-corner-all ui-state-default" type="button" onclick="addQuestion();"
               value='<spring:message code="facilitydata.add-question" />'/>
    </div>


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
        <td><input name="action" type="submit" value="<spring:message code="facilitydata.save-section"/>"/></td>
        <td><input type="button" value="<spring:message code="general.cancel"/>"
                   onclick="document.location.href='section.list';"/>
        </td>
    </tr>
    </table>
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>