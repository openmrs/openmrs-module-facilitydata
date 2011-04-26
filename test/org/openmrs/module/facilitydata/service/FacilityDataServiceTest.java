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
package org.openmrs.module.facilitydata.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType;
import org.openmrs.module.facilitydata.util.BaseFacilityDataContextSensitiveTest;

/**
 * Test of the Facility Data Service
 */
public class FacilityDataServiceTest extends BaseFacilityDataContextSensitiveTest {

	public final Log log = LogFactory.getLog(this.getClass());
	
	@Before
	public void setup() throws Exception {
		executeDataSet("org/openmrs/module/facilitydata/include/FacilityDataTestDataset.xml");
	}
	
	/**
	 * @return the FacilityDataService
	 */
	public FacilityDataService getService() throws Exception {
		return Context.getService(FacilityDataService.class);
	}
	
	/**
	 * General Test
	 */
	@Test
	public void generalTest() throws Exception {
		for (FacilityDataFormSchema schema : getService().getAllFacilityDataFormSchemas()) {
			log.warn("In schema: " + schema);
			for (FacilityDataFormSection section : schema.getSections()) {
				log.warn("In section: " + section);
				for (FacilityDataFormQuestion formQuestion : section.getQuestions()) {
					log.warn("Found question: " + formQuestion);
					FacilityDataQuestionType type = formQuestion.getQuestion().getQuestionType();
					log.warn("This is of type: " + type);
					log.warn("is this assignable to coded? " + CodedFacilityDataQuestionType.class.isAssignableFrom(type.getClass()));
					log.warn("is this assignable to numeric? " + NumericFacilityDataQuestionType.class.isAssignableFrom(type.getClass()));
					if (CodedFacilityDataQuestionType.class.isAssignableFrom(type.getClass())) {
						CodedFacilityDataQuestionType cq = (CodedFacilityDataQuestionType) formQuestion.getQuestion().getQuestionType();
						log.warn("This is coded, with options: " + cq.getOptions());
					}
					else {
						NumericFacilityDataQuestionType cq = (NumericFacilityDataQuestionType) formQuestion.getQuestion().getQuestionType();
						log.warn("This is numeric, with constraints: " + cq.getMinValue() + " - " + cq.getMaxValue());
					}
				}
			}
		}
	}
}