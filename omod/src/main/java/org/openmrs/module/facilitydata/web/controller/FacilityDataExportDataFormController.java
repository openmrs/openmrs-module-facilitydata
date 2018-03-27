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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataForm;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataFormEditor;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.ExcelSheetHelper;
import org.openmrs.module.facilitydata.util.ExcelStyleHelper;
import org.openmrs.module.facilitydata.util.FacilityDataConstants;
import org.openmrs.module.facilitydata.util.FacilityDataQuery;
import org.openmrs.propertyeditor.LocationEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/module/facilitydata/exportData.form")
public class FacilityDataExportDataFormController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(FacilityDataForm.class, new FacilityDataFormEditor());
    	binder.registerCustomEditor(FacilityDataQuestion.class, new FacilityDataQuestionEditor());
	 	binder.registerCustomEditor(Location.class, new LocationEditor());
	 	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true));
    }
    
    @ModelAttribute("query")
    public FacilityDataQuery getQuery() {
		return new FacilityDataQuery();
    }
    
    @ModelAttribute("forms")
    public List<FacilityDataForm> getForms() {
		return Context.getService(FacilityDataService.class).getAllFacilityDataForms();
    }
    
    @ModelAttribute("questions")
    public List<FacilityDataQuestion> getQuestions() {
		return Context.getService(FacilityDataService.class).getAllQuestions();
    }
    
    @ModelAttribute("locations")
    public List<Location> getLocations() {
		return FacilityDataConstants.getSupportedFacilities();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(ModelMap map, HttpServletRequest request, @ModelAttribute("query") FacilityDataQuery query) {	
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void exportData(ModelMap map, @ModelAttribute("query") FacilityDataQuery query,
    		               HttpServletResponse response) throws IOException {
    	
        HSSFWorkbook wb = new HSSFWorkbook();
        ExcelStyleHelper styleHelper = new ExcelStyleHelper(wb);
        
        // First Create the sheet that contains the data
        HSSFSheet sheet = wb.createSheet("export");
        ExcelSheetHelper helper = new ExcelSheetHelper(sheet);
        
        // Headers
        HSSFCellStyle headerStyle = styleHelper.getStyle("bold,border=bottom");
        helper.addCell("Form", headerStyle);
        helper.addCell("Schema", headerStyle);
        helper.addCell("Section", headerStyle);
        helper.addCell("Question Name", headerStyle);
        helper.addCell("Question Number", headerStyle);
        helper.addCell("Question Label", headerStyle);
        helper.addCell("Facility", headerStyle);
        helper.addCell("From Date", headerStyle);
        helper.addCell("To Date", headerStyle);
        helper.addCell("Value Numeric", headerStyle);
        helper.addCell("Value Coded", headerStyle);
        helper.addCell("Comments", headerStyle);
        helper.addCell("Date Entered", headerStyle);
        helper.addCell("Entered By", headerStyle);
        helper.nextRow();
        
        // Rows
        HSSFCellStyle dateStyle = styleHelper.getStyle("date");
        List<FacilityDataValue> values = Context.getService(FacilityDataService.class).evaluateFacilityDataQuery(query);
        for (FacilityDataValue v : values) {
        	helper.addCell(v.getQuestion().getSection().getSchema().getForm().getName());
        	helper.addCell(v.getQuestion().getSection().getSchema().getName());
        	helper.addCell(v.getQuestion().getSection().getName());
        	helper.addCell(v.getQuestion().getQuestion().getName());
        	helper.addCell(v.getQuestion().getQuestionNumber());
        	helper.addCell(v.getQuestion().getName());
        	helper.addCell(v.getFacility().getName());
        	helper.addCell(v.getFromDate(), dateStyle);
        	helper.addCell(v.getToDate(), dateStyle);
        	helper.addCell(v.getValueNumeric());
        	helper.addCell(v.getValueCoded());
        	helper.addCell(v.getComments());
        	helper.addCell(v.getDateCreated(), dateStyle);
        	helper.addCell(v.getCreator().getPersonName().getFullName());
        	helper.nextRow();
        }
        
        // Now Create the sheet that contains the export query
        HSSFSheet metadataSheet = wb.createSheet("query");
        ExcelSheetHelper metadataHelper = new ExcelSheetHelper(metadataSheet);
        HSSFCellStyle labelStyle = styleHelper.getStyle("bold");
        if (query.getForm() != null) {
        	metadataHelper.addCell("Form", labelStyle);
        	metadataHelper.addCell(query.getForm().getName());
        	metadataHelper.nextRow();
        }
        if (query.getQuestion() != null) {
        	metadataHelper.addCell("Question", labelStyle);
        	metadataHelper.addCell(query.getQuestion().getName());
        	metadataHelper.nextRow();
        }
        if (query.getFacility() != null) {
        	metadataHelper.addCell("Facility", labelStyle);
        	metadataHelper.addCell(query.getFacility().getName());
        	metadataHelper.nextRow();
        }
        if (query.getFromDate() != null) {
        	metadataHelper.addCell("From Date", labelStyle);
        	metadataHelper.addCell(query.getFromDate(), dateStyle);
        	metadataHelper.nextRow();
        }
        if (query.getToDate() != null) {
        	metadataHelper.addCell("To Date", labelStyle);
        	metadataHelper.addCell(query.getToDate(), dateStyle);
        	metadataHelper.nextRow();
        }
        if (query.getEnteredFromDate() != null) {
        	metadataHelper.addCell("Entered From Date", labelStyle);
        	metadataHelper.addCell(query.getEnteredFromDate(), dateStyle);
        	metadataHelper.nextRow();
        }
        if (query.getEnteredToDate() != null) {
        	metadataHelper.addCell("Entered To Date", labelStyle);
        	metadataHelper.addCell(query.getEnteredToDate(), dateStyle);
        	metadataHelper.nextRow();
        }
        metadataHelper.nextRow();
    	metadataHelper.addCell("Requested By", labelStyle);
    	metadataHelper.addCell(Context.getAuthenticatedUser().getPersonName().getFullName());
    	metadataHelper.nextRow();
    	metadataHelper.addCell("Requested On", labelStyle);
    	metadataHelper.addCell(new Date(), dateStyle);
        
        // Export Data
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
		wb.write(response.getOutputStream());
    }
}