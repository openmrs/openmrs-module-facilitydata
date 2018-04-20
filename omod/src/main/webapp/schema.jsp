<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<script type="text/javascript">

$j(document).ready(function() {
	$j('#editSectionDiv').dialog({
		autoOpen: false,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		title: '<spring:message code="facilitydata.section-details"/>',
		width: '75%',
		height: $j(window).height()/2,
		position: [150,150],
		zIndex:990
	});
	
	$j('#editQuestionDiv').dialog({
		autoOpen: false,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		title: '<spring:message code="facilitydata.question-details"/>',
		width: '75%',
		height: $j(window).height()/2,
		position: [150,150],
		zIndex:990
	});
	
	$j('#moveQuestionDiv').dialog({
		autoOpen: false,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		title: '<spring:message code="facilitydata.move-question"/>',
		width: '75%',
		height: $j(window).height()/2,
		position: [150,150],
		zIndex:990
	});
	
	$j('#cloneSchemaDiv').dialog({
		autoOpen: false,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		title: '<spring:message code="facilitydata.clone-schema"/>',
		width: '75%',
		height: $j(window).height()/2,
		position: [150,150],
		zIndex:990
	});
	
	<c:forEach items="${schema.sections}" var="section">
		$j('#deleteSection${section.id}').dialog({
			autoOpen: false,
			modal: true,
			draggable: false,
			closeOnEscape: false,
			title: "<spring:message code="facilitydata.delete-section"/>: ${section.name}",
			width: '75%',
			height: $j(window).height()/2,
			position: [150,150],
			zIndex:990
		});
	</c:forEach>

	$j('#saveSectionButton').click(function(event){
		var sectionId = $j('#editSectionIdField').val();
		var sectionName = $j('#editSectionNameField').val();
		if (sectionName == '') {
			$j('#sectionErrorSpan').html('<spring:message code="facilitydata.required-field"/>').show();
		}
		window.location.href='saveSection.form?schema=${schema.id}&id='+ sectionId + '&name='+sectionName;
	});
	
	$j('#saveQuestionButton').click(function(event){
		var url = 'saveFormQuestion.form?schema=${schema.id}'
		url += '&sectionId=' + $j('#sectionIdField').val();
		url += '&formQuestionId=' + $j('#questionIdField').val();
		url += '&name=' + escape($j('#questionNameField').val());
		url += '&questionNumber=' + escape($j('#questionNumberField').val());
		url += '&question=' + $j('#questionField').val();
		console.log(url);
		window.location.href=url;
	});
	
	$j('#moveQuestionButton').click(function(event){
		var url = 'moveFormQuestion.form?schema=${schema.id}';
		url += '&formQuestionId=' + $j('#moveQuestionIdField').val();
		url += '&fromSectionId=' + $j('#moveFromSectionIdField').val();
		url += '&toSectionId=' + $j('#moveToSectionIdField').val();
		window.location.href = url;
	});
	
	$j('#sectionList').dataTable({
	    "bPaginate": false,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": false,
	    "bAutoWidth": false,
	    "bSortable": false
	});
	
	$j('#questionTable').dataTable({
	    "bPaginate": false,
	    "bLengthChange": false,
	    "bFilter": false,
	    "bSort": false,
	    "bInfo": false,
	    "bAutoWidth": false,
	    "bSortable": false
	});		
	
	$j('#sectionChooser').change(function(event){
		var sectionId = $j(this).val();
		$j('.questionRow').hide();
		$j('.questionRow'+sectionId).show();
		$j('#sectionIdField').val(sectionId);
		$j.cookie("facilitydata_lastSchemaSection", sectionId);
	});
	
	var savedSection = $j.cookie("facilitydata_lastSchemaSection");
	if (savedSection != null && savedSection != '') {
		$j('#sectionChooser').val(savedSection).change();
	}
	$j('#sectionIdField').val($j('#sectionChooser').val());
	
	$j('#questionField').change(function(event){
		if ($j('#questionNameField').val() == '') {
			$j('#questionNameField').val($j(this).find('option:selected').text());
		}
	});

	$j('#cloneSchemaButton').click(function(event){
		$j('#cloneSchemaDiv').dialog('open');
	});
	
	$j('#deleteSchemaButton').click(function(event){
		if (confirm('<spring:message code="facilitydata.schema.delete-warning"/>')) {
			window.location.href='deleteSchema.form?schemaId=${schema.id}';
		}
	});

});

function editSection(existingId) {
	$j('#editSectionIdField').val(existingId);
	var existingName = $j("#sectionName"+existingId).html();
	$j('#editSectionNameField').val(existingName);
	$j('#editSectionDiv').dialog('option', 'title', '<spring:message code="facilitydata.edit-section"/>: ' + existingName).dialog('open');
}

function deleteSection(id) {
	$j('#deleteSection'+id).dialog('open');
}

function moveSection(existingIndex, newIndex) {
	window.location.href='moveSection.form?schema=${schema.id}&existingIndex='+ existingIndex + '&newIndex='+newIndex;
}

function editQuestion(id, questionNumber, question) {
	$j('#questionIdField').val(id);
	var existingName = $j("#questionName"+id).html();
	$j('#questionNameField').val(existingName);
	$j('#questionNumberField').val(questionNumber);
	$j('#questionField').val(question);
	$j('#editQuestionDiv').dialog('option', 'title', '<spring:message code="facilitydata.edit-form-question"/>: ' + existingName).dialog('open');
}

function moveQuestion(questionId, sectionId) {
	$j('#moveQuestionIdField').val(questionId);
	$j('#moveFromSectionIdField').val(sectionId);
	$j('#moveQuestionDiv').dialog('open');
}

function deleteQuestion(questionId, sectionId) {
	if (confirm('<spring:message code="facilitydata.confirm-delete-form-question"/>')) {
		window.location.href='deleteFormQuestion.form?schema=${schema.id}&questionId='+ questionId + '&sectionId='+sectionId;
	}
}

</script>

<div class="facilityDataHeader">
	<a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message code="facilitydata.dashboard"/></a>
	-&gt;
	<a href="form.list"><spring:message code="facilitydata.manage-form-schema"/></a>
	-&gt;
	${schema.name}
	<hr/>
</div>

