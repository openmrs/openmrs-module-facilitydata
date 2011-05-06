<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<style>
	table.schemaForm td {padding:5px; font-size:small;}
</style>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<a href="form.list"><spring:message code="facilitydata.manage-form"/></a>
	-&gt;
	<c:choose>
		<c:when test="${schema.id == null}"><spring:message code="facilitydata.new-schema"/></c:when>
		<c:otherwise>${schema.name}</c:otherwise>
	</c:choose>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.schema.form"/></b>
<div class="box">
	<spring:message code="facilitydata.schema.info"/><br/><br/>
	<frm:form commandName="schema" method="post">
	    <table class="schemaForm">
	    	<tr><th colspan="2"><spring:message code="facilitydata.form-details"/></th></tr>
	        <tr>
	            <td>
	            	<spring:message code="facilitydata.display-name"/>
	            	<spring:message code="facilitydata.required"/>
	            </td>
	            <td>
	                <frm:input path="form.name" size="50"/>
	                <frm:errors path="form.name" cssClass="error"/>
	            </td>
	        </tr>
	        <tr>
	            <td>
	            	<spring:message code="facilitydata.schema.frequency"/>
	            	<spring:message code="facilitydata.required"/>
	            </td>
	            <td>
	                <frm:select path="form.frequency" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.select-frequency"/></frm:option>
	                    <frm:options items="${frequencies}"/>
	                </frm:select>
	                <frm:errors cssClass="error" path="form.frequency"/>
	            </td>
	        </tr>
	        <tr>
	        	<td><spring:message code="general.description"/></td>
				<td><frm:textarea path="form.description" cols="70" rows="2"/></td>
			</tr>   
	        <tr><th colspan="2" style="padding-top:20px;"><spring:message code="facilitydata.schema-details"/></th></tr>
	        <tr>
	            <td><spring:message code="facilitydata.display-name"/></td>
	            <td>
	                <frm:input path="name" size="50"/>
	                <frm:errors path="name" cssClass="error"/>
	            </td>
	        </tr>
	        <tr>
	            <td><spring:message code="facilitydata.valid-from"/></td>
	            <td><frm:input path="validFrom" id="validFrom" onclick="showCalendar(this);"/>
	            <frm:errors cssClass="error" path="validFrom"/></td>
	        </tr>
	        <tr>
	            <td><spring:message code="facilitydata.valid-to"/></td>
	            <td><frm:input path="validTo" id="validTo" onclick="showCalendar(this);"/>
	            <frm:errors cssClass="error" path="validTo"/></td>
	        </tr>
		    <tr id="buttonsAtBottom">
		        <td colspan="2">
		        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-schema"/>"/>
		        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='form.list'"/>
		        </td>
		    </tr>     
	    </table>
	    <br/>
	</frm:form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>
