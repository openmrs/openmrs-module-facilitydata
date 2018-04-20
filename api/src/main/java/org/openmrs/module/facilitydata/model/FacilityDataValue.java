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

import javax.persistence.*;
import java.util.Date;

/**
 * This represents a particular value entered for a particular question on a form
 */
@Entity
@Table(name = "facilitydata_value")
public class FacilityDataValue extends BaseOpenmrsData {
	
	//***** PROPERTIES *****
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private Integer id;
	
	@ManyToOne
    @JoinColumn(nullable = false,name = "facility")
    private Location facility;
    
    @Column(name="from_date",nullable = false)
    private Date fromDate;
    
    @Column(name="to_date",nullable = false)
    private Date toDate;
    
    @ManyToOne
    @JoinColumn(name = "question",nullable = false)
    private FacilityDataFormQuestion question; // The question on the form that was answered
    
    @Column(name="value_numeric")
    private Double valueNumeric; // Populated if this is a Numeric question

    @ManyToOne
    @JoinColumn(name = "value_coded")
    private FacilityDataCodedOption valueCoded; // Populated if this is a Coded question
    
    @Column(name="value_text")
    private String valueText;
    
    @Column(name="value_blob")
    private byte[] valueBlob;
    
    @Column(name="comments")
    private String comments; // Optional comments associated with the value entered

	@Column(name="document_value", length = Integer.MAX_VALUE)
	private String documentValue;

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

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public byte[] getValueBlob() {
		return valueBlob;
	}

	public void setValueBlob(byte[] valueBlob) {
		this.valueBlob = valueBlob;
	}

	public String getDocumentValue() {
		return documentValue;
	}

	public void setDocumentValue(String documentValue) {
		this.documentValue = documentValue;
	}
}
