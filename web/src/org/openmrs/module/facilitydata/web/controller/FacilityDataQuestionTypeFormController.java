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
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionTypeEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.validator.FacilityDataQuestionTypeValidator;
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
public class FacilityDataQuestionTypeFormController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestionType.class, new FacilityDataQuestionTypeEditor());
    }

    @RequestMapping("/module/facilitydata/questionTypeForm.form")
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

    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestionType(@RequestParam(required = false) Integer id,
                             @ModelAttribute("questionType") FacilityDataQuestionType questionType, BindingResult result,
                             HttpServletRequest request, ModelMap map) throws ServletRequestBindingException {
        new FacilityDataQuestionTypeValidator().validate(questionType, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionTypeForm";
        }
        Context.getService(FacilityDataService.class).saveQuestionType(questionType);
        request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "facilitydata.questionType.saved");
        return String.format("redirect:questionType.form?id=%s", questionType.getId());
    }

    /*
    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestion(@RequestParam(required = false) Integer id, @ModelAttribute("command") FacilityDataQuestionType questionType, BindingResult result, HttpServletRequest request) {

        Map<String, String> questionTypeTypes = new HashMap<String, String>();
        questionTypeTypes.put("NumericQuestion", "org.openmrs.module.facilitydata.model.NumericQuestion");
        questionTypeTypes.put("StockQuestion", "org.openmrs.module.facilitydata.model.StockQuestion");
        questionTypeTypes.put("BooleanCodedQuestion", "org.openmrs.module.facilitydata.model.BooleanCodedQuestion");
        new FacilityDataQuestionTypeValidator().validate(command, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionTypeTypeForm";
        }
        FacilityDataService service = Context.getService(FacilityDataService.class);
        if (id != null) {
            FacilityDataQuestionType questionType = service.getQuestion(id);
            if (hasDataTypeChanged(questionType, command)) {
                command.setType(questionType.getClass().getSimpleName());
                // need to clear out the NumericQuestion properties.
                if (!FacilityDataFunctions.isNumericQuestion(questionType)) {
                    command.setAllowDecimals(false);
                    command.setMinValue(null);
                    command.setMaxValue(null);
                }
                result.rejectValue("type", "facilitydata.type-change");
                return "/module/facilitydata/questionTypeTypeForm";
            }
            questionType.setName(command.getName());
            questionType.setDescription(command.getDescription());
            questionType.setAggregationMethod(command.getMethod());
            if (command.getRetired() != null && command.getRetired())
                service.retireFacilityDataQuestionType(questionType, command.getRetireReason());
            if (command.getRetired() != null && !command.getRetired()) service.unretireFacilityDataQuestionType(questionType);
            if (FacilityDataFunctions.isNumericQuestion(questionType)) {
                if (StringUtils.hasText(request.getParameter("minValue")))
                    ((NumericFacilityDataQuestionType) questionType).setMinValue(command.getMinValue());
                if (StringUtils.hasText(request.getParameter("maxValue")))
                    ((NumericFacilityDataQuestionType) questionType).setMaxValue(command.getMaxValue());
                if (command.getAllowDecimals() != null)
                    ((NumericFacilityDataQuestionType) questionType).setAllowDecimals(command.getAllowDecimals());
            }
            service.saveQuestion(questionType);
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.questionType.saved"));
        } else if (command.getTypes().contains(command.getType())) {
            try {
                Class<?> clazz = Class.forName(questionTypeTypes.get(command.getType()));
                if (NumericFacilityDataQuestionType.class.isAssignableFrom(clazz)) {
                    NumericFacilityDataQuestionType questionType = (NumericFacilityDataQuestionType) clazz.newInstance();
                    questionType.setName(command.getName());
                    if (command.getDescription() != null) questionType.setDescription(command.getDescription());
                    questionType.setAggregationMethod(command.getMethod());
                    if (StringUtils.hasText(request.getParameter("minValue")))
                        questionType.setMinValue(command.getMinValue());
                    if (StringUtils.hasText(request.getParameter("maxValue")))
                        questionType.setMaxValue(command.getMaxValue());
                    if (command.getAllowDecimals() != null) questionType.setAllowDecimals(command.getAllowDecimals());
                    FacilityDataQuestionType q = service.saveQuestion(questionType);
                    request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.questionType.saved"));
                    return getRedirect(q);
                } else if (CodedFacilityDataQuestionType.class.isAssignableFrom(clazz)) {
                    CodedFacilityDataQuestionType codedQuestion = (CodedFacilityDataQuestionType) clazz.newInstance();
                    codedQuestion.setAggregationMethod(command.getMethod());
                    codedQuestion.setName(command.getName());
                    if (command.getDescription() != null) codedQuestion.setDescription(command.getDescription());
                    FacilityDataQuestionType q = service.saveQuestion(codedQuestion);
                    request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.questionType.saved"));
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
        
        return String.format("redirect:questionType.form?id=%s", id);
    }
    */
}