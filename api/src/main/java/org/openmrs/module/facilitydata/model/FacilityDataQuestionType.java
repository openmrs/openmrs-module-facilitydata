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

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
/**
 * The base class for all question types.
 */
@Entity  
@Table(name = "facilitydata_question_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="data_type",discriminatorType=DiscriminatorType.STRING,length=255)
@DiscriminatorOptions(insert=true,force=true)  // prevent WrongClassException
public abstract class FacilityDataQuestionType extends BaseFacilityMetaData  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_type_id", unique = true, nullable = false, updatable = false )
	private Integer questionTypeId; 
	
	@Column(name="field_style")
	private String fieldStyle;

    public FacilityDataQuestionType() {}

	/**
	 * @return the field style
	 */
	public String getFieldStyle() {
		return fieldStyle;
	}

	/**
	 * @param fieldStyle the field style to set
	 */
	public void setFieldStyle(String fieldStyle) {
		this.fieldStyle = fieldStyle;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
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
        if (this.getClass().isAssignableFrom(o.getClass())) {
        	FacilityDataQuestionType that = (FacilityDataQuestionType)o;
        	return this.getQuestionTypeId() != null && this.getQuestionTypeId().equals(that.getQuestionTypeId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = questionTypeId != null ? questionTypeId.hashCode() : super.hashCode();
        return result;
    }

	@Override
	public Integer getId() {
		
		return questionTypeId;
	}

	@Override
	public void setId(Integer id) {
		this.questionTypeId=id;
	}
    
    
    
    
}