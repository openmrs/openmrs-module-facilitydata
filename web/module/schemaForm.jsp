<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

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
</script>

<p>
    <spring:message code="facilitydata.schema.info"/>
</p>
<div class="boxHeader" style="font-weight:bold;text-align:center;"><spring:message
        code="facilitydata.schema.form"/></div>
<frm:form commandName="schema" cssClass="box" method="post" onsubmit="removeHiddenRows();">
    <table>
        <tr>
            <td><spring:message code="facilitydata.display-name"/></td>
            <td>
                <frm:input path="name" size="50"/>
                <spring:message code="facilitydata.required"/>
                <frm:errors path="name" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.schema.frequency"/></td>
            <td>
                <frm:select path="frequency" multiple="false">
                    <frm:option value=""><spring:message code="facilitydata.select-frequency"/></frm:option>
                    <frm:options items="${frequencies}"/>
                </frm:select>
                <spring:message code="facilitydata.required"/>
                <frm:errors cssClass="error" path="frequency"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-from"/></td>
            <td><frm:input path="validFrom" id="validFrom"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-to"/></td>
            <td><frm:input path="validTo" id="validTo"/></td>
        </tr>
        <tr>
            <td><spring:message code="general.description"/></td>
            <td><frm:textarea path="description" cols="70" rows="2"/></td>
        </tr>        
        <tr>
            <td><spring:message code="facilitydata.section"/></td>
            <td>
                <frm:select path="formSections" id="multiselect" cssClass="multiselect" multiple="multiple">
                    <frm:options items="${sections}" itemValue="id" itemLabel="name"/>
                </frm:select>
            </td>
        </tr>
    </table>


    <br/><br/>

    <c:if test="${param.id != null}">
        <table>
            <tr>
                <td><spring:message code="general.retired"/></td>
                <td><frm:checkbox path="retired" id="retired" onchange="checkRetired();"/></td>
            </tr>
            <tr style="display:none;" id="reason">
                <td><spring:message code="general.retiredReason"/></td>
                <td>
                    <frm:input path="retireReason"/>
                    <frm:errors path="retireReason" cssClass="error"/>
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
</frm:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>
