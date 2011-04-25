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
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
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
    }

    @RequestMapping("/module/facilitydata/schema.form")
    public String viewSchema(@RequestParam(required = false) Integer id, ModelMap map) {
        FacilityDataService svc = Context.getService(FacilityDataService.class);
        if (id == null) {
        	return "redirect:/module/facilitydata/schemaForm.form";
        }
        FacilityDataFormSchema schema = svc.getFacilityDataFormSchema(id);
        map.addAttribute("schema", schema);
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
        	for (FacilityDataFormSection s : schema.getSections()) {
        		if (s.getId().equals(id)) {
        			section = s;
        		}
        	}
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
}