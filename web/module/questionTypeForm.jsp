<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/includeScripts.jsp"%>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp"%>

<style>
	table.questionForm td {padding:5px; font-size:small;}
</style>

<openmrs:require privilege="Manage Facility Data Reports" otherwise="/login.htm" redirect="/module/facilitydata/question.form"/>

<b class="boxHeader"><spring:message code="facilitydata.question-form"/></b>
<div class="box">
	Editing a question type with class ${questionType.class.simpleName}
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>