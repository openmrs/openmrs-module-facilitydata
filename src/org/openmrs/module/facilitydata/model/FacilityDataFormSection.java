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

import com.google.common.collect.Sets;
import org.openmrs.BaseOpenmrsMetadata;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Represents a single report section with a specified number of questions
 */
public class FacilityDataFormSection extends BaseOpenmrsMetadata {
	
	/**
	 * Primary Key Id
	 */
    private Integer id;
    
    /**
     * schemas to which this section belongs
     */
    private Set<FacilityDataFormSchema> formSchemas = Sets.newHashSet();

    /**
     * Questions defined for this section.
     */
    private SortedSet<FacilityDataFormQuestion> questions = Sets.newTreeSet();

    public FacilityDataFormSection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<FacilityDataFormSchema> getFormSchemas() {
        return formSchemas;
    }

    public void setFormSchemas(Set<FacilityDataFormSchema> formSchemas) {
        this.formSchemas = formSchemas;
    }

    public SortedSet<FacilityDataFormQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(SortedSet<FacilityDataFormQuestion> questions) {
        this.questions = questions;        
    }
    
    public void addSectionToSchema(FacilityDataFormSchema schema) {
        if(formSchemas == null) {
            formSchemas = Sets.newHashSet();
        }
        formSchemas.add(schema);
    }
    public void addQuestion(FacilityDataFormQuestion question) {
    	if (questions == null) {
    		questions = Sets.newTreeSet();
    	}
        questions.add(question);
        question.setSection(this);
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
        if (!(o instanceof FacilityDataFormSection)) return false;

        FacilityDataFormSection section = (FacilityDataFormSection) o;

        if (id != null ? !id.equals(section.id) : section.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
