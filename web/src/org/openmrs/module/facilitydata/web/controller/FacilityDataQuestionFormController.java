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

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.enums.PeriodApplicability;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionEditor;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionTypeEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.validator.FacilityDataQuestionValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/facilitydata/questionForm.form")
public class FacilityDataQuestionFormController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestion.class, new FacilityDataQuestionEditor());
        binder.registerCustomEditor(FacilityDataQuestionType.class, new FacilityDataQuestionTypeEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(@RequestParam(required = false) Integer id, ModelMap map,
                           @ModelAttribute("question") FacilityDataQuestion question) {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
        map.addAttribute("allQuestionTypes", svc.getAllQuestionTypes());
        map.addAttribute("periodApplicabilities", PeriodApplicability.values());
        if (id != null) {
            question = svc.getQuestion(id);
            map.addAttribute("question", question);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestion(@RequestParam(required = false) Integer id,
                             @ModelAttribute("question") FacilityDataQuestion question, BindingResult result,
                             HttpServletRequest request, ModelMap map) throws ServletRequestBindingException {   	
    	new FacilityDataQuestionValidator().validate(question, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionForm";
        }
        Context.getService(FacilityDataService.class).saveQuestion(question);
        request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "facilitydata.question.saved");
        return "redirect:question.list";
    }
}