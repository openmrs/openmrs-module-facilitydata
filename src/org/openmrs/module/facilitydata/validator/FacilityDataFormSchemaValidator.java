package org.openmrs.module.facilitydata.validator;

import java.text.SimpleDateFormat;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
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
    public void validate(Object obj, Errors errors) {
    	FacilityDataFormSchema schema = (FacilityDataFormSchema) obj; 
    	super.validate(obj, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "error.null");
        /* Valid from, to validation */
        if ( schema.getValidFrom() != null ) {
        	if ( schema.getValidTo() != null ) {
        		if (schema.getValidFrom().after(schema.getValidTo())) { /* check if the field valid from is prior than valid to*/
        			errors.rejectValue("validTo", "facilitydata.from-date-after-to-date");
        		}
        	}
        }
    }
    
    
}






































