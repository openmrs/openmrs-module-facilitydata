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

import java.util.Collections;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FacilityDataFormSchemaController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
        binder.registerCustomEditor(FacilityDataQuestion.class, new FacilityDataQuestionEditor());
    }
    
	@RequestMapping("/module/facilitydata/schema.list")
    public String listSchemas(ModelMap map) {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
        map.addAttribute("schemas", svc.getAllFacilityDataFormSchemas());
        return "/module/facilitydata/schemaList";
    }

    @RequestMapping("/module/facilitydata/schema.form")
    public String viewSchema(@RequestParam(required = false) Integer id, ModelMap map) {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
        if (id == null) {
        	return "redirect:/module/facilitydata/schemaForm.form";
        }
        FacilityDataFormSchema schema = svc.getFacilityDataFormSchema(id);
        map.addAttribute("schema", schema);
        map.addAttribute("questions", svc.getAllQuestions());
        return "/module/facilitydata/schema";
    }
    
    @RequestMapping("/module/facilitydata/saveSection.form")
    public String saveSchema(ModelMap map,
    						 @RequestParam(required=true) FacilityDataFormSchema schema, 
    						 @RequestParam(required=false) Integer id, 
    						 @RequestParam(required=true) String name) throws Exception {

        FacilityDataFormSection section = null;
        if (id == null) {
        	section = new FacilityDataFormSection();
        	section.setSchema(schema);
        	schema.addSection(section);
        }
        else {
        	section = schema.getSectionById(id);
        }
    	section.setName(name);
    	Context.getService(FacilityDataService.class).saveFacilityDataFormSchema(schema);
        return String.format("redirect:schema.form?id=%s", schema.getId());
    }
    
    @RequestMapping("/module/facilitydata/moveSection.form")
    public String moveSection(ModelMap map,
    						 @RequestParam(required=true) FacilityDataFormSchema schema, 
    						 @RequestParam(required=true) Integer existingIndex, 
    						 @RequestParam(required=true) Integer newIndex) throws Exception {

    	Collections.swap(schema.getSections(), existingIndex, newIndex);
    	Context.getService(FacilityDataService.class).saveFacilityDataFormSchema(schema);
        return String.format("redirect:schema.form?id=%s", schema.getId());
    }
    
    @RequestMapping("/module/facilitydata/deleteSection.form")
    public String deleteSection(ModelMap map,
								 @RequestParam(required=true) FacilityDataFormSchema schema, 
								 @RequestParam(required=true) Integer sectionId, 
								 @RequestParam(required=false) Integer newQuestionSectionId) throws Exception {

    	FacilityDataFormSection section = schema.getSectionById(sectionId);
    	FacilityDataFormSection newSection = schema.getSectionById(newQuestionSectionId);
    	if (!section.getQuestions().isEmpty()) {
    		newSection.getQuestions().addAll(section.getQuestions());
    	}
    	schema.getSections().remove(section);
    	Context.getService(FacilityDataService.class).saveFacilityDataFormSchema(schema);
        return String.format("redirect:schema.form?id=%s", schema.getId());
    }
    
    @RequestMapping("/module/facilitydata/saveFormQuestion.form")
    public String saveFormQuestion(ModelMap map,
    						 @RequestParam(required=true) FacilityDataFormSchema schema, 
    						 @RequestParam(required=true) Integer sectionId, 
    						 @RequestParam(required=true) Integer formQuestionId, 
    						 @RequestParam(required=true) String name, 
    						 @RequestParam(required=true) String questionNumber,
    						 @RequestParam(required=true) FacilityDataQuestion question) throws Exception {

    	FacilityDataFormSection section = schema.getSectionById(sectionId);
    	
        FacilityDataFormQuestion q = null;
        if (formQuestionId == null) {
        	q = new FacilityDataFormQuestion();
        	q.setSection(section);
        	section.getQuestions().add(q);
        }
        else {
        	q = section.getQuestionById(formQuestionId);
        }
    	q.setName(name);
    	q.setQuestionNumber(questionNumber);
    	q.setQuestion(question);
    	Context.getService(FacilityDataService.class).saveFacilityDataFormSchema(schema);
        return String.format("redirect:schema.form?id=%s", schema.getId());
    }
}