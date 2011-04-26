<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<script type="text/javascript">
    function addNewOptionRow() {
    	var $newRow = $('#rowOptionTemplate').clone(true).show();
    	$('.optionRow:last').after($newRow);
    }
</script>

<style>
	table.questionForm td {padding:5px; font-size:small;}
</style>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/question.form"/>

<b class="boxHeader"><spring:message code="facilitydata.question-type-form"/></b>
<div class="box">
	<spring:message code="facilitydata.question-type.info"/><br/><br/>
	<frm:form commandName="questionType" method="post">
		<input type="hidden" name="dataType" value="${questionType.class.name}"/>
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

	        <c:choose>
	        	<c:when test="${questionType.class.name == 'org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType'}">
			        <tr>
			            <td><spring:message code="facilitydata.minValue"/></td>
			            <td><frm:input path="minValue" size="10"/> <frm:errors path="minValue" cssClass="error"/></td>
			        </tr>
			        <tr>
			            <td><spring:message code="facilitydata.maxValue"/></td>
			            <td><frm:input path="maxValue" size="10"/> <frm:errors path="maxValue" cssClass="error"/></td>
			        </tr>
			        <tr>
			            <td><spring:message code="facilitydata.allowDecimals"/></td>
			            <td><frm:checkbox path="allowDecimals"/> <frm:errors path="allowDecimals" cssClass="error"/></td>
			        </tr>
	        	</c:when>
	        	<c:when test="${questionType.class.name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
	        		<tr>
			            <td style="vertical-align:top;"><spring:message code="facilitydata.coded-options"/></td>
			            <td>
							<table class="questionForm">
								<thead>
									<tr>
										<th><spring:message code="facilitydata.display-name"/></th>
										<th><spring:message code="facilitydata.option-code"/></th>
										<th><spring:message code="general.description"/></th>
									</tr>
								</thead>
								<tbody id="optionTableBody">
									<c:forEach var="option" items="${questionType.options}">
										<tr class="optionRow">
											<td>
												<input type="hidden" name="optionId" value="${option.id}"/>
												<input type="text" name="optionName" value="${option.name}"/>
											</td>
											<td><input type="text" name="optionCode" value="${option.code}"/></td>
											<td><input type="text" name="optionDescription" size="75" value="${option.description}"/></td>
										</tr>
									</c:forEach>
									<tr id="rowOptionTemplate" class="optionRow" style="display:none;">
										<td><input type="hidden" name="optionId" value=""/><input type="text" name="optionName" value=""/></td>
										<td><input type="text" name="optionCode" value=""/></td>
										<td><input type="text" name="optionDescription" size="75" value=""/></td>
									</tr>
									<tr><td colspan="4">
										<a href="javascript:addNewOptionRow();">
											<spring:message code="facilitydata.add-new-option"/>
										</a>
									</td></tr>
								</tbody>
							</table>
			            </td>
			        </tr>
	        	</c:when>
	        	<c:otherwise></c:otherwise>
	        </c:choose>
	        
	        <tr>
	            <td><spring:message code="general.description"/></td>
	            <td><frm:textarea path="description" cols="70" rows="2"/> <frm:errors path="description" cssClass="error"/></td>
	        </tr>

		    <tr id="buttonsAtBottom">
		        <td colspan="2">
		        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-question"/>"/>
		        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='questionType.list';"/>
		        </td>
		    </tr>     
	    </table>
	    <br/>
	</frm:form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>