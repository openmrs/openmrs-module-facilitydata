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
 * This is where the entered data is stored.
 */
public class FacilityDataValue extends BaseOpenmrsData {
    private Integer id;

    /**
     * the {@link Location} of the clinic
     */
    private Location facility;
    /**
     * Start date of the period for which this form is entered.
     */
    private Date fromDate;

    /**
     * End date of the period for which this form is entered.
     */
    private Date toDate;

    /**
     * Question to which this is storing the answer for.
     */
    private FacilityDataQuestion question;

    /**
     * Numeric answer (if non-numeric this should always be null)
     */
    private Double valueNumeric;

    /**
     * if coded, this is where the coded option will be stored.
     */
    private String valueText;

    /**
     * The comments (e.g. Why?)
     */
    private String comments;

    public FacilityDataValue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getFacility() {
        return facility;
    }

    public void setFacility(Location facility) {
        this.facility = facility;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public FacilityDataQuestion getQuestion() {
        return question;
    }

    public void setQuestion(FacilityDataQuestion question) {
        this.question = question;
    }

    public Double getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacilityDataValue value = (FacilityDataValue) o;

        if (comments != null ? !comments.equals(value.comments) : value.comments != null) return false;
        if (!facility.equals(value.facility)) return false;
        if (!fromDate.equals(value.fromDate)) return false;
        if (!id.equals(value.id)) return false;
        if (!question.equals(value.question)) return false;
        if (!toDate.equals(value.toDate)) return false;
        if (valueNumeric != null ? !valueNumeric.equals(value.valueNumeric) : value.valueNumeric != null) return false;
        if (valueText != null ? !valueText.equals(value.valueText) : value.valueText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + (facility != null ? facility.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0); 
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (valueNumeric != null ? valueNumeric.hashCode() : 0);
        result = 31 * result + (valueText != null ? valueText.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
