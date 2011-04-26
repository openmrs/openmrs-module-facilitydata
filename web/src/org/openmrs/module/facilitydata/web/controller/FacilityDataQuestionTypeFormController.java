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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataCodedOption;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionTypeEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.validator.FacilityDataQuestionTypeValidator;
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
@RequestMapping("/module/facilitydata/questionTypeForm.form")
public class FacilityDataQuestionTypeFormController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestionType.class, new FacilityDataQuestionTypeEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(@RequestParam(required = false) Integer id, ModelMap map,
    		             @RequestParam(required = false) Class<? extends FacilityDataQuestionType> dataType) throws Exception {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
        FacilityDataQuestionType questionType = null;
        if (id != null) {
            questionType = svc.getQuestionType(id);
        } 
        else {
        	questionType = dataType.newInstance();
        }
        map.addAttribute("questionType", questionType);
    }

    @RequestMapping(method = RequestMethod.POST, params={"dataType=org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType"})
    public String saveNumericQuestionType(@RequestParam(required = false) Integer id,
                             @ModelAttribute("questionType") NumericFacilityDataQuestionType questionType, BindingResult result,
                             HttpServletRequest request, ModelMap map) throws ServletRequestBindingException {
        new FacilityDataQuestionTypeValidator().validate(questionType, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionTypeForm";
        }
        Context.getService(FacilityDataService.class).saveQuestionType(questionType);
        return "redirect:questionType.list";
    }
    
    @RequestMapping(method = RequestMethod.POST, params={"dataType=org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType"})
    public String saveCodedQuestionType(@RequestParam(required = false) Integer id,
                             @ModelAttribute("questionType") CodedFacilityDataQuestionType questionType, BindingResult result,
                             HttpServletRequest request, ModelMap map,
                             @RequestParam(required = false) Integer[] optionId,
                             @RequestParam(required = false) String[] optionName,
                             @RequestParam(required = false) String[] optionCode,
                             @RequestParam(required = false) String[] optionDescription) throws ServletRequestBindingException {

    	// TODO: Make sure options are saved
        new FacilityDataQuestionTypeValidator().validate(questionType, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionTypeForm";
        }
        Context.getService(FacilityDataService.class).saveQuestionType(questionType);
        return "redirect:questionType.list";
    }
}