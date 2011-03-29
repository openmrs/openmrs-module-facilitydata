package org.openmrs.module.facilitydata.validator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates a Facility Data Question
 */
@Handler(supports={FacilityDataFormSchema.class})
public class FacilityDataFormSchemaValidator extends BaseFacilityMetadataValidator {

	/**
	 * @see Validator#validate(Object, Errors)
	 */
    public void validate(Object o, Errors errors) {
    	super.validate(o, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "error.null");
    }
}
