<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>

<style>
	table.sectionForm td {padding:5px; font-size:small;}
</style>

<frm:form commandName="section" method=post">
	<input type="hidden" name="schema" value="${schema.id}"/>
	<table>
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
	    <tr id="buttonsAtBottom">
	        <td colspan="2">
	        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-schema"/>"/>
	        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='schema.form?id=${schema.id}';"/>
	        </td>
	    </tr>
	<table>
</frm:form>
