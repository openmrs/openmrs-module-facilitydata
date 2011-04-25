<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<script type="text/javascript">

$(document).ready(function() {	
	$('#editSectionDiv').dialog({
		autoOpen: false,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		title: '<spring:message code="facilitydata.section-details"/>',
		width: '75%',
		height: $(window).height()/2,
		position: [150,150],
		zIndex:990
	});

	$('#saveSectionButton').click(function(event){
		var sectionId = $('#sectionIdField').val();
		var sectionName = $('#sectionNameField').val();
		if (sectionName == '') {
			$('#sectionErrorSpan').html('<spring:message code="facilitydata.required-field"/>').show();
		}
		window.location.href='saveSection.form?schema=${schema.id}&id='+ sectionId + '&name='+sectionName;
	});
	
	$('#sectionList').dataTable({
	    "bPaginate": false,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": false,
	    "bAutoWidth": false,
	    "bSortable": false
	});
});

function editSection(existingId, existingName) {
	$('#sectionIdField').val(existingId);
	$('#sectionNameField').val(existingName);
	$('#editSectionDiv').dialog('open');
}

function moveSection(existingIndex, newIndex) {
	window.location.href='moveSection.form?schema=${schema.id}&existingIndex='+ existingIndex + '&newIndex='+newIndex;
}

</script>

<b class="boxHeader">${schema.name}</b>
<div class="box">
	<table width="100%">
		<tr>
			<td width="33%">
				<fieldset>
					<legend>
						<a href="schemaForm.form?id=${schema.id}">
							<spring:message code="facilitydata.schema-details"/>
						</a>
					</legend>
					<table class="facilityDataTable">
						<tr>
							<th><spring:message code="facilitydata.display-name"/></th>
							<td>${schema.name}</td>				
						</tr>
						<tr>
							<th><spring:message code="facilitydata.schema.frequency"/></th>
							<td>${schema.frequency}</td>				
						</tr>
						<tr>
							<th><spring:message code="facilitydata.valid-from"/></th>
							<td><openmrs:formatDate date="${schema.validFrom}"/></td>
						</tr>
						<tr>
							<th><spring:message code="facilitydata.valid-to"/></th>
							<td><openmrs:formatDate date="${schema.validTo}"/></td>			
						</tr>
						<tr>
							<th><spring:message code="general.description"/></th>
							<td>${schema.description}</span></td>				
						</tr>
					</table>
				</fieldset>
				<br/>
				<fieldset>
					<legend>
						<spring:message code="facilitydata.sections"/>
					</legend>
					<table id="sectionList" class="facilityDataTable" style="width:100%">
						<thead>
							<tr>
								<th style="width:100%"><spring:message code="facilitydata.display-name"/></th>
								<th style="white-space:nowrap;"><spring:message code="facilitydata.actions"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${schema.sections}" var="section" varStatus="sectionStatus">
								<tr>
									<td>${section.name}</td>
									<td style="white-space:nowrap; text-align:center;">
										<img class="actionImage" src='<c:url value="/images/edit.gif"/>' border="0" onclick="editSection('${section.id}','${section.name}');"/>
										<c:if test="${fn:length(schema.sections) > 1}">
											<c:choose>
												<c:when test="${!sectionStatus.first}">
													<img class="actionImage" src='<c:url value="/images/moveup.gif"/>' border="0" onclick="moveSection('${sectionStatus.index}','${sectionStatus.index-1}');"/>
												</c:when>
												<c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${!sectionStatus.last}">
													<img class="actionImage" src='<c:url value="/images/movedown.gif"/>' border="0" onclick="moveSection('${sectionStatus.index}','${sectionStatus.index+1}');"/>
												</c:when>
												<c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;</c:otherwise>
											</c:choose>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<a href="javascript:editSection('','');" ><spring:message code="facilitydata.add-section"/></a>
					
					<div id="editSectionDiv" style="display:none;">
						<table>
							<tr>
								<th><spring:message code="facilitydata.display-name"/></th>
								<td>
									<input id="sectionIdField" type="hidden"/>
									<input id="sectionNameField" type="text" name="name" size="50"/> <span class="error" id="sectionErrorSpan" style="display:none;"></span>
								</td>
							</tr>
							<tr>
								<th colspan="2"><input id="saveSectionButton" type="button" value="<spring:message code="general.save"/>"/></th>
							</tr>
						</table>
					</div>
				</fieldset>
			</td>
			<td style="width:67%; vertical-align:top;">
				<fieldset>
					<legend>
						<spring:message code="facilitydata.questions"/>
					</legend>
					<br/>
					<c:if test="${empty schema.sections}">
						<i><spring:message code="facilitydata.add-section-to-add-question-message"/></i>
					</c:if>
					<c:forEach items="${schema.sections}" var="section">
						${section.name}
						<br/>
						<a href=""><spring:message code="facilitydata.formQuestion.create"/></a>
					</c:forEach>
				</fieldset>
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
