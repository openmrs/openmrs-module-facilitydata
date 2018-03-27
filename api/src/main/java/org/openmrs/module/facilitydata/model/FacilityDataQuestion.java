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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all question types.
 */
@Entity
@Table(name="facilitydata_question")
public class FacilityDataQuestion extends BaseFacilityMetaData {

	//***** PROPERTIES *****
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="question_id")
	private int questionId;
	
	@Column(name="period_applicability")
	@Enumerated(EnumType.STRING)
    private PeriodApplicability periodApplicability;
    
    @ManyToOne(fetch = FetchType.EAGER,cascade= {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "question_type")
    private FacilityDataQuestionType questionType;

    //***** CONSTRUCTORS *****
    
    public FacilityDataQuestion() {}

	//***** INSTANCE METHJODS *****

	/**
	 * @return a list of dependencies for the Metadata Sharing Module
	 */
	public List<Object> getPriorityDependenciesForMetadataSharing() {
		List<Object> list = new ArrayList<Object>();
		list.add(questionType);
		return list;
	}
    
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

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	@Override
	public Integer getId() {
		
		return questionId;
	}

	@Override
	public void setId(Integer id) {
		this.questionId=id;
	}
	
	
}