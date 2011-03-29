package org.openmrs.module.facilitydata.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;

/**
 * Validates a Facility Data Question
 */
public class FacilityDataFormQuestionValidator extends BaseFacilityMetadataValidator {
	
	/**
	 * @see Validator#supports(Class)
	 */
	public boolean supports(Class aClass) {
        return FacilityDataFormQuestion.class.isAssignableFrom(aClass);
    }

	/**
	 * @see Validator#validate(Object, Errors)
	 */
    public void validate(Object o, Errors errors) {
    	super.validate(o, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionNumber", "error.null");
    }
}
