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

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.BooleanCodedQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.NumericQuestion;
import org.openmrs.module.facilitydata.model.StockQuestion;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;
import org.openmrs.module.facilitydata.validator.FacilityDataQuestionValidator;
import org.openmrs.module.facilitydata.command.FacilityDataQuestionCommand;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Controller("question")
@RequestMapping("/module/facilitydata/question.form")
public class FacilityDataQuestionFormController {
    private static final Logger log = Logger.getLogger(FacilityDataQuestionFormController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String homepage(ModelMap map, @RequestParam(required = false) Integer id, @ModelAttribute("command") FacilityDataQuestionCommand command) {
        if (id != null) {
            FacilityDataQuestion question = FacilityDataUtil.getService().getQuestion(id);
            command.setName(question.getName());
            command.setDescription(command.getDescription());
            command.setType(question.getClass().getSimpleName());
            command.setMethod(question.getAggregationMethod());
            command.setRetired(question.getRetired());
            command.setRetireReason(question.getRetireReason());
            if (FacilityDataUtil.isNumericQuestion(question)) {
                NumericQuestion numericQuestion = (NumericQuestion) question;
                command.setMinValue(numericQuestion.getMinValue());
                command.setMaxValue(numericQuestion.getMaxValue());
                command.setAllowDecimals(numericQuestion.isAllowDecimals());
            }
        }
        return "/module/facilitydata/questionForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestion(@RequestParam(required = false) Integer id, @ModelAttribute("command") FacilityDataQuestionCommand command, BindingResult result, HttpServletRequest request) {

        Map<String, String> questionTypes = Maps.newHashMap();
        questionTypes.put("NumericQuestion", "org.openmrs.module.facilitydata.model.NumericQuestion");
        questionTypes.put("StockQuestion", "org.openmrs.module.facilitydata.model.StockQuestion");
        questionTypes.put("BooleanCodedQuestion", "org.openmrs.module.facilitydata.model.BooleanCodedQuestion");
        new FacilityDataQuestionValidator().validate(command, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionForm";
        }
        FacilityDataService service = FacilityDataUtil.getService();
        if (id != null) {
            FacilityDataQuestion question = service.getQuestion(id);
            if (hasDataTypeChanged(question, command)) {
                command.setType(question.getClass().getSimpleName());
                // need to clear out the NumericQuestion properties.
                if (!FacilityDataUtil.isNumericQuestion(question)) {
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
            if (FacilityDataUtil.isNumericQuestion(question)) {
                if (StringUtils.hasText(request.getParameter("minValue")))
                    ((NumericQuestion) question).setMinValue(command.getMinValue());
                if (StringUtils.hasText(request.getParameter("maxValue")))
                    ((NumericQuestion) question).setMaxValue(command.getMaxValue());
                if (command.getAllowDecimals() != null)
                    ((NumericQuestion) question).setAllowDecimals(command.getAllowDecimals());
            }
            FacilityDataQuestion q = service.saveQuestion(question);
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.question.saved"));
        } else if (command.getTypes().contains(command.getType())) {
            try {
                Class<?> clazz = Class.forName(questionTypes.get(command.getType()));
                if (NumericQuestion.class.isAssignableFrom(clazz)) {
                    NumericQuestion question = (NumericQuestion) clazz.newInstance();
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
                } else if (BooleanCodedQuestion.class.isAssignableFrom(clazz)) {
                    BooleanCodedQuestion codedQuestion = (BooleanCodedQuestion) clazz.newInstance();
                    codedQuestion.setAggregationMethod(command.getMethod());
                    codedQuestion.setName(command.getName());
                    if (command.getDescription() != null) codedQuestion.setDescription(command.getDescription());
                    FacilityDataQuestion q = service.saveQuestion(codedQuestion);
                    request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage("facilitydata.question.saved"));
                    return getRedirect(q);
                } else if (StockQuestion.class.isAssignableFrom(clazz)) {
                    StockQuestion stockQuestion = (StockQuestion) clazz.newInstance();
                    stockQuestion.setAggregationMethod(command.getMethod());
                    stockQuestion.setName(command.getName());
                    if (command.getDescription() != null) stockQuestion.setDescription(command.getDescription());
                    FacilityDataQuestion q = service.saveQuestion(stockQuestion);
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

    private boolean hasDataTypeChanged(FacilityDataQuestion question, FacilityDataQuestionCommand command) {
        return !question.getClass().getSimpleName().equals(command.getType());
    }

    private String getRedirect(FacilityDataQuestion question) {
        return String.format("redirect:question.form?id=%s", question.getId());
    }
}