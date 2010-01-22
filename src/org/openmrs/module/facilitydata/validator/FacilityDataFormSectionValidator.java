package org.openmrs.module.facilitydata.validator;

import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FacilityDataFormSectionValidator implements Validator {
    public boolean supports(Class aClass) {
        return FacilityDataFormSection.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        FacilityDataFormSection section = (FacilityDataFormSection) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.null");
        if (section.getRetired() != null && section.getRetired()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retireReason", "general.retiredReason.empty");
        }
    }
}
