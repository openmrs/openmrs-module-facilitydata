<%@ include file="/WEB-INF/view/module/facilitydata/include/include.jsp" %>
<%@ include file="/WEB-INF/view/module/facilitydata/include/localHeader.jsp" %>

<openmrs:require anyPrivilege="View Facility Data Reports,Enter Facility Data Reports" otherwise="/login.htm"
                 redirect="/module/facilitydata/formEntry.form"/>

<div class="facilityDataHeader">
    <a href="${pageContext.request.contextPath}/module/facilitydata/dashboard.list"><spring:message
            code="facilitydata.dashboard"/></a>
    -&gt;
    <a href="formEntryOverview.form?form=${schema.form.id}">${schema.form.name}</a>
    -&gt;
    ${facility.name} -
    <c:choose>
        <c:when test="${schema.form.frequency == 'DAILY'}">${facilitydata:formatDate(fromDate, 'dd/MMM/yyyy', '')}</c:when>
        <c:otherwise>${facilitydata:formatDate(fromDate, 'MMM yyyy', '')}</c:otherwise>
    </c:choose>
    <hr/>
</div>

<script type="text/javascript">

    var autocompleteQuestionOptions = {};
    <c:forEach items="${questionTypes}" var="qt">
    <c:if test="${qt['class'].name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
    var typeOptions = new Array();
    <c:forEach items="${qt.options}" var="option">
    <c:if test="${!option.retired}">
    typeOptions.push({"code": "${option.id}", "label": "${option.name}"});
    </c:if>
    </c:forEach>
    autocompleteQuestionOptions['${qt.id}'] = typeOptions;
    </c:if>
    </c:forEach>

    function setupAutocomplete(element, questionTypeId) {
        var $hiddenField = $j(element).parent().children('.autoCompleteHidden');
        var $textField = $j(element).parent().children('.autoCompleteText');
        if ($hiddenField.length > 0 && $textField.length > 0) {
            $textField.autocomplete(
                autocompleteQuestionOptions[questionTypeId],
                {
                    "minChars": 0,
                    "width": 600,
                    "scroll": false,
                    "matchContains": true,
                    "autoFill": false,
                    "formatItem": function (row, i, max) {
                        return row.label;
                    },
                    "formatMatch": function (row, i, max) {
                        return row.label;
                    },
                    "formatResult": function (row) {
                        return row.label;
                    }
                }
            );

            $textField.result(function (event, data, formatted) {
                $hiddenField.val(data.code);
            });
        }
    }

    function validate(value, minValue, maxValue, allowDecimals, errorSpanId) {
        $j("#" + errorSpanId).html('').hide();
        $j("#submitButton").removeAttr("disabled");
        if (value != null && value != '') {
            if (minValue != null && value < minValue) {
                $j("#" + errorSpanId).html('<spring:message code="facilitydata.minValueExceeded"/>').show();
            }
            if (maxValue != null && value > maxValue) {
                $j("#" + errorSpanId).html('<spring:message code="facilitydata.maxValueExceeded"/>').show();
            }
            if (!allowDecimals && value != parseInt(value)) {
                $j("#" + errorSpanId).html('<spring:message code="facilitydata.decimalNotAllowed"/>').show();
            }
        }
        $j(".facilityDataAnswerError").each(function (i, val) {
            var errorText = $j(val).text();
            if (errorText && errorText != '') {
                $j("#submitButton").attr("disabled", "disabled");
            }
        });
    }

    $j(document).ready(function (event) {

        $j(".autocompleteText").mouseup(function (e) {
            e.preventDefault();
        });

    });


</script>

