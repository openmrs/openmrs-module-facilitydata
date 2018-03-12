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
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Represents a single report section with a specified number of questions
 */
@Entity
@Table(name="facilitydata_form_section")
public class FacilityDataFormSection extends BaseFacilityMetaData {
    
	//***** PROPERTIES ***** form_section_id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="form_section_id")
	private int formSectionId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schema_id", insertable = false, updatable = false)
    private FacilityDataFormSchema schema;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="section",targetEntity=FacilityDataFormQuestion.class,orphanRemoval=false)
	private Set<FacilityDataFormQuestion> questions;

	@Column(name="section_number")
	private int sectionNumber;
    //***** CONSTRUCTORS *****
    
    public FacilityDataFormSection() {}
    
    //***** INSTANCE METHODS *****
    
    /**
     * @return the question with the passed id
     */
    public FacilityDataFormQuestion getQuestionById(Integer questionId) {
    	for (FacilityDataFormQuestion question : getQuestions()) {
    		if (question.getFormQuestionId()==questionId) {
    			return question;
    		}
    	}
    	return null;
    }

	/**
	 * @return a list of dependencies for the Metadata Sharing Module
	 */
	public List<Object> getPriorityDependenciesForMetadataSharing() {
		List<Object> list = new ArrayList<Object>();
		list.add(getSchema());
		return list;
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

	public int getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	@Override
	public Integer getId() {
		
		return formSectionId;
	}

	@Override
	public void setId(Integer id) {
		this.formSectionId=id;
	}
	
	
	
}
