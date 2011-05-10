<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<script type="text/javascript">
    function addNewOptionRow() {
    	var $newRow = $('#rowOptionTemplate').clone(true).show();
    	$('.optionRow:last').after($newRow);
    }
    
    function deleteOption(element, id, count) {
    	if (count == '') {
    		if (confirm('<spring:message code="facilitydata.codedOption.unused-delete-warning"/>')) {
    			$(element).parent().parent().remove();
    		}
    	}
    	else {
    		if (confirm('<spring:message code="facilitydata.codedOption.used-delete-warning"/>')) {
    			$(element).parent().parent().find("input").addClass("retSty");
    			$(element).parent().parent().find("input").attr("disabled", "disabled");
    		}
    	}
    }
    
    function undeleteOption(element, id) {
   		if (confirm('<spring:message code="facilitydata.codedOption.undelete-warning"/>')) {
   			$(element).parent().parent().find("input").removeClass("retSty");
   			$(element).parent().parent().find("input").removeAttr("disabled");
   		}
    }
    
</script>

<style>
	table.questionForm td {padding:5px; font-size:small;}
	.retSty {text-decoration:line-through; background-color:lightgrey;}
</style>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/question.form"/>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<a href="questionType.list"><spring:message code="facilitydata.manage-question-type"/></a>
	-&gt;
	<c:choose>
		<c:when test="${questionType.id == null}"><spring:message code="facilitydata.new-question-type"/></c:when>
		<c:otherwise>${questionType.name}</c:otherwise>
	</c:choose>
	<hr/>
</div>

<b class="boxHeader"><spring:message code="facilitydata.question-type-form"/></b>
<div class="box">
	<spring:message code="facilitydata.question-type.info"/><br/><br/>
	<form method="post">
	    <table class="questionForm">
	        <tr>
	            <td>
	            	<spring:message code="facilitydata.display-name"/>
	            	<spring:message code="facilitydata.required"/>
	            </td>
	            <td><input name="name" size="50" value="${questionType.name}"/></td>
	        </tr>
	        <tr>
	            <td><spring:message code="general.description"/></td>
	            <td><textarea name="description" cols="70" rows="2"/>${questionType.description}</textarea></td>
	        </tr>

	        <c:choose>
	        	<c:when test="${questionType.class.name == 'org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType'}">
			        <tr>
			            <td><spring:message code="facilitydata.minValue"/></td>
			            <td><input name="minValue" size="50" value="${questionType.minValue}"/></td>
			        </tr>
			        <tr>
			            <td><spring:message code="facilitydata.maxValue"/></td>
			            <td><input name="maxValue" size="50" value="${questionType.maxValue}"/></td>
			        </tr>
			        <tr>
			            <td><spring:message code="facilitydata.allowDecimals"/></td>
			            <td><input type="checkbox" name="allowDecimals" ${questionType.allowDecimals ? 'checked="checked"' : ""}/></td>
			        </tr>
	        	</c:when>
	        	<c:when test="${questionType.class.name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
	        		<tr>
			            <td style="vertical-align:top;"><spring:message code="facilitydata.coded-options"/></td>
			            <td>
							<table class="questionForm">
								<thead>
									<tr>
										<th><spring:message code="facilitydata.display-name"/><spring:message code="facilitydata.required"/></th>
										<th><spring:message code="facilitydata.option-code"/><spring:message code="facilitydata.required"/></th>
										<th><spring:message code="general.description"/></th>
										<th></th>
									</tr>
								</thead>
								<tbody id="optionTableBody">
									<c:forEach var="option" items="${questionType.options}">
										<c:set var="retAtt" value="${option.retired ? 'style=\"retiredRow\" disabled=\"disabled\"' : ''}"/>
										<tr class="optionRow">
											<td>
												<input type="hidden" name="optionId" value="${option.id}" ${retAtt}/>
												<input type="text" name="optionName" value="${option.name}" ${retAtt}/>
											</td>
											<td><input type="text" name="optionCode" value="${option.code}" ${retAtt}/></td>
											<td><input type="text" name="optionDescription" size="75" value="${option.description}" ${retAtt}/></td>
											<td>
												<c:choose>
													<c:when test="${option.retired}">
														<img class="actionImage" src='<c:url value="/images/add.gif"/>' border="0" onclick="undeleteOption(this, '${option.id}');"/>
													</c:when>
													<c:otherwise>
														<img class="actionImage" src='<c:url value="/images/delete.gif"/>' border="0" onclick="deleteOption(this, '${option.id}','${optionBreakdown[option.id]}');"/>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
									<tr id="rowOptionTemplate" class="optionRow" style="display:none;">
										<td>
											<input type="hidden" name="optionId" value=""/>
											<input type="text" name="optionName" value=""/>
										</td>
										<td><input type="text" name="optionCode" value=""/></td>
										<td><input type="text" name="optionDescription" size="75" value=""/></td>
										<td><img class="actionImage" src='<c:url value="/images/delete.gif"/>' border="0" onclick="deleteOption(this, '', '');"/></td>
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

		    <tr id="buttonsAtBottom">
		        <td colspan="2">
		        	<input name="action" type="submit" value="<spring:message code="facilitydata.save-question-type"/>"/>
		        	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='questionType.list';"/>
		        </td>
		    </tr>     
	    </table>
	    <br/>
	</form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>