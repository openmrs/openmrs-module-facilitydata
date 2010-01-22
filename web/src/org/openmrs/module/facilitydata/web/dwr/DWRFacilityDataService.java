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
package org.openmrs.module.facilitydata.web.dwr;

import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.enums.FacilityDataFrequency;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;

/**
 * DWR class to perform various JS->Java tasks. 
 */
public class DWRFacilityDataService {
    /**
     * Get the question type to be displayed next to the question data type when retrieving questions.
     * @param questionId
     * @return the simple class name (e.g.,String instead of java.lang.String)
     */
    public String getType(Integer questionId) {
        FacilityDataService service = FacilityDataUtil.getService();
        FacilityDataQuestion facilityDataQuestion = service.getQuestion(questionId);
        return questionId != null ? facilityDataQuestion.getClass().getSimpleName() : "";
    }

    public boolean isMonthlyReport(int schemaId) {
        FacilityDataService svc = FacilityDataUtil.getService();
        FacilityDataFormSchema schema = svc.getFacilityDataFormSchema(schemaId);
        return schema.getFrequency() == FacilityDataFrequency.MONTHLY;        
    }
}