<form method="post" id="entryForm" enctype="multipart/form-data">
    <table width="100%" class="facilityDataTable">
        <tr>
            <td> Facility</td>
            <c:choose>
                <c:when test="${facility != null}">
                    <td>
                        <span class="readOnly">${facility.name}</span></td>
                </c:when>
                <c:otherwise>
                    <td><select name="facility" required="true">
                        <option value=""></option>
                        <c:forEach items="${locations}" var="location">

                            <option value="${location.id}">${location.name}</option>
                        </c:forEach>
                    </select>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
        <tr>
            <td><spring:message code="facilitydata.from-date"/></td>

            <c:choose>
                <c:when test="${fromDate != null}">

                    <td><span class="readOnly">${fromDate}</span></td>
                </c:when>
                <c:otherwise>
                    <td>
                        <input required="true" id="fromDate" onclick="showCalendar(this);"
                               name="fromDate"/></td>
                </c:otherwise>
            </c:choose>

        </tr>
        <tr>
            <td><spring:message code="facilitydata.to-date"/></td>
            <c:choose>

                <c:when test="${toDate!= null}">
                    <td>
                        <span class="readOnly">${toDate}</span></td>
                </c:when>
                <c:otherwise>
                    <td>
                        <input required="true" id="toDate" onclick="showCalendar(this);" name="toDate"/></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </table>

    <c:forEach items="${schema.sections}" var="section">
        <c:if test="${!empty section.questions}">
            <fieldset>
                <legend>${section.name}</legend>
                <table width="100%" class="facilityDataTable">
                    <tr>
                        <th colspan="2"></th>
                        <th><spring:message code="facilitydata.value"/></th>
                        <th><spring:message code="facilitydata.optional-comments"/></th>
                    <tr>
                            <c:set var="oddEven" value="odd"/>
                        <c:forEach items="${section.questions}" var="q">
                            <c:set var="oddEven" value="${oddEven == 'odd' ? 'even' : 'odd'}"/>
                    <tr align="left">
                        <td class="${oddEven}"
                            style="white-space:nowrap; vertical-align:middle;">${q.questionNumber}</td>
                        <td class="${oddEven}" style="width:400px; vertical-align:middle;">${q.name}</td>
                        <td class="${oddEven}" style="width:200px; white-space:nowrap;">
                            <c:choose>
                                <c:when test="${q.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType'}">
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <c:choose>
                                                <c:when test="${q.question.questionType.fieldStyle == 'AUTOCOMPLETE'}">
                                                        <span class="autoCompleteSection">
                                                            <input id="valueCoded_${q.id}" class="autoCompleteHidden"
                                                                   name="valueCoded.${q.id}" type="hidden"
                                                                   value="${report.values[q].valueCoded.id}"/>
                                                            <input id="valueCodedText_${q.id}" size="40" type="text"
                                                                   name="valueCodedText.${q.id}"
                                                                   class="autoCompleteText"
                                                                   onfocus="setupAutocomplete(this, '${q.question.questionType.id}');"
                                                                   value="${report.values[q].valueCoded.name}"/>
                                                        </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <select name="valueCoded.${q.id}">
                                                        <option value=""></option>
                                                        <c:forEach items="${q.question.questionType.options}"
                                                                   var="option">
                                                            <c:set var="codedSelected"
                                                                   value="${option == report.values[q].valueCoded}"/>
                                                            <c:if test="${codedSelected || !option.retired}">
                                                                <option value="${option.id}" ${codedSelected ? "selected" : ""}>${option.name}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${report.values[q].valueCoded != null}">
                                                    <span class="readOnly">${report.values[q].valueCoded.name}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="noAnswer"><spring:message
                                                            code="facilitydata.no-answer"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>

                                <c:when test="${q.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.DocumentTypeFacilityDataQuestionType'}">
                                    <c:set var="qt" value="${q.question.questionType}"/>
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <input type="file" name="documentTypeFile" id="documentTypeFile"
                                                   accept=".xml,.json"/>
                                        </c:when>

                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${report.values[q].documentValue!= null}">
                                                    <span class="readOnly">${report.values[q].documentValue}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="noAnswer"><spring:message
                                                            code="facilitydata.no-answer"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>


                                    </c:choose>

                                </c:when>

                                <c:when test="${q.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.BlobFacilityDataQuestionType'}">
                                    <c:set var="qt" value="${q.question.questionType}"/>
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <input type="file" name="blobFile" id="blobFile" accept="*/*"/>
                                        </c:when>

                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${report.values[q].valueBlob != null}">
                                                    <span class="readOnly">Saved as Binary Value in System</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="noAnswer"><spring:message
                                                            code="facilitydata.no-answer"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>


                                    </c:choose>

                                </c:when>

                                <c:when test="${q.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType'}">
                                    <c:set var="qt" value="${q.question.questionType}"/>
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <input type="text" size="10" id="valueNumeric.${q.id}"
                                                   name="valueNumeric.${q.id}"
                                                   value="<c:out value="${report.values[q].valueNumeric}"/>"
                                                   onblur="validate(this.value, ${qt.minValue == null ? 'null' : qt.minValue}, ${qt.maxValue == null ? 'null' : qt.maxValue}, ${qt.allowDecimals}, 'valueError${q.id}');"/>
                                            <span id="valueError${q.id}" class="facilityDataAnswerError"
                                                  style="display:none;"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${report.values[q].valueNumeric != null}">
                                                    <span class="readOnly">${report.values[q].valueNumeric}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="noAnswer"><spring:message
                                                            code="facilitydata.no-answer"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>

                                <c:when test="${q.question.questionType['class'].name == 'org.openmrs.module.facilitydata.model.FreeTextFacilityDataQuestionType'}">
                                    <c:set var="qt" value="${q.question.questionType}"/>
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <input type="text" size="50" id="valueText.${q.id}" name="valueText.${q.id}"
                                                   value="<c:out value="${report.values[q].valueText}"/>"/>
                                            <span id="valueError${q.id}" class="facilityDataAnswerError"
                                                  style="display:none;"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${report.values[q].valueText != null}">
                                                    <span class="readOnly">${report.values[q].valueText}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="noAnswer"><spring:message
                                                            code="facilitydata.no-answer"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>

                                <c:otherwise>
                                    <spring:message
                                            code="facilitydata.invalid-question-type"/>: ${q.question.questionType['class'].name}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="${oddEven}" style="white-space:nowrap;">
                            <c:choose>
                                <c:when test="${!viewOnly}">
                                    <input type="text" class="wide" name="comments.${q.id}"
                                           value="<c:out value="${report.values[q].comments}" default=""/>"/>
                                </c:when>
                                <c:otherwise>
                                    <span class="readOnly">${report.values[q].comments}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </c:if>
        <br/>
    </c:forEach>
    <c:if test="${!viewOnly}">
        <input id="submitButton" type="submit" value="<spring:message code="general.save"/>"/>
        <a href="dashboard.list"><input type="button"
                                                                        value="<spring:message code="general.cancel"/>"></a>
    </c:if>
    <openmrs:hasPrivilege privilege="Enter Facility Data Reports,Manage Facility Data Reports">
        <c:if test="${viewOnly}">
            <a href="randomformEntry.form?schema=${schema.schemaId}&facility=${facility.id}&fromDate=${facilitydata:formatDate(fromDate, 'yyyy/MM/dd', '')}">
                <input type="button" value="<spring:message code="general.edit"/>">
            </a>
        </c:if>
    </openmrs:hasPrivilege>
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>