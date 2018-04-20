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

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.model.enums.Frequency;
import org.openmrs.module.facilitydata.model.enums.PeriodApplicability;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataConstants;
import org.openmrs.module.facilitydata.util.FacilityDataQuery;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;
import org.openmrs.propertyeditor.LocationEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class FacilityDataCompletionAnalysisFormController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
    	binder.registerCustomEditor(Location.class, new LocationEditor());
	 	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true));
    }
    
    @ModelAttribute("locations")
    public List<Location> getLocations() {
		return FacilityDataConstants.getSupportedFacilities();
    }
    
    @ModelAttribute("schemas")
    public List<FacilityDataFormSchema> getSchemas() {
		return Context.getService(FacilityDataService.class).getAllFacilityDataFormSchemas();
    }

    @RequestMapping("/module/facilitydata/completionAnalysis.form")
    public void viewForm(ModelMap map, HttpServletRequest request, @ModelAttribute("query") FacilityDataQuery query) {	
    	
    	FacilityDataService fds = Context.getService(FacilityDataService.class);
    	
    	if (query.getSchema() != null) {
    		
    		Date validFrom = query.getSchema().getValidFrom();
    		if (query.getFromDate() != null && validFrom != null && query.getFromDate().before(validFrom)) {
    			query.setFromDate(validFrom);
    		}
    		if (query.getFromDate() == null) {
    			Date minEntryDate = fds.getMinEnteredStartDateForSchema(query.getSchema());
    			if (minEntryDate == null) {
    				minEntryDate = new Date();
    			}
    			query.setFromDate(minEntryDate);
    		}
    		
    		Date validTo = query.getSchema().getValidTo();
    		if (query.getToDate() != null && validTo != null && query.getToDate().after(validTo)) {
    			query.setToDate(validTo);
    		}
    		if (query.getToDate() == null) {
    			query.setToDate(new Date());
    		}
    		
    		Calendar cal = Calendar.getInstance();
    		Frequency frequency = query.getSchema().getForm().getFrequency();
    		if (frequency == Frequency.MONTHLY) {
    			cal.setTime(query.getFromDate());
    			if (cal.get(Calendar.DATE) > 1) {
    				cal.set(Calendar.DATE, 1);
    				query.setFromDate(cal.getTime());
    			}
    			cal.setTime(query.getToDate());
    			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
    			query.setToDate(cal.getTime());
    		}
    		
    		int numExpected = 0;
    		List<Integer> daysOfWeek = FacilityDataConstants.getDailyReportDaysOfWeek();
    		Map<FacilityDataFormQuestion, Integer> numValuesByQuestion = new LinkedHashMap<FacilityDataFormQuestion, Integer>();
    		Map<FacilityDataFormQuestion, Double> numericTotals = new HashMap<FacilityDataFormQuestion, Double>();
    		Map<FacilityDataFormQuestion, Map<FacilityDataCodedOption, Integer>> codedTotals = new HashMap<FacilityDataFormQuestion, Map<FacilityDataCodedOption, Integer>>();
    		
    		cal.setTime(query.getFromDate());
    		while(cal.getTime().compareTo(query.getToDate()) <= 0) {
    			if (frequency != Frequency.DAILY || daysOfWeek.contains(cal.get(Calendar.DAY_OF_WEEK))) {
    				numExpected ++;
    			}
    			cal.add(frequency.getCalendarField(), frequency.getCalendarIncrement());
    		}
    		
    		List<FacilityDataValue> values = fds.evaluateFacilityDataQuery(query);
    		Map<FacilityDataFormQuestion, Map<Location, FacilityDataValue>> latestValuesForQuestion = new HashMap<FacilityDataFormQuestion, Map<Location, FacilityDataValue>>();
    		for (FacilityDataValue v : values) {
    			
				Integer numValues = numValuesByQuestion.get(v.getQuestion());
				numValuesByQuestion.put(v.getQuestion(), numValues == null ? 1 : numValues + 1);
				
    			FacilityDataQuestionType type = v.getQuestion().getQuestion().getQuestionType();
    			if (type instanceof CodedFacilityDataQuestionType) {
    				Map<FacilityDataCodedOption, Integer> m = codedTotals.get(v.getQuestion());
    				if (m == null) {
    					m = new HashMap<FacilityDataCodedOption, Integer>();
    					codedTotals.put(v.getQuestion(), m);
    				}
    				Integer num = m.get(v.getValueCoded());
    				num = num == null ? 1 : num + 1;
    				m.put(v.getValueCoded(), num);
    			}
    			else {
    				PeriodApplicability pa = v.getQuestion().getQuestion().getPeriodApplicability();
    				if (pa == PeriodApplicability.DURING_PERIOD) {
	    				Double num = numericTotals.get(v.getQuestion());
	    				if (num == null) {
	    					num = new Double(0);
	    				}
	    				num += v.getValueNumeric();
	    				numericTotals.put(v.getQuestion(), num);
    				}
    				else {
    					Map<Location, FacilityDataValue> valueForQuestion = latestValuesForQuestion.get(v.getQuestion());
    					if (valueForQuestion == null) {
    						valueForQuestion = new HashMap<Location, FacilityDataValue>();
    						latestValuesForQuestion.put(v.getQuestion(), valueForQuestion);
    					}
    					FacilityDataValue valueForLocation = valueForQuestion.get(v.getFacility());
    					if (valueForLocation == null || valueForLocation.getToDate().before(v.getToDate())) {
    						valueForQuestion.put(v.getFacility(), v);
    					}
    				}
    			}
    		}
    		if (!latestValuesForQuestion.isEmpty()) {
    			for (FacilityDataFormSection section : query.getSchema().getSections()) {
    				for (FacilityDataFormQuestion question : section.getQuestions()) {
    					Double d = new Double(0);
    					Map<Location, FacilityDataValue> m = latestValuesForQuestion.get(question);
    					if (m != null) {
	    					for (FacilityDataValue v : m.values()) {
	    						if (v != null) {
	    							d += v.getValueNumeric();
	    						}
	    					}
	    					numericTotals.put(question, d);
    					}
    				}
    			}
    		}
    		
    		if (query.getFacility() == null) {
    			numExpected = numExpected * getLocations().size();
    		}
			else {
				Set<Location> allFacilities = FacilityDataUtil.getAllLocationsInHierarchy(query.getFacility());
				List<Location> supportedFacilities = FacilityDataConstants.getSupportedFacilities();
				allFacilities.retainAll(supportedFacilities);
				numExpected = numExpected * allFacilities.size();
			}
    		
    		map.addAttribute("numExpected", numExpected);
    		map.addAttribute("numValuesByQuestion", numValuesByQuestion);
    		map.addAttribute("numericTotals", numericTotals);
    		map.addAttribute("codedTotals", codedTotals);
    	}
    }
}