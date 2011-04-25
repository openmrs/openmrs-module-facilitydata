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
import org.openmrs.module.facilitydata.model.enums.PeriodApplicability;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionEditor;
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
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(@RequestParam(required = false) Integer id, ModelMap map,
                           @ModelAttribute("question") FacilityDataQuestion question) {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
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
        return String.format("redirect:question.form?id=%s", question.getId());
    }

    /*
    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestion(@RequestParam(required = false) Integer id, @ModelAttribute("command") FacilityDataQuestion question, BindingResult result, HttpServletRequest request) {

        Map<String, String> questionTypes = new HashMap<String, String>();
        questionTypes.put("NumericQuestion", "org.openmrs.module.facilitydata.model.NumericQuestion");
        questionTypes.put("StockQuestion", "org.openmrs.module.facilitydata.model.StockQuestion");
        questionTypes.put("BooleanCodedQuestion", "org.openmrs.module.facilitydata.model.BooleanCodedQuestion");
        new FacilityDataQuestionValidator().validate(command, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionForm";
        }
        FacilityDataService service = Context.getService(FacilityDataService.class);
        if (id != null) {
            FacilityDataQuestion question = service.getQuestion(id);
            if (hasDataTypeChanged(question, command)) {
                command.setType(question.getClass().getSimpleName());
                // need to clear out the NumericQuestion properties.
                if (!FacilityDataFunctions.isNumericQuestion(question)) {
                    command.setAllowDecimals(false);
                    command.setMinValue(null);
                    command.setMaxValue(null);
                }
                result.rejectValue("type", "facilitydata.type-change");
                return "/module/facilitydata/questionForm";
            }
            question.setName(command.getName());
            question.setDescription(command.getDescription());
            question.setAggregationMethod(command.getMethod());
            if (command.getRetired() != null && command.getRetired())
                service.retireFacilityDataQuestion(question, command.getRetireReason());
            if (command.getRetired() != null && !command.getRetired()) service.unretireFacilityDataQuestion(question);
            if (FacilityDataFunctions.isNumericQuestion(question)) {
                if (StringUtils.hasText(request.getParameter("minValue")))
                    ((NumericFacilityDataQuestion) question).setMinValue(command.getMinValue());
                if (StringUtils.hasText(request.getParameter("maxValue")))
                    ((NumericFacilityDataQuestion) question).setMaxValue(command.getMaxValue());
                if (command.getAllowDecimals() != null)
                    ((NumericFacilityDataQuestion) question).setAllowDecimals(command.getAllowDecimals());
            }
            service.saveQuestion(question);
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.question.saved"));
        } else if (command.getTypes().contains(command.getType())) {
            try {
                Class<?> clazz = Class.forName(questionTypes.get(command.getType()));
                if (NumericFacilityDataQuestion.class.isAssignableFrom(clazz)) {
                    NumericFacilityDataQuestion question = (NumericFacilityDataQuestion) clazz.newInstance();
                    question.setName(command.getName());
                    if (command.getDescription() != null) question.setDescription(command.getDescription());
                    question.setAggregationMethod(command.getMethod());
                    if (StringUtils.hasText(request.getParameter("minValue")))
                        question.setMinValue(command.getMinValue());
                    if (StringUtils.hasText(request.getParameter("maxValue")))
                        question.setMaxValue(command.getMaxValue());
                    if (command.getAllowDecimals() != null) question.setAllowDecimals(command.getAllowDecimals());
                    FacilityDataQuestion q = service.saveQuestion(question);
                    request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.question.saved"));
                    return getRedirect(q);
                } else if (CodedFacilityDataQuestion.class.isAssignableFrom(clazz)) {
                    CodedFacilityDataQuestion codedQuestion = (CodedFacilityDataQuestion) clazz.newInstance();
                    codedQuestion.setAggregationMethod(command.getMethod());
                    codedQuestion.setName(command.getName());
                    if (command.getDescription() != null) codedQuestion.setDescription(command.getDescription());
                    FacilityDataQuestion q = service.saveQuestion(codedQuestion);
                    request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.question.saved"));
                    return getRedirect(q);
                }
            } catch (ClassNotFoundException e) {
                // ignore
            } catch (InstantiationException e) {
                // ignore
            } catch (IllegalAccessException e) {
                // ignore
            }
        } else {
            request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage("facilitydata.error.saved"));
        }
        
        return String.format("redirect:question.form?id=%s", id);
    }
    */
}