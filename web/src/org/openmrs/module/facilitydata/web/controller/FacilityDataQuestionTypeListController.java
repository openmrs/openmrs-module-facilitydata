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
package org.openmrs.module.facilitydata.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/facilitydata/questionType.list")
public class FacilityDataQuestionTypeListController {

    @RequestMapping(method= RequestMethod.GET)
    public String listQuestions(ModelMap map) {
        map.addAttribute("questionTypes", Context.getService(FacilityDataService.class).getAllQuestionTypes());
        Map<Class<? extends FacilityDataQuestionType>, String> questionDataTypes = new LinkedHashMap<Class<? extends FacilityDataQuestionType>, String>();
        questionDataTypes.put(CodedFacilityDataQuestionType.class, "facilitydata.question-type-coded");
        questionDataTypes.put(NumericFacilityDataQuestionType.class, "facilitydata.question-type-numeric");
        map.addAttribute("questionDataTypes", questionDataTypes);
        return "/module/facilitydata/questionTypeList";
    }
}