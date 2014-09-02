<!-- Include css from module -->
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/facilitydata/dataTables/css/page.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/facilitydata/dataTables/css/table.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/facilitydata/dataTables/css/custom.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/facilitydata/autocomplete/jquery.autocomplete.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/facilitydata/facilitydata.css"/>

<!-- Include javascript from core -->
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<!-- Include javascript from module -->
<openmrs:htmlInclude file='${pageContext.request.contextPath}/moduleResources/facilitydata/dataTables/jquery.dataTables.min.js'/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}//moduleResources/facilitydata/autocomplete/jquery.autocomplete.min.js" />
<openmrs:htmlInclude file="${pageContext.request.contextPath}//moduleResources/facilitydata/jquery.cookie.js" />

<script>
    $j = jQuery;
</script>