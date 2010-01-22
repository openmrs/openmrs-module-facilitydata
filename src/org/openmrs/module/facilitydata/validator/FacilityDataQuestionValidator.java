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
package org.openmrs.module.facilitydata.validator;

import org.openmrs.module.facilitydata.command.FacilityDataQuestionCommand;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FacilityDataQuestionValidator implements Validator {
    public boolean supports(Class aClass) {
        return FacilityDataQuestionCommand.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        FacilityDataQuestionCommand command = (FacilityDataQuestionCommand) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.null");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "error.null");
        if (command.getMethod() == null) errors.rejectValue("method", "error.null");
        if (command.getRetired() != null && command.getRetired()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retireReason", "general.retiredReason.empty");
        }
    }
}
