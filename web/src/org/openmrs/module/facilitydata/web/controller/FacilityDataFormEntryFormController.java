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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataCodedOption;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.model.enums.Frequency;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;
import org.openmrs.propertyeditor.LocationEditor;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/facilitydata/formEntry.form")
public class FacilityDataFormEntryFormController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
	 	binder.registerCustomEditor(Location.class, new LocationEditor());
	 	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), false));
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(ModelMap map, HttpServletRequest request, 
    		               @RequestParam(required = true) FacilityDataFormSchema schema,
                           @RequestParam(required = true) Location facility, 
                           @RequestParam(required = true) Date fromDate,
                           @RequestParam(required = false) Boolean viewOnly) {
    	
		Date toDate = fromDate;
		if (schema.getForm().getFrequency() == Frequency.MONTHLY) {
			toDate = FacilityDataUtil.getEndOfMonth(fromDate);
		}
		else if (schema.getForm().getFrequency() != Frequency.DAILY) {
			throw new RuntimeException("Unsupported period of " + schema.getForm().getFrequency());
		}
		FacilityDataReport report = Context.getService(FacilityDataService.class).getReport(schema, fromDate, toDate, facility);
		map.addAttribute("schema", schema);
		map.addAttribute("facility", facility);
		map.addAttribute("fromDate", fromDate);
		map.addAttribute("toDate", toDate);
		map.addAttribute("viewOnly", viewOnly == Boolean.TRUE);
	    map.addAttribute("report", report);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String saveReport( ModelMap map,
    						  @RequestParam(required = false) FacilityDataFormSchema schema,
    						  @RequestParam(required = false) Location facility, 
    						  @RequestParam(required = false) Date fromDate,
    		                  @RequestParam(required = false) Date toDate,
    		                  HttpServletRequest request) {
    	
    	FacilityDataReport report = Context.getService(FacilityDataService.class).getReport(schema, fromDate, toDate, facility);
    	Date currentDate = new Date();
    	User currentUser = Context.getAuthenticatedUser();
    	
    	for (FacilityDataFormSection section : schema.getSections()) {
    		for (FacilityDataFormQuestion question : section.getQuestions()) {
    			FacilityDataValue value = report.getValue(question);
    			String valueCodedParam = request.getParameter("valueCoded." + question.getId());
    			String valueNumericParam = request.getParameter("valueNumeric." + question.getId());
    			String commentsParam = request.getParameter("comments." + question.getId());
    			FacilityDataCodedOption valueCoded = null;
    			Double valueNumeric = null;
    			if (StringUtils.isNotBlank(valueCodedParam)) {
    				CodedFacilityDataQuestionType codedType = (CodedFacilityDataQuestionType) question.getQuestion().getQuestionType();
    				valueCoded = codedType.getOptionById(Integer.parseInt(valueCodedParam));
    			}
    			else if (StringUtils.isNotBlank(valueNumericParam)) {
    				valueNumeric = Double.valueOf(valueNumericParam);
    			}
    			boolean createNew = valueCoded != null || valueNumeric != null || StringUtils.isNotBlank(commentsParam);
    			if (value != null){
    				if (!OpenmrsUtil.nullSafeEquals(valueNumeric, value.getValueNumeric()) || 
    					!OpenmrsUtil.nullSafeEquals(valueCoded, value.getValueCoded()) || 
    					!OpenmrsUtil.nullSafeEquals(commentsParam, value.getComments())) {
    					value.setVoided(true);
    					value.setVoidedBy(currentUser);
    					value.setDateVoided(currentDate);
    				}
    				else {
    					createNew = false;
    				}
    			}
    			if (createNew) {
    				FacilityDataValue newValue = new FacilityDataValue();
    				newValue.setFacility(facility);
    				newValue.setFromDate(fromDate);
    				newValue.setToDate(toDate);
    				newValue.setQuestion(question);
    				newValue.setValueCoded(valueCoded);
    				newValue.setValueNumeric(valueNumeric);
    				newValue.setComments(commentsParam);
    				report.addValue(newValue);
    			}
    		}
    	}
    	SimpleDateFormat df = Context.getDateFormat();
    	Context.getService(FacilityDataService.class).saveReport(report);
    	return String.format("redirect:formEntry.form?schema=%s&facility=%s&fromDate=%s&toDate=%s&viewOnly=true", 
    						 schema.getId(), facility.getId(), df.format(fromDate), df.format(toDate));
    }
}