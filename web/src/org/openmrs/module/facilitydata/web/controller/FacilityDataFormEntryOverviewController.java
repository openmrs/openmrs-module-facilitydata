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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.enums.Frequency;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormSchemaEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacilityDataFormEntryOverviewController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true));
    }

    @RequestMapping("/module/facilitydata/formEntryOverview.form")
    public void formEntryOverview(ModelMap map, 
    								@RequestParam(required = true) FacilityDataFormSchema schema,
    								@RequestParam(required = false) Integer yearIncrement,
    								@RequestParam(required = false) Integer monthIncrement) throws Exception {
    	
    	FacilityDataService service = Context.getService(FacilityDataService.class);

    	Calendar cal = Calendar.getInstance();
    	
    	if (yearIncrement != null) {
    		cal.add(Calendar.YEAR, yearIncrement);
    	}
    	if (monthIncrement != null) {
    		cal.add(Calendar.MONTH, monthIncrement);
    	}

    	Date endDate = cal.getTime();
    	if (schema.getFrequency() == Frequency.MONTHLY) {  // For monthly reports, display last year
    		cal.set(Calendar.DATE, 1);
    		cal.add(Calendar.YEAR, -1);
    	}
    	else if (schema.getFrequency() == Frequency.DAILY) {  // For daily reports, display last 3 weeks
    		cal.add(Calendar.DATE, -21);
    	}
    	else {
    		throw new RuntimeException("Unable to handle a report with frequency: " + schema.getFrequency());
    	}
    	Date startDate = cal.getTime();
    	
    	Map<Integer, Map<String, Integer>> questionsAnswered = service.getNumberOfQuestionsAnswered(schema, startDate, endDate);
    	
    	DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    	DateFormat monthFormat = new SimpleDateFormat("MMM");
    	
    	Map<Integer, Integer> yearCols = new LinkedHashMap<Integer, Integer>(); // Year -> Number of columns
    	Map<String, Integer> monthCols = new LinkedHashMap<String, Integer>();  // Month -> Number of columns
    	Map<String, Date> dayCols = new LinkedHashMap<String, Date>();
    	Map<Integer, Map<String, Integer>> dayData = new HashMap<Integer, Map<String, Integer>>();    // LocationId -> Day -> Number of questions
    	Map<Object, String> displayKeys = new HashMap<Object, String>();  // Map key -> Display format
    	
    	while (cal.getTime().before(endDate)) {
    		
    		String dateStr = ymdFormat.format(cal.getTime());
    		Integer year = cal.get(Calendar.YEAR);
    		String month = monthFormat.format(cal.getTime());
    		Integer day = cal.get(Calendar.DAY_OF_MONTH);
    		
    		yearCols.put(year, yearCols.get(year) == null ? 1 : yearCols.get(year) + 1);
    		monthCols.put(month+year, monthCols.get(month+year) == null ? 1 : monthCols.get(month+year) + 1);
    		dayCols.put(day+month+year, cal.getTime());
    		
    		for (Integer locationId : questionsAnswered.keySet()) {
    			Map<String, Integer> questionsAnsweredAtLocation = questionsAnswered.get(locationId);
    			Integer numAnswered = questionsAnsweredAtLocation == null ? null : questionsAnsweredAtLocation.get(dateStr);
    			Map<String, Integer> locationData = dayData.get(locationId);
    			if (locationData == null) {
    				locationData = new HashMap<String, Integer>();
    				dayData.put(locationId, locationData);
    			}
    			locationData.put(day+month+year, numAnswered == null ? 0 : numAnswered);
    		}
    		
    		displayKeys.put(year, year.toString());
    		displayKeys.put(month+year, month);
    		displayKeys.put(day+month+year, day.toString());
    		
    		if (schema.getFrequency() == Frequency.MONTHLY) {
    			cal.add(Calendar.MONTH, 1);
    		}
    		else if (schema.getFrequency() == Frequency.DAILY) {
    			cal.add(Calendar.DATE, 1);
    		}
    		else {
    			throw new RuntimeException("Unable to handle a report with frequency: " + schema.getFrequency());
    		}
    	}
    	
    	map.addAttribute("schema", schema);
    	map.addAttribute("yearIncrement", yearIncrement);
    	map.addAttribute("monthIncrement", monthIncrement);
    	map.addAttribute("numQuestions", schema.getTotalNumberOfQuestions());
    	map.addAttribute("yearCols", yearCols);
    	map.addAttribute("monthCols", monthCols);
    	map.addAttribute("dayCols", dayCols);
    	map.addAttribute("dayData", dayData);
    	map.addAttribute("displayKeys", displayKeys);
    	map.addAttribute("questionsAnswered", questionsAnswered);
    	map.addAttribute("locations", Context.getLocationService().getAllLocations());
    }
}