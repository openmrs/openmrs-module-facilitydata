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
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacilityDataQuestionListController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestion.class, new FacilityDataQuestionEditor());
    }

	@RequestMapping("/module/facilitydata/question.list")
    public String listQuestions(ModelMap map) {
        map.addAttribute("questions", Context.getService(FacilityDataService.class).getAllQuestions());
        map.addAttribute("questionBreakdown", Context.getService(FacilityDataService.class).getQuestionBreakdown());
        return "/module/facilitydata/questionList";
    }
	
	@RequestMapping("/module/facilitydata/deleteQuestion.form")
    public String deleteQuestion(ModelMap map, @RequestParam(required = true) FacilityDataQuestion question) {
		Context.getService(FacilityDataService.class).deleteQuestion(question);
        return "redirect:/module/facilitydata/question.list";
    }
}