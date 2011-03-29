package org.openmrs.module.facilitydata.validator;

import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates a Facility Data Question
 */
public class FacilityDataFormSchemaValidator extends BaseFacilityMetadataValidator {
	
	/**
	 * @see Validator#supports(Class)
	 */
	public boolean supports(Class aClass) {
        return FacilityDataFormSchema.class.isAssignableFrom(aClass);
    }

	/**
	 * @see Validator#validate(Object, Errors)
	 */
    public void validate(Object o, Errors errors) {
    	super.validate(o, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "error.null");
    }
}
