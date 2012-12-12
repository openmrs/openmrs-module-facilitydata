package org.openmrs.module.facilitydata.validator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates a Facility Data Question
 */
@Handler(supports={FacilityDataFormQuestion.class})
public class FacilityDataFormQuestionValidator extends BaseFacilityMetadataValidator {

	/**
     * @see Validator#supports(Class)
     */
    @SuppressWarnings("unchecked")
    public boolean supports(Class c) {
	return FacilityDataFormQuestion.class.isAssignableFrom(c);
    }

    /**
	 * @see Validator#validate(Object, Errors)
	 */
    public void validate(Object o, Errors errors) {
    	super.validate(o, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionNumber", "error.null");
    }
}
