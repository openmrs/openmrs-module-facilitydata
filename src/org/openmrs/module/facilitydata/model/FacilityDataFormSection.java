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

import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a single report section with a specified number of questions
 */
public class FacilityDataFormSection extends BaseFacilityMetaData {
    
	//***** PROPERTIES *****
	
    private FacilityDataFormSchema schema;
    private Set<FacilityDataFormQuestion> questions;

    //***** CONSTRUCTORS *****
    
    public FacilityDataFormSection() {}
    
    //***** INSTANCE METHODS *****
    
    /**
     * @return the question with the passed id
     */
    public FacilityDataFormQuestion getQuestionById(Integer questionId) {
    	for (FacilityDataFormQuestion question : getQuestions()) {
    		if (question.getId().equals(questionId)) {
    			return question;
    		}
    	}
    	return null;
    }

    //***** PROPERTY ACCESS *****
	/**
	 * @return the schema
	 */
	public FacilityDataFormSchema getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(FacilityDataFormSchema schema) {
		this.schema = schema;
	}

	/**
	 * @return the questions
	 */
	public Set<FacilityDataFormQuestion> getQuestions() {
		if (questions == null) {
			questions = new TreeSet<FacilityDataFormQuestion>();
		}
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Set<FacilityDataFormQuestion> questions) {
		this.questions = questions;
	}
}
