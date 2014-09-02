<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<style>
	table.questionForm td {padding:5px; font-size:small;}
</style>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/question.form"/>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<a href="question.list"><spring:message code="facilitydata.manage-question"/></a>
	-&gt;
	<c:choose>
		<c:when test="${question.id == null}"><spring:message code="facilitydata.new-question"/></c:when>
		<c:otherwise>${question.name}</c:otherwise>
	</c:choose>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.question-form"/></b>
<div class="box">
	<spring:message code="facilitydata.question.info"/><br/><br/>
	<frm:form commandName="question" method="post">
	    <table class="questionForm">
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
	            <td width="20%">
	            	<spring:message code="facilitydata.period-applicability"/>
	            	<spring:message code="facilitydata.required"/>
	            </td>
	            <td>
	                <frm:select path="periodApplicability" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.choose"/>...</frm:option>
	                    <frm:options items="${periodApplicabilities}"/>
	                </frm:select>
	                <frm:errors cssClass="error" path="periodApplicability"/>
	            </td>
	        </tr>
	        <tr>
	        	<td>
	        		<spring:message code="facilitydata.question-type"/>
	        		<spring:message code="facilitydata.required"/>
	        	</td>
	        	<td>
	        		<frm:select path="questionType" multiple="false">
	                    <frm:option value=""><spring:message code="facilitydata.choose"/>...</frm:option>
	                    <frm:options items="${allQuestionTypes}" itemValue="id" itemLabel="name" />
	                </frm:select>
	                <frm:errors cssClass="error" path="questionType"/>
	        	</td>
	        </tr>
	        <tr>
	            <td><spring:message code="general.description"/></td>
	            <td><frm:textarea path="description" cols="70" rows="2"/></td>
	        </tr>   
		    <tr id="buttonsAtBottom">
		        <td colspan="2">
		        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-question"/>"/>
		        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='question.list';"/>
		        </td>
		    </tr>     
	    </table>
	    <br/>
	</frm:form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>