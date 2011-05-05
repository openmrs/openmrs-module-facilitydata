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
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.DateUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/facilitydata/report.form")
public class FacilityDataReportFormController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), false));
    }
    // a map with the UUID for the corresponding as the key and the FacilityDataValue instance as the value.
    public Map<String,FacilityDataValue> getValues(FacilityDataFormSchema schema, FacilityDataReport formData) {
        Map<String, FacilityDataValue> values = new HashMap<String, FacilityDataValue>();
        for (FacilityDataValue value : formData.getValues()) {
            for (FacilityDataFormSection section : schema.getSections())
                for (FacilityDataFormQuestion question : section.getQuestions()) {
                    if (question.getQuestion().getUuid().equals(value.getQuestion().getUuid()))
                        values.put(question.getQuestion().getUuid(), value);
                }
        }
        return values;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String homepage(ModelMap map, @RequestParam(required=true)Integer id,HttpServletRequest request) throws ParseException {
        if(Context.isAuthenticated()) {
            FacilityDataFormSchema schema = Context.getService(FacilityDataService.class).getFacilityDataFormSchema(id);
            map.addAttribute("schema", schema);
            Date startDate = Context.getDateFormat().parse(request.getParameter("startDate"));
            Date endDate = Context.getDateFormat().parse(request.getParameter("endDate"));
            Location location = Context.getLocationService().getLocation(Integer.parseInt(request.getParameter("site")));
            FacilityDataReport formData = Context.getService(FacilityDataService.class).getReport(schema,startDate,endDate, location);
            map.addAttribute("values",getValues(schema,formData));
        }
        return "/module/facilitydata/reportForm";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String saveReport(ModelMap map, @RequestParam("startDate") Date startDate,
                             @RequestParam("endDate") Date endDate,
                             HttpServletRequest request, HttpServletResponse response) throws ParseException {
        Location site = Context.getLocationService().getLocation(Integer.parseInt(request.getParameter("site")));
        FacilityDataFormSchema schema = (FacilityDataFormSchema)map.get("schema");
        Map<String,String[]> parameterMap = request.getParameterMap();
        // filter out the questions -- we only need them, we have site, frequency, and the validity period.
        Map<String, String[]> questions = new HashMap<String, String[]>(); // TODO: Implement this
        FacilityDataReport formData = Context.getService(FacilityDataService.class).getReport(schema,startDate,endDate,site);
        //TODO: look to see if this can be cleaned up more.
        //TODO: validate! 
        for(Map.Entry<String, String[]> entry : questions.entrySet()) {
            String uuid = entry.getKey().substring("question.".length()); // will always pass -- no need for extra checks. 
            FacilityDataQuestion question = Context.getService(FacilityDataService.class).getQuestionByUUID(uuid);
            FacilityDataValue value = null;
            for(FacilityDataValue dataValue : formData.getValues()) {
                if(dataValue.getQuestion().getUuid().equals(question.getUuid())) {
                    value = dataValue;
                    break; // look no further
                }
            }
            // TODO: Fix Context.getService(FacilityDataService.class).processReportAnswers(question, value, answer, comment, site, startDate, endDate);
            map.addAttribute("values", getValues(schema, formData));
        }
        return String.format("redirect:report.form?id=%d&startDate=%s&endDate=%s&site=%s",
                schema.getId(), Context.getDateFormat().format(startDate),
                Context.getDateFormat().format(endDate),request.getParameter("site"));
    }
}