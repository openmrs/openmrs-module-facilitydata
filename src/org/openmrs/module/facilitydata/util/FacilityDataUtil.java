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
package org.openmrs.module.facilitydata.util;

import org.apache.log4j.Logger;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.BooleanCodedQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.NumericQuestion;
import org.openmrs.module.facilitydata.model.StockQuestion;
import org.openmrs.module.facilitydata.model.enums.AggregationMethod;
import org.openmrs.module.facilitydata.model.enums.FacilityDataFrequency;
import org.openmrs.module.facilitydata.service.FacilityDataService;

import java.util.List;

public class FacilityDataUtil {
    private static final Logger log = Logger.getLogger(FacilityDataUtil.class);

    private FacilityDataUtil() {
    }

    public static FacilityDataFormSection createNewFormSection(String displayName) {
        FacilityDataFormSection section = getNewSection();
        section.setName(displayName);
        return section;
    }

    public static FacilityDataFormQuestion createNewFormQuestion(String displayName, String questionNumber, FacilityDataQuestion question) {
        FacilityDataFormQuestion formQuestion = getNewFormQuestion(displayName);
        formQuestion.setQuestionNumber(questionNumber);
        formQuestion.setQuestion(question);
        return formQuestion;
    }

    public static FacilityDataService getService() {
        return Context.getService(FacilityDataService.class);
    }
  
    public static boolean isStockQuestion(FacilityDataQuestion question) {
        return question instanceof StockQuestion;
    }

    public static boolean isBooleanCodedQuestion(FacilityDataQuestion question) {
        return question instanceof BooleanCodedQuestion;
    }

    public static boolean isNumericQuestion(FacilityDataQuestion question) {
        return question instanceof NumericQuestion;
    }

    // the methods below may go at a later date; primarily they exist to make my life easier when mocking up the initial form.
    public static FacilityDataFormSection getNewSection() {
        return new FacilityDataFormSection();
    }

    public static FacilityDataFormQuestion getNewFormQuestion(String name) {
        FacilityDataFormQuestion q = new FacilityDataFormQuestion();
        q.setName(name);
        return q;
    }

    public static NumericQuestion getNewNumericQuestion(String name) {
        NumericQuestion q = new NumericQuestion();
        q.setName(name);
        return q;
    }

    public static StockQuestion getNewStockQuestion(String name) {
        StockQuestion q = new StockQuestion();
        q.setName(name);
        return q;
    }

    public static BooleanCodedQuestion getNewBooleanCodedQuestion(String name) {
        BooleanCodedQuestion q = new BooleanCodedQuestion();
        q.setName(name);
        return q;
    }

    /* below this is for mocks for our inital project goal */
    /* each section is mocked out in its own method */
    public static FacilityDataFormSchema createMockSchema() {

        List<FacilityDataFormSchema> schemas = getService().getAllFacilityDataFormSchemas();
        if (!schemas.isEmpty()) {
            return schemas.get(0);
        }

        FacilityDataFormSchema schema = new FacilityDataFormSchema();
        schema.setName("Daily Site Report");
        schema.setFrequency(FacilityDataFrequency.DAILY);

        {
            FacilityDataFormSection section = createNewFormSection("Equipment Status");

            // question 1a -- section data will be set up later.
            FacilityDataQuestion elecWorking = getNewBooleanCodedQuestion("test");
            elecWorking.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion electricFormQuestion = createNewFormQuestion("Electric Working", "1a", elecWorking);
            electricFormQuestion.setSection(section);
            section.addQuestion(electricFormQuestion);

            // question 1b
            FacilityDataQuestion genWorking = getNewBooleanCodedQuestion("test");
            genWorking.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion genWorkingFormQuestion = createNewFormQuestion("Generator Working", "1b", genWorking);
            genWorkingFormQuestion.setSection(section);
            section.addQuestion(genWorkingFormQuestion);

            schema.addFormSection(section);
        }

        {
            FacilityDataFormSection section = createNewFormSection("Number of Vaccinations Performed");

            // question 2a -- section data will be set up later.
            FacilityDataQuestion numAdults = getNewNumericQuestion("test");
            numAdults.setAggregationMethod(AggregationMethod.SUM);

            FacilityDataFormQuestion numAdultsFormQuestion = createNewFormQuestion("Adults", "2a", numAdults);
            numAdultsFormQuestion.setSection(section);
            section.addQuestion(numAdultsFormQuestion);

            // question 2b
            FacilityDataQuestion numChildren = getNewNumericQuestion("test");
            numChildren.setAggregationMethod(AggregationMethod.SUM);

            FacilityDataFormQuestion numChildrenFormQuestion = createNewFormQuestion("Children", "2b", numChildren);
            numChildrenFormQuestion.setSection(section);
            section.addQuestion(numChildrenFormQuestion);

            schema.addFormSection(section);
        }

        {
            FacilityDataFormSection section = createNewFormSection("Mobile Clinic");

            // 3a
            FacilityDataQuestion mobileClinicQuestion = getNewBooleanCodedQuestion("test");
            mobileClinicQuestion.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion mobileClinicFormQuestion = createNewFormQuestion("Was there a mobile clinic today?", "3a",
                    mobileClinicQuestion);
            section.addQuestion(mobileClinicFormQuestion);

            // 3b
            FacilityDataQuestion numAdultsSeen = getNewNumericQuestion("test");
            numAdultsSeen.setAggregationMethod(AggregationMethod.SUM);

            FacilityDataFormQuestion numAdultsSeenQuestion = createNewFormQuestion("Number of Adults Seen", "3b", numAdultsSeen);
            numAdultsSeenQuestion.setSection(section);
            section.addQuestion(numAdultsSeenQuestion);

            // 3c
            FacilityDataQuestion numChildrenSeen = getNewNumericQuestion("test");
            numChildrenSeen.setAggregationMethod(AggregationMethod.SUM);

            FacilityDataFormQuestion numChildrenSeenFormQuestion = createNewFormQuestion("Number of Children Seen", "3c", numChildrenSeen);
            numChildrenSeenFormQuestion.setSection(section);
            section.addQuestion(numChildrenSeenFormQuestion);

            schema.addFormSection(section);
        }

        {
            FacilityDataFormSection section = createNewFormSection("Stock Status - Vaccinations");

            // 4a
            FacilityDataQuestion measles = getNewStockQuestion("test");
            measles.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion measlesFormQuestion = createNewFormQuestion("Measles", "4a", measles);
            measlesFormQuestion.setSection(section);
            section.addQuestion(measlesFormQuestion);

            // 4b
            FacilityDataQuestion polio = getNewStockQuestion("test");
            polio.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion polioFormQuestion = createNewFormQuestion("Polio", "4b", polio);
            polioFormQuestion.setSection(section);
            section.addQuestion(polioFormQuestion);

            // 4c
            FacilityDataQuestion tetanus = getNewStockQuestion("test");
            tetanus.setAggregationMethod(AggregationMethod.LAST);

            FacilityDataFormQuestion tetanusFormQuestion = createNewFormQuestion("Tetanus", "4c", tetanus);
            tetanusFormQuestion.setSection(section);
            section.addQuestion(tetanusFormQuestion);

            schema.addFormSection(section);
        }

        return getService().saveFacilityDataFormSchema(schema);
    }
}