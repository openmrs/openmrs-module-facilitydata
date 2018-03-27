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

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionTypeEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class FacilityDataQuestionTypeListController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestionType.class, new FacilityDataQuestionTypeEditor());
    }

	@RequestMapping("/module/facilitydata/questionType.list")
    public String listQuestions(ModelMap map) {
        map.addAttribute("questionTypes", Context.getService(FacilityDataService.class).getAllQuestionTypes());
        map.addAttribute("questionTypeBreakdown", Context.getService(FacilityDataService.class).getQuestionTypeBreakdown());
        Map<Class<? extends FacilityDataQuestionType>, String> questionDataTypes = new LinkedHashMap<Class<? extends FacilityDataQuestionType>, String>();
        questionDataTypes.put(CodedFacilityDataQuestionType.class, "facilitydata.question-type-coded");
        questionDataTypes.put(NumericFacilityDataQuestionType.class, "facilitydata.question-type-numeric");
        questionDataTypes.put(FreeTextFacilityDataQuestionType.class, "facilitydata.question-type-text");
        questionDataTypes.put(DocumentTypeFacilityDataQuestionType.class, "facilitydata.question-type-document");
        questionDataTypes.put(BlobFacilityDataQuestionType.class, "facilitydata.question-type-blob");
        map.addAttribute("questionDataTypes", questionDataTypes);
        return "/module/facilitydata/questionTypeList";
    }
    
	@RequestMapping("/module/facilitydata/deleteQuestionType.form")
    public String deleteQuestion(ModelMap map, @RequestParam(required = true) FacilityDataQuestionType questionType) {
		Context.getService(FacilityDataService.class).deleteQuestionType(questionType);
        return "redirect:/module/facilitydata/questionType.list";
    }
}