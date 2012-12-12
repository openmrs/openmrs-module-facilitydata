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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;

import java.util.Date;

/**
 * This represents a particular value entered for a particular question on a form
 */
public class FacilityDataValue extends BaseOpenmrsData {
	
	//***** PROPERTIES *****
	
    private Integer id;
    private Location facility;
    private Date fromDate;
    private Date toDate;
    private FacilityDataFormQuestion question; // The question on the form that was answered
    private Double valueNumeric; // Populated if this is a Numeric question
    private FacilityDataCodedOption valueCoded; // Populated if this is a Coded question
    private String comments; // Optional comments associated with the value entered

    //***** CONSTRUCTORS *****
    
    public FacilityDataValue() {}

    //***** INSTANCE METHODS *****
    
    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FacilityDataValue)) return false;
        FacilityDataValue that = (FacilityDataValue) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
    
    //***** PROPERTY ACCESS *****

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the facility
	 */
	public Location getFacility() {
		return facility;
	}

	/**
	 * @param facility the facility to set
	 */
	public void setFacility(Location facility) {
		this.facility = facility;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the question
	 */
	public FacilityDataFormQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(FacilityDataFormQuestion question) {
		this.question = question;
	}

	/**
	 * @return the valueNumeric
	 */
	public Double getValueNumeric() {
		return valueNumeric;
	}

	/**
	 * @param valueNumeric the valueNumeric to set
	 */
	public void setValueNumeric(Double valueNumeric) {
		this.valueNumeric = valueNumeric;
	}

	/**
	 * @return the valueCoded
	 */
	public FacilityDataCodedOption getValueCoded() {
		return valueCoded;
	}

	/**
	 * @param valueCoded the valueCoded to set
	 */
	public void setValueCoded(FacilityDataCodedOption valueCoded) {
		this.valueCoded = valueCoded;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
