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
import java.util.List;

import org.openmrs.module.facilitydata.util.DelimitedKeyComparator;

/**
 * This represents a question that is a part of a particular report.  It is an instance of a question
 */
public class FacilityDataFormQuestion extends BaseFacilityMetaData implements Comparable<FacilityDataFormQuestion> {
    
	//***** PROPERTIES *****
	
    private FacilityDataFormSection section;
    private String questionNumber;
    private FacilityDataQuestion question;

    //**** CONSTRUCTORS *****
    
    public FacilityDataFormQuestion() {}
    
    //***** INSTANCE METHODS *****

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(FacilityDataFormQuestion that) {
    	DelimitedKeyComparator c = new DelimitedKeyComparator();
    	return c.compare(this.getQuestionNumber(), that.getQuestionNumber());
	}

    //***** PROPERTY ACCESS *****
    
	/**
	 * @return the section
	 */
	public FacilityDataFormSection getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(FacilityDataFormSection section) {
		this.section = section;
	}

	/**
	 * @return the questionNumber
	 */
	public String getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * @param questionNumber the questionNumber to set
	 */
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * @return the question
	 */
	public FacilityDataQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(FacilityDataQuestion question) {
		this.question = question;
	}
	
	/**
	 * @return a list of dependencies for the Metadata Sharing Module
	 */
	public List<Object> getPriorityDependenciesForMetadataSharing() {
		List<Object> list = new ArrayList<Object>();
		list.add(getQuestion());
		list.add(getSection());
		return list;
	}
}