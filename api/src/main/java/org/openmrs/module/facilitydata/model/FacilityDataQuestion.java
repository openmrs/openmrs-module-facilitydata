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

import org.openmrs.module.facilitydata.model.enums.PeriodApplicability;

/**
 * The base class for all question types.
 */
public class FacilityDataQuestion extends BaseFacilityMetaData {

	//***** PROPERTIES *****
	
    private PeriodApplicability periodApplicability;
    private FacilityDataQuestionType questionType;

    //***** CONSTRUCTORS *****
    
    public FacilityDataQuestion() {}
    
    //***** PROPERTY ACCESS *****

    /**
	 * @return the periodApplicability
	 */
	public PeriodApplicability getPeriodApplicability() {
		return periodApplicability;
	}

	/**
	 * @param periodApplicability the periodApplicability to set
	 */
	public void setPeriodApplicability(PeriodApplicability periodApplicability) {
		this.periodApplicability = periodApplicability;
	}

	/**
	 * @return the questionType
	 */
	public FacilityDataQuestionType getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(FacilityDataQuestionType questionType) {
		this.questionType = questionType;
	}
}