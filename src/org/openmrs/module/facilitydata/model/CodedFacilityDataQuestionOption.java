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

/**
 * Represents one of the possible answers to a Coded Question.
 */
public class CodedFacilityDataQuestionOption extends BaseFacilityMetaData {
	
	//***** PROPERTIES *****
	
	private CodedFacilityDataQuestion question;
	private String code;

	//***** CONSTRUCTORS *****
	
	public CodedFacilityDataQuestionOption() {super();}
	
	//***** PROPERTY ACCESS*****

	/**
	 * @return the question
	 */
	public CodedFacilityDataQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(CodedFacilityDataQuestion question) {
		this.question = question;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
