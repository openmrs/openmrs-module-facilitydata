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




import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Base class for all question types that are restricted to a particular set of specific values
 */
@Entity
@Table(name = "facilitydata_question_type")
@DiscriminatorValue("org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType")
public class CodedFacilityDataQuestionType extends FacilityDataQuestionType {
	
	//***** PROPERTIES *****

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "questionType", orphanRemoval = true)
	//@JoinColumn(name = "question_type_id")
	private List<FacilityDataCodedOption> options=new ArrayList<FacilityDataCodedOption>();
	
	//***** CONSTRUCTORS *****
	
	public CodedFacilityDataQuestionType() {super();}
	
	//***** PROPERTY ACCESS *****

	/**
	 * @return the options
	 */
	public List<FacilityDataCodedOption> getOptions() {

		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<FacilityDataCodedOption> options) {
		this.options = options;
	}
	
	/**
	 * @returns the option with the passed id form this question
	 */
	public FacilityDataCodedOption getOptionById(Integer id) {
		for (FacilityDataCodedOption option : getOptions()) {
			if (option.getId().equals(id)) {
				return option;
			}
		}
		return null;
	}
	
	/**
	 * @returns the non-retired options
	 */
	public List<FacilityDataCodedOption> getActiveOptions() {
		List<FacilityDataCodedOption> ret = new ArrayList<FacilityDataCodedOption>();
		for (FacilityDataCodedOption option : getOptions()) {
			if (option.getRetired() != Boolean.TRUE) {
				ret.add(option);
			}
		}
		return ret;
	}
	
	/**
	 * @returns the retired options
	 */
	public List<FacilityDataCodedOption> getRetiredOptions() {
		List<FacilityDataCodedOption> ret = new ArrayList<FacilityDataCodedOption>();
		for (FacilityDataCodedOption option : getOptions()) {
			if (option.getRetired() == Boolean.TRUE) {
				ret.add(option);
			}
		}
		return ret;
	}

	public void addFacilityDataCodedOption(FacilityDataCodedOption child) {
		//child.setQuestionType(this);
		options.add(child);
	}
}
