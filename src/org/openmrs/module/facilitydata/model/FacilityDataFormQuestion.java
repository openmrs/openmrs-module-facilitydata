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

import org.openmrs.BaseOpenmrsMetadata;

/**
 * This represents a question as it is displayed on the report form itself.
 */
public class FacilityDataFormQuestion extends BaseOpenmrsMetadata implements Comparable<FacilityDataFormQuestion> {
    private Integer id;

    /**
     * Parent section
     */
    private FacilityDataFormSection section;
    /**
     * Question number (e.g. 1a)
     */
    private String questionNumber;

    /**
     * A reference to the question itself.
     */
    private FacilityDataQuestion question;

    public FacilityDataFormQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FacilityDataFormSection getSection() {
        return section;
    }

    public void setSection(FacilityDataFormSection section) {
        this.section = section;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public FacilityDataQuestion getQuestion() {
        return question;
    }

    public void setQuestion(FacilityDataQuestion question) {
        this.question = question;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        if (getName() == null) {
            return super.toString();
        }
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacilityDataFormQuestion)) return false;

        FacilityDataFormQuestion question1 = (FacilityDataFormQuestion) o;

        if (id != null ? !id.equals(question1.id) : question1.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(FacilityDataFormQuestion question) {
        if (this.getQuestionNumber() == null || question.getQuestionNumber() == null) {
            return 0;
        }
        return this.getQuestionNumber().compareTo(question.getQuestionNumber());
	}
}