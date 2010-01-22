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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormQuestionEditor;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSectionEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;
import org.openmrs.module.facilitydata.validator.FacilityDataFormSectionValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@Controller
@RequestMapping("/module/facilitydata/section.form")
public class FacilityDataFormSectionFormController {
    private static final Logger log = Logger.getLogger(FacilityDataFormSectionFormController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataFormSection.class, new FacilityDataFormSectionEditor());
        binder.registerCustomEditor(FacilityDataFormQuestion.class, new FacilityDataFormQuestionEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homepage(@RequestParam(required = false) Integer id, ModelMap map,
                           @ModelAttribute("section") FacilityDataFormSection section) {
        FacilityDataService svc = FacilityDataUtil.getService();
        map.addAttribute("questions", svc.getAllFacilityDataQuestions());
        if (id != null) {
            section = svc.getFacilityDataFormSection(id);
            map.addAttribute("section", section);
        }

        return "/module/facilitydata/sectionForm";
    }

    //TODO: questions aren't actually edited -- instead new questions are created... this needs to be fixed.
    @RequestMapping(method = RequestMethod.POST)
    public String saveSection(@ModelAttribute("section") FacilityDataFormSection section, BindingResult result,
                              HttpServletRequest request, ModelMap map) throws ServletRequestBindingException {
        FacilityDataService svc = FacilityDataUtil.getService();
        new FacilityDataFormSectionValidator().validate(section, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/sectionForm";
        }
        String[] ids = ServletRequestUtils.getStringParameters(request, "form_question_id");
        String[] displayNames = ServletRequestUtils.getStringParameters(request, "displayName");
        String[] questionNumbers = ServletRequestUtils.getStringParameters(request, "questionNumber");
        String[] descriptions = ServletRequestUtils.getStringParameters(request, "desc");
        int[] questionIds = ServletRequestUtils.getIntParameters(request, "questionId");
        Map<Integer, FacilityDataFormQuestion> questionMap = Maps.newHashMap();
        SortedSet<FacilityDataFormQuestion> questions = section.getQuestions();

        // used for removing questions that no longer exist (removed via the UI).
        List<FacilityDataFormQuestion> formQuestionList = Lists.newArrayList();

        FacilityDataFormSection formSection = null;

        // this is a hacky way to handle editing an existing question.
        for (FacilityDataFormQuestion q : questions) {
            questionMap.put(q.getId(), q);
        }


        for (int i = 0; i < displayNames.length; i++) {
            FacilityDataFormQuestion question = null;
            if(!(ids.length < displayNames.length)) {
                question = questionMap.get(Integer.parseInt(ids[i]));
            }
            String name = displayNames[i];
            String desc = descriptions[i];
            int questionId = questionIds[i];
            FacilityDataQuestion facilityDataQuestion = svc.getQuestion(questionId);
            String questionNumber = questionNumbers[i];
            if (question != null) {
                if (!name.equals(question.getName())) {
                    question.setName(name);
                }
                if (!desc.equals(question.getDescription())) {
                    question.setDescription(desc);
                }
                if (!facilityDataQuestion.equals(question.getQuestion())) {
                    question.setQuestion(facilityDataQuestion);
                }
                if (!questionNumber.equals(question.getQuestionNumber())) {
                    question.setQuestionNumber(questionNumber);
                }
                if (question.getSection() == null) {
                    question.setSection(section);
                }
                section.addQuestion(question);
                formQuestionList.add(question);

            } else {
                FacilityDataFormQuestion q = FacilityDataUtil.createNewFormQuestion(name, questionNumber, facilityDataQuestion);
                if (!"".equals(desc)) q.setDescription(desc);
                section.addQuestion(q);
                formQuestionList.add(q);
            }
        }
        //pruneQuestions(section.formQuestionList);

        formSection = svc.saveFacilityDataFormSection(section);
        request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,"facilitydata.save-section");
        return formSection != null ? String.format("redirect:section.form?id=%s", formSection.getId())
                : "/module/facilitydata/sectionForm";

    }

    private void pruneQuestions(FacilityDataFormSection section, List<FacilityDataFormQuestion> questions) {
        for (FacilityDataFormQuestion q : section.getQuestions()) {
            if (questions.contains(q)) {
                //TODO: do we want to remove or retire??
                //removeQuestion(section, q);
            }
            else {
                section.addQuestion(q);
            }
        }
    }

    // remove the question from the section's Set.
    private void removeQuestion(FacilityDataFormSection section, FacilityDataFormQuestion question) {
        for (Iterator<FacilityDataFormQuestion> iterator = section.getQuestions().iterator(); iterator.hasNext();) {
            FacilityDataFormQuestion q = iterator.next();
            if (q.equals(question)) iterator.remove();
        }
    }
}