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

/**
 * Base class for all question types that are restricted to a particular set of specific values
 * @see StockQuestion
 * @see BooleanCodedQuestion
 */
public class CodedFacilityDataQuestion extends FacilityDataQuestion {
	
	//***** PROPERTIES *****
	
	private List<CodedFacilityDataQuestionOption> options;
	
	//***** CONSTRUCTORS *****
	
	public CodedFacilityDataQuestion() {super();}

	/**
	 * @return the options
	 */
	public List<CodedFacilityDataQuestionOption> getOptions() {
		if (options == null) {
			options = new ArrayList<CodedFacilityDataQuestionOption>();
		}
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<CodedFacilityDataQuestionOption> options) {
		this.options = options;
	}
}
