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
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.NumericFacilityDataQuestion;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test of the Facility Data Service
 */
public class FacilityDataServiceTest extends BaseModuleContextSensitiveTest {

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
					if (formQuestion.getQuestion() instanceof CodedFacilityDataQuestion) {
						CodedFacilityDataQuestion cq = (CodedFacilityDataQuestion) formQuestion.getQuestion();
						log.warn("This is coded, with options: " + cq.getOptionSet().getOptions());
					}
					else {
						NumericFacilityDataQuestion cq = (NumericFacilityDataQuestion) formQuestion.getQuestion();
						log.warn("This is numeric, with constraints: " + cq.getMinValue() + " - " + cq.getMaxValue());
					}
				}
			}
		}
	}
}