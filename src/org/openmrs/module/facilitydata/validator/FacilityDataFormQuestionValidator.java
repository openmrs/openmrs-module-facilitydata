package org.openmrs.module.facilitydata.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;

public class FacilityDataFormQuestionValidator implements Validator {
    public boolean supports(Class aClass) {
        return FacilityDataFormQuestion.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.null");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionNumber", "error.null");
        FacilityDataFormQuestion q = (FacilityDataFormQuestion) o;
        if (q.getRetired() != null && q.getRetired()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retireReason", "general.retiredReason.empty");
        }
    }
}
