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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/module/facilitydata/formEntry.form")
public class   FacilityDataFormEntryFormController {

	protected FacilityDataService getFacilityDataService() {
		return Context.getService(FacilityDataService.class);
	}

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(FacilityDataFormSchema.class, new FacilityDataFormSchemaEditor());
	 	binder.registerCustomEditor(Location.class, new LocationEditor());
	 	binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
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
		FacilityDataReport report = getFacilityDataService().getReport(schema, fromDate, toDate, facility);
		map.addAttribute("schema", schema);
		map.addAttribute("facility", facility);
		map.addAttribute("fromDate", fromDate);
		map.addAttribute("toDate", toDate);
		map.addAttribute("viewOnly", viewOnly == Boolean.TRUE);
	    map.addAttribute("report", report);

		map.addAttribute("questionTypes", getFacilityDataService().getAllQuestionTypes());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveReport( ModelMap map,
    						  @RequestParam(required = false) FacilityDataFormSchema schema,
    						  @RequestParam(required = false) Location facility,
    						  @RequestParam(required = false) Date fromDate,
    		                  @RequestParam(required = false) Date toDate,
							  @RequestParam(required = false) MultipartFile[] documentTypeFile,
							  @RequestParam(required = false) MultipartFile[] blobFile,
    		                  HttpServletRequest request) {

    	FacilityDataReport report = getFacilityDataService().getReport(schema, fromDate, toDate, facility);
    	Date currentDate = new Date();
    	User currentUser = Context.getAuthenticatedUser();
		int documentTypeCurrentThread=0;
		int blobFileCurrentThread=0;
		for (FacilityDataFormSection section : schema.getSections()) {
    		for (FacilityDataFormQuestion question : section.getQuestions()) {
    			FacilityDataValue value = report.getValue(question);
				System.out.println("question :::: "+question.getQuestionNumber());
    			String valueCodedParam = request.getParameter("valueCoded." + question.getId());
				String valueCodedTextParam = request.getParameter("valueCodedText." + question.getId());
    			String valueNumericParam = request.getParameter("valueNumeric." + question.getId());
    			String commentsParam = request.getParameter("comments." + question.getId()); //freeText
				String valueTextParam=request.getParameter("valueText." + question.getId());
    			FacilityDataCodedOption valueCoded = null;
    			Double valueNumeric = null;
				String documentValue=null;
    			String valueText=null;
				byte[] blobValue=null;
    			if(value!=null) {
					if (value.getFacility() == null) {
						value.setFacility(facility);
					}
					if(value.getQuestion()==null){

						value.setQuestion(question);
					}
				}
				System.out.println("Document File : "+ documentTypeFile);
    			if (StringUtils.isNotBlank(valueCodedParam) || StringUtils.isNotBlank(valueCodedTextParam)) {
					System.out.println("value Coded param :::: "+valueCodedParam);
					System.out.println("value Coded Text param ::::: "+valueCodedTextParam);
					CodedFacilityDataQuestionType codedType = (CodedFacilityDataQuestionType) question.getQuestion().getQuestionType();
					if (!"AUTOCOMPLETE".equals(codedType.getFieldStyle()) || StringUtils.isNotBlank(valueCodedTextParam)) {
						valueCoded = codedType.getOptionById(Integer.parseInt(valueCodedParam));
						System.out.println("valueCoded :::: "+valueCoded);
					}
    			}
    			else if (StringUtils.isNotBlank(valueNumericParam)) {
    				valueNumeric = Double.valueOf(valueNumericParam);
    			}
    			else if(StringUtils.isNotBlank(valueTextParam)){
					valueText=valueTextParam;
				}

				else if(documentTypeFile!=null && documentTypeFile.length>0 && documentTypeCurrentThread<documentTypeFile.length){

					try {
						System.out.println("documentTypeCurrentThread :::: "+ documentTypeCurrentThread);
						System.out.println("documentTypeCurrentThread :::: "+ documentTypeCurrentThread);
						System.out.println("documentTypeCurrentThread :::: "+ documentTypeCurrentThread);
						System.out.println("documentTypeCurrentThread :::: "+ documentTypeCurrentThread);
						File convFile = new File( documentTypeFile[documentTypeCurrentThread].getOriginalFilename());
						documentTypeCurrentThread++;
						System.out.println("in try block !");
						documentTypeFile[documentTypeCurrentThread].transferTo(convFile);
						documentValue = FileUtils.readFileToString(convFile);
						System.out.println("Document ::: "+documentValue) ;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if (blobFile!=null  && blobFile.length>0 && blobFileCurrentThread<blobFile.length){
					try {
						System.out.println("blobFileCurrentThread :::: "+ blobFileCurrentThread);
						System.out.println("blobFileCurrentThread :::: "+ blobFileCurrentThread);
						System.out.println("blobFileCurrentThread :::: "+ blobFileCurrentThread);
						System.out.println("blobFileCurrentThread :::: "+ blobFileCurrentThread);
						blobValue=blobFile[blobFileCurrentThread].getBytes();
						blobFileCurrentThread++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				boolean createNew = blobValue!=null || documentValue!=null ||valueText!=null || valueCoded != null || valueNumeric != null || StringUtils.isNotBlank(commentsParam);

				//boolean createNew = valueText!=null || valueCoded != null || valueCodedTextParam!=null ||valueNumeric != null || StringUtils.isNotBlank(commentsParam);
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
    				newValue.setValueText(valueText);
					newValue.setDocumentValue(documentValue);
					newValue.setValueBlob(blobValue);
    				report.addValue(newValue);

    			}
    		}
    	}
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	getFacilityDataService().saveReport(report);
    	return String.format("redirect:formEntry.form?schema=%s&facility=%s&fromDate=%s&viewOnly=true",
    						 schema.getId(), facility.getId(), df.format(fromDate));
    }
}