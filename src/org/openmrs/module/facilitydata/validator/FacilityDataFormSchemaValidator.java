package org.openmrs.module.facilitydata.validator;

import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FacilityDataFormSchemaValidator implements Validator {
    public boolean supports(Class aClass) {
        return FacilityDataFormSchema.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "errors.null");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "errors.null");

    }
}
