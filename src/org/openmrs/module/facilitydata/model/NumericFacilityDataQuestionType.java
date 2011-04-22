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
 * The Numeric question type, which contains additional fields including min/max values
 * Example Numeric Question: "How many children were seen today?"
 */
public class NumericFacilityDataQuestionType extends FacilityDataQuestionType {

	//***** PROPERTIES *****
	
    private Double minValue;
    private Double maxValue;
    private boolean allowDecimals;

    //***** CONSTRUCTORS *****
    
    public NumericFacilityDataQuestionType() {super();}
    
    //***** PROPERTY ACCESS *****

	/**
	 * @return the minValue
	 */
	public Double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public Double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the allowDecimals
	 */
	public boolean isAllowDecimals() {
		return allowDecimals;
	}

	/**
	 * @param allowDecimals the allowDecimals to set
	 */
	public void setAllowDecimals(boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}
}