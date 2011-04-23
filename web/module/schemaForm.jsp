<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<style>
	table.schemaForm td {padding:5px;}
</style>

<b class="boxHeader"><spring:message code="facilitydata.schema.form"/></b>
<br/>
<p><spring:message code="facilitydata.schema.info"/></p>
<frm:form commandName="schema" cssClass="box" method="post" onsubmit="removeHiddenRows();">
    <table class="schemaForm">
        <tr>
            <td>
            	<spring:message code="facilitydata.display-name"/>
            	<spring:message code="facilitydata.required"/>
            </td>
            <td>
                <frm:input path="name" size="50"/>
                <frm:errors path="name" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>
            	<spring:message code="facilitydata.schema.frequency"/>
            	<spring:message code="facilitydata.required"/>
            </td>
            <td>
                <frm:select path="frequency" multiple="false">
                    <frm:option value=""><spring:message code="facilitydata.select-frequency"/></frm:option>
                    <frm:options items="${frequencies}"/>
                </frm:select>
                <frm:errors cssClass="error" path="frequency"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-from"/></td>
            <td><frm:input path="validFrom" id="validFrom" onclick="showCalendar(this);"/></td>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.valid-to"/></td>
            <td><frm:input path="validTo" id="validTo" onclick="showCalendar(this);"/></td>
        </tr>
        <tr>
            <td><spring:message code="general.description"/></td>
            <td><frm:textarea path="description" cols="70" rows="2"/></td>
        </tr>   
	    <tr id="buttonsAtBottom">
	        <td colspan="2">
	        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-schema"/>"/>
	        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='schema.list';"/>
	        </td>
	    </tr>     
    </table>
    <br/>
</frm:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>