<b class="boxHeader">${schema.name}</b>
<div class="box">
	<table width="100%">
		<tr>
			<td width="33%" style="vertical-align:top;">
				<fieldset>
					<legend>
						<a href="schemaForm.form?schema=${schema.id}">
							<spring:message code="facilitydata.schema-details"/>
						</a>
					</legend>
					<table class="facilityDataTable">
						<tr>
							<th><spring:message code="facilitydata.display-name" htmlEscape="false"/></th>
							<td>${schema.name}</td>				
						</tr>
						<tr>
							<th><spring:message code="facilitydata.schema.frequency" htmlEscape="false"/></th>
							<td>${schema.form.frequency}</td>				
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
									<td><span id="sectionName${section.id}">${section.name}</span> (${fn:length(section.questions)})</td>
									<td style="white-space:nowrap; text-align:center;">
										<img class="actionImage" src='<c:url value="/images/edit.gif"/>' border="0" onclick='editSection(${section.id});'/>
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
										<img class="actionImage" src='<c:url value="/images/trash.gif"/>' border="0" onclick="deleteSection('${section.id}');"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:forEach items="${schema.sections}" var="section" varStatus="sectionStatus">
						<div id="deleteSection${section.id}" style="display:none;">
							
							<form action="deleteSection.form">
								<input type="hidden" name="schema" value="${schema.id}"/>
								<input type="hidden" name="sectionId" value="${section.id}"/>
								
								<c:if test="${fn:length(section.questions) > 0}">
									<spring:message code="facilitydata.section.delete-with-questions-warning"/>
									<br/>
									<select name="newQuestionSectionId">
										<option value=""><spring:message code="facilitydata.choose-section"/></option>
										<c:forEach items="${schema.sections}" var="s">
											<c:if test="${s != section}">
												<option value="${s.id}">${s.name}</option>
											</c:if>
										</c:forEach>
									</select>
								</c:if>
								
								<spring:message code="facilitydata.section.delete-warning" htmlEscape="false"/>
								<input type="submit" value="<spring:message code="general.delete"/>"/>
							</form>
						</div>					
					</c:forEach>

					<a href="javascript:editSection('');" ><spring:message code="facilitydata.add-section" htmlEscape="false"/></a>
					
					<div id="editSectionDiv" style="display:none;">
						<table>
							<tr>
								<th><spring:message code="facilitydata.display-name" htmlEscape="false"/></th>
								<td>
									<input id="editSectionIdField" type="hidden"/>
									<input id="editSectionNameField" type="text" name="name" size="50"/> <span class="error" id="sectionErrorSpan" style="display:none;"></span>
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
						<spring:message code="facilitydata.questions" htmlEscape="false"/>
					</legend>
					<br/>
					<c:if test="${empty schema.sections}">
						<i><spring:message code="facilitydata.add-section-to-add-question-message"/></i>
					</c:if>
					<div id="questionsInSectionDiv" style="${empty schema.sections ? 'display:none;' : ''}">
						<spring:message code="facilitydata.questions-in-section" htmlEscape="false"/>
						<select id="sectionChooser">
							<c:forEach items="${schema.sections}" var="section">
								<option value="${section.id}">${section.name}</option>
							</c:forEach>
						</select>
						<table width="100%" class="facilityDataTable" id="questionTable">
							<thead>
								<tr>
									<th style="text-decoration:nowrap;">#</th>
									<th style="width:100%"><spring:message code="facilitydata.question" htmlEscape="false"/></th>
									<th style="text-decoration:nowrap;"><spring:message code="facilitydata.actions" htmlEscape="false"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${schema.sections}" var="section" varStatus="sectionStatus">
									<c:choose>
										<c:when test="${empty section.questions}">
										</c:when>
										<c:otherwise>
											<c:forEach items="${section.questions}" var="question">
												<tr class="questionRow questionRow${section.id}" style="${sectionStatus.index == 0 ? '' : 'display:none;'}">
													<td style="white-space:nowrap;">${question.questionNumber}</td>
													<td width="100%"><span id="questionName${question.id}">${question.name}</span></td>
													<td style="white-space:nowrap;">
														<img class="actionImage" src='<c:url value="/images/edit.gif"/>' border="0" onclick="editQuestion('${question.id}','${question.questionNumber}','${question.question.id}');"/>
														<img class="actionImage" src='<c:url value="/images/lookup.gif"/>' border="0" onclick="moveQuestion('${question.id}','${section.id}');"/>
														<c:if test="${questionBreakdown[question.id] == null || questionBreakdown[question.id] == 0}">
															<img class="actionImage" src='<c:url value="/images/trash.gif"/>' border="0" onclick="deleteQuestion('${question.id}','${section.id}');"/>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tbody>
						</table>
						<a href="javascript:editQuestion('','','');"><spring:message code="facilitydata.add-form-question" htmlEscape="false"/></a>
					</div>
					
					<div id="editQuestionDiv" style="display:none;">
						<input id="questionIdField" type="hidden" value=""/>
						<input id="sectionIdField" type="hidden" value=""/>
						<table>
							<tr>
								<th><spring:message code="facilitydata.question.number" htmlEscape="false"/></th>
								<td><input id="questionNumberField" type="text" name="questionNumber" size="15" style="font-size:small;"/></td>
							</tr>
							<tr>
								<th><spring:message code="facilitydata.question" htmlEscape="false"/></th>
								<td>
									<select id="questionField" name="question" style="font-size:small;">
										<option value=""><spring:message code="facilitydata.choose"/>...</option>
										<c:forEach items="${questions}" var="q">
											<option value="${q.id}">${q.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="facilitydata.question.display-name" htmlEscape="false"/></th>
								<td><input id="questionNameField" type="text" name="name" size="75" style="font-size:small;"/></td>
							</tr>
							<tr>
								<th colspan="2"><input id="saveQuestionButton" type="button" value="<spring:message code="general.save"/>"/></th>
							</tr>
						</table>
					</div>
					<div id="moveQuestionDiv" style="display:none;">
						<input id="moveQuestionIdField" type="hidden" value=""/>
						<input id="moveFromSectionIdField" type="hidden" value=""/>
						<table>
							<tr>
								<th><spring:message code="facilitydata.new-section-for-question" htmlEscape="false"/></th>
								<td>
									<select id="moveToSectionIdField" style="font-size:small;">
										<option value=""><spring:message code="facilitydata.choose"/>...</option>
										<c:forEach items="${schema.sections}" var="section">
											<option value="${section.id}">${section.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th colspan="2"><input id="moveQuestionButton" type="button" value="<spring:message code="general.save"/>"/></th>
							</tr>
						</table>
					</div>
				</fieldset>
			</td>
		</tr>
	</table>
</div>
<br/>
<input type="button" id="cloneSchemaButton" value="<spring:message code="facilitydata.clone-schema"/>"/>

<c:if test="${lastStartDate == null}">
	&nbsp;&nbsp;&nbsp;
	<input type="button" id="deleteSchemaButton" value="<spring:message code="facilitydata.delete-schema"/>"/>
</c:if>

<div id="cloneSchemaDiv" style="display:none;">
	<spring:message code="facilitydata.clone-date-message"/><br/><br/>
	<form action="cloneSchema.form">
		<input type="hidden" name="schema" value="${schema.id}"/>
		<spring:message code="facilitydata.clone-date"/>: <input type="text" name="startDate" onclick="showCalendar(this);"/>
		<br/><br/>
		<input id="moveQuestionButton" type="submit" value="<spring:message code="general.save"/>"/>
	</form>
</div>
<br/>
<%@ include file="/WEB-INF/template/footer.jsp" %>
