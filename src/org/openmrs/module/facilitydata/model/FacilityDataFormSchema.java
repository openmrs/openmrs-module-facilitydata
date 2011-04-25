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
package org.openmrs.module.facilitydata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.module.facilitydata.model.enums.Frequency;

/**
 * This represents a collection of questions that are asked together on a Form, in one or more sections
 */
public class FacilityDataFormSchema extends BaseFacilityMetaData {

	//***** PROPERTIES *****

    private Frequency frequency;
    private Date validFrom;
    private Date validTo;
    private List<FacilityDataFormSection> sections;

    //***** CONSTRUCTORS *****
    
    public FacilityDataFormSchema() {}
    
    //***** INSTANCE METHODS *****
    
    /**
     * @return the number of questions in the schema
     */
    public int getTotalNumberOfQuestions() {
        int cnt = 0;
        for (FacilityDataFormSection section : getSections()) {
        	cnt += section.getQuestions().size();
        }
        return cnt;
    }
    
    /**
     * @return the section with the passed id
     */
    public FacilityDataFormSection getSectionById(Integer sectionId) {
    	for (FacilityDataFormSection section : getSections()) {
    		if (section.getId().equals(sectionId)) {
    			return section;
    		}
    	}
    	return null;
    }

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
	 * @return the validFrom
	 */
	public Date getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return the validTo
	 */
	public Date getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo the validTo to set
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	/**
	 * @return the sections
	 */
	public List<FacilityDataFormSection> getSections() {
		if (sections == null) {
			sections = new ArrayList<FacilityDataFormSection>();
		}
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(List<FacilityDataFormSection> sections) {
		this.sections = sections;
	}
	
	/**
	 * @param section the section to add
	 */
	public void addSection(FacilityDataFormSection section) {
		getSections().add(section);
	}
}
