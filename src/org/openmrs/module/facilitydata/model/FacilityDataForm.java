package org.openmrs.module.facilitydata.model;

import java.util.HashSet;
import java.util.Set;

import org.openmrs.module.facilitydata.model.enums.Frequency;

/**
 * Represents a particular form, which might be represented by different schemas over time
 */
public class FacilityDataForm extends BaseFacilityMetaData {
	
	//***** PROPERTIES *****
	
	private Frequency frequency;
    private Set<FacilityDataFormSchema> schemas;
    
    //***** CONSTRUCTORS *****
    
    public FacilityDataForm() {}
    
    //***** INSTANCE METHODS *****


    //***** PROPERTY ACCESS *****
    
	/**
	 * @return the frequency
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the schemas
	 */
	public Set<FacilityDataFormSchema> getSchemas() {
		if (schemas == null) {
			 schemas = new HashSet<FacilityDataFormSchema>();
		}
		return schemas;
	}

	/**
	 * @param schemas the schemas to set
	 */
	public void setSchemas(Set<FacilityDataFormSchema> schemas) {
		this.schemas = schemas;
	}
}
