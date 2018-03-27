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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataForm;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.enums.Frequency;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.validator.FacilityDataFormSchemaValidator;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/module/facilitydata/schemaForm.form")
public class FacilityDataFormSchemaFormController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true));
    }
    
    @ModelAttribute("frequencies")
    public Frequency[] getFrequencies() {
    	return Frequency.values();
    }
    
    @ModelAttribute("schema")
    public FacilityDataFormSchema getSchema(@RequestParam(value="schema", required=false) FacilityDataFormSchema schema) {
    	if (schema == null) {
    		schema = new FacilityDataFormSchema();
    		schema.setForm(new FacilityDataForm());
    	}
    	return schema;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String viewForm(ModelMap map,
                           @ModelAttribute("schema") FacilityDataFormSchema schema) {
        return "/module/facilitydata/schemaForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSchema(@ModelAttribute("schema") FacilityDataFormSchema schema, BindingResult result, 
    						 ModelMap map, HttpServletRequest request) throws ServletRequestBindingException {
    	
    	FacilityDataService svc = Context.getService(FacilityDataService.class);
        FacilityDataForm form = schema.getForm();
        if (StringUtils.isBlank(schema.getName())) {
        	schema.setName(form.getName());
        }
        if (!form.getSchemas().contains(schema)) {
        	form.getSchemas().add(schema);
        }
        
        new FacilityDataFormSchemaValidator().validate(schema, result);
        if (result.hasErrors()) {
        	
            return "/module/facilitydata/schemaForm";
        }
        
        svc.saveFacilityDataForm(form);
      
        return String.format("redirect:schema.form?id=%s", schema.getSchemaId());
    }
}