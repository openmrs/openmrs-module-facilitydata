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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one of the possible answers to a Coded Question.
 */

@Entity
@Table(name="facilitydata_coded_option")
public class FacilityDataCodedOption extends BaseFacilityMetaData {
	
	//***** PROPERTIES *****
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "question_type_id")
	private CodedFacilityDataQuestionType questionType;
	
	@Column(name="code")
	private String code;

	@Column(name="option_order")
	private int optionOrder;
	//***** CONSTRUCTORS *****
	
	public FacilityDataCodedOption() {super();}

	//***** INSTANCE METHODS *****

	/**
	 * @return a list of dependencies for the Metadata Sharing Module
	 */
	public List<Object> getPriorityDependenciesForMetadataSharing() {
		List<Object> list = new ArrayList<Object>();
		list.add(questionType);
		return list;
	}
	
	//***** PROPERTY ACCESS*****

	/**
	 * @return the questionType
	 */
	public CodedFacilityDataQuestionType getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(CodedFacilityDataQuestionType questionType) {
		this.questionType = questionType;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOptionOrder() {
		return optionOrder;
	}

	public void setOptionOrder(int optionOrder) {
		this.optionOrder = optionOrder;
	}
}
