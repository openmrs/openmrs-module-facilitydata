/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

$(document).ready(function() {
    checkType();
    checkRetired();
});
/**
 * This is used to determine whether or not to display the properties for NumericQuestions.
 */
function checkType() {
    var type = $("#type").val();
    if (type !== null) {
        if (type == "NumericQuestion") {
            $("#numericQuestion").fadeIn();
        } else {
            $("#numericQuestion").fadeOut();
        }
    }
}

function checkRetired() {
    var retired = document.getElementById("retired");
    if (retired !== null) {
        if (retired.checked) {
            $("#reason").fadeIn();
        } else {
            $("#reason").fadeOut();
        }
    }
} 