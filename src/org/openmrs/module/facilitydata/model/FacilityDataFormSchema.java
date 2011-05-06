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

/**
 * This represents a collection of questions that are asked together on a Form, in one or more sections
 */
public class FacilityDataFormSchema extends BaseFacilityMetaData {

	//***** PROPERTIES *****

	private FacilityDataForm form;
    private Date validFrom;
    private Date validTo;
    private List<FacilityDataFormSection> sections;

    //***** CONSTRUCTORS *****
    
    public FacilityDataFormSchema() {}
    
    //***** INSTANCE METHODS *****
    
    /**
     * @return true if this schema is active on the current date
     */
    public boolean isActive() {
    	return isActive(null);
    }
    
    /**
     * @return true if this schema is active on the passed date
     */
    public boolean isActive(Date d) {
    	d = (d == null ? new Date() : d);
		if (getValidFrom() == null || getValidFrom().compareTo(d) <= 0) {
			if (getValidTo() == null || getValidTo().compareTo(d) >= 0) {
				return true;
			}
		}
		return false;
    }
    
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
	 * @return the form
	 */
	public FacilityDataForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(FacilityDataForm form) {
		this.form = form;
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
