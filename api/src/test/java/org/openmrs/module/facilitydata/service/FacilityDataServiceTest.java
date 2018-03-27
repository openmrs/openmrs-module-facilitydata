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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.util.BaseFacilityDataContextSensitiveTest;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
	public void testThatSchemasAreLoaded() throws Exception {
		List<FacilityDataFormSchema> schemas = getService().getAllFacilityDataFormSchemas();
		//List<FacilityDataQuestionType> questionTypesList = getService().getAllQuestionTypes();
		
		FacilityDataFormSchema schema = schemas.get(0);

		Assert.assertEquals(1, schema.getSchemaId());
		
		Assert.assertEquals("Daily Report", schema.getName());

		Assert.assertEquals(2, schema.getSections().size());

		FacilityDataFormSection s1 = schema.getSections().get(0);
		Assert.assertEquals("Equipment Status", s1.getName());
	
		Assert.assertEquals(3, s1.getQuestions().size());

		Iterator<FacilityDataFormQuestion> iterator = s1.getQuestions().iterator();
/*		FacilityDataFormQuestion q = iterator.next();
		
		Assert.assertEquals("Hours without water", q.getName());
		Assert.assertEquals(24, ((NumericFacilityDataQuestionType)q.getQuestion().getQuestionType()).getMaxValue().intValue());
		q = iterator.next();
		System.out.println(q.getName());
		Assert.assertEquals("Internet Status", q.getName());
		System.out.println(((CodedFacilityDataQuestionType)q.getQuestion().getQuestionType()).getOptions().size());
		Assert.assertEquals(4, ((CodedFacilityDataQuestionType)q.getQuestion().getQuestionType()).getOptions().size());
		System.out.println(q.getName());
		q = iterator.next();
		Assert.assertEquals("Any power outages", q.getName());
		Assert.assertEquals(3, ((CodedFacilityDataQuestionType)q.getQuestion().getQuestionType()).getOptions().size());
		q = iterator.next();*/
			}
	
	/**
	 * General Test
	 */
	@Test
	public void testSavingAndLoadingValues() throws Exception {
		FacilityDataFormSchema schema = getService().getFacilityDataFormSchema(1);
		Location l = new Location(1);
		Date d1 = FacilityDataUtil.parseYmd("2010-08-01");
		FacilityDataReport report = getService().getReport(schema, d1, d1, l);
		Assert.assertEquals(1, report.getValues().size());
	}
}
