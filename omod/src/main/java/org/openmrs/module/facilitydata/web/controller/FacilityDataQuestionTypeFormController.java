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

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.model.enums.DocumentType;
import org.openmrs.module.facilitydata.propertyeditor.FacilityDataQuestionTypeEditor;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.validator.FacilityDataQuestionTypeValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/module/facilitydata/questionTypeForm.form")
public class FacilityDataQuestionTypeFormController {
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FacilityDataQuestionType.class, new FacilityDataQuestionTypeEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public void viewForm(@RequestParam(required = false) Integer id, ModelMap map,
    		             @RequestParam(required = false) Class<? extends FacilityDataQuestionType> dataType) throws Exception {

        map.addAttribute("questionType", getQuestionType(id, dataType));
        map.addAttribute("optionBreakdown", getService().getCodedOptionBreakdown());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveQuestionType(HttpServletRequest request, ModelMap map,
    						 @RequestParam(required = false) Integer id,
    						 @RequestParam(required = true) String name,
    						 @RequestParam(required = false) String description,
    						 @RequestParam(required = false) Class<? extends FacilityDataQuestionType> dataType,
							 @RequestParam(required = false) String fieldStyle,
    						 @RequestParam(required = false) Double minValue,
    						 @RequestParam(required = false) Double maxValue,
    						 @RequestParam(required = false) Boolean allowDecimals,
                             @RequestParam(required = false) Integer[] optionId,
                             @RequestParam(required = false) String[] optionName,
                             @RequestParam(required = false) String[] optionCode,
                             @RequestParam(required = false) String[] optionDescription,
                             @RequestParam(required = false) String questionText,
						     @RequestParam(required = false) String documentType   ) throws Exception {
    	
    	FacilityDataQuestionType questionType = getQuestionType(id, dataType);
    	questionType.setName(name);
    	questionType.setDescription(description);
		questionType.setFieldStyle(fieldStyle);
    	
    	if (questionType instanceof NumericFacilityDataQuestionType) {
    		NumericFacilityDataQuestionType numericType = (NumericFacilityDataQuestionType) questionType;
    		numericType.setMinValue(minValue);
    		numericType.setMaxValue(maxValue);
    		numericType.setAllowDecimals(allowDecimals != null && allowDecimals == Boolean.TRUE);
    	}
    	else if(questionType instanceof FreeTextFacilityDataQuestionType) {
			FreeTextFacilityDataQuestionType freeTextType=(FreeTextFacilityDataQuestionType)questionType;
			freeTextType.setQuestionText(questionText);
		}
		else if(questionType instanceof DocumentTypeFacilityDataQuestionType) {
			DocumentTypeFacilityDataQuestionType documentTypeQuestion=(DocumentTypeFacilityDataQuestionType)questionType;
			documentTypeQuestion.setDocumentType(DocumentType.valueOf(documentType));
		}
    	else if (questionType instanceof CodedFacilityDataQuestionType) {
    		CodedFacilityDataQuestionType codedType = (CodedFacilityDataQuestionType) questionType;

        	Map<Integer, Integer> codedOptionBreakdown = getService().getCodedOptionBreakdown();
        	Date now = new Date();
			List<Integer> passedOptionIds = Arrays.asList(optionId);

			// Get existing options from the question and clear the question out
			Map<Integer, FacilityDataCodedOption> existingOptions = new HashMap<Integer, FacilityDataCodedOption>();
			for (FacilityDataCodedOption option : codedType.getOptions()) {
				existingOptions.put(option.getId(), option);
			}
			codedType.getOptions().clear();

			// Go through and retire or delete options that have been removed
			List<Integer> needRetiring = new ArrayList<Integer>();
			for (Iterator<Integer> existingOptionIdIterator = existingOptions.keySet().iterator(); existingOptionIdIterator.hasNext();) {
				Integer existingOptionId = existingOptionIdIterator.next();
				if (!passedOptionIds.contains(existingOptionId)) { // In this case we need to delete or retire
					Integer numValues = codedOptionBreakdown.get(existingOptionId);
					if (numValues == null || numValues.intValue() == 0) { // If no values are saved for it, we can delete
						existingOptionIdIterator.remove();
					}
					else { // If values exist for it, we have to retire
						needRetiring.add(existingOptionId);
					}
				}
			}

        	// Now add the passed in options using the new order

			for (int i=0; i<passedOptionIds.size(); i++) {
				if (optionCode[i].length() > 0 && optionName[i].length() > 0) {
					Integer optId = passedOptionIds.get(i);
					FacilityDataCodedOption codedOption = existingOptions.get(optId);
					if (codedOption == null) {
						codedOption = new FacilityDataCodedOption();
					}
					codedOption.setName(optionName[i]);
					codedOption.setCode(optionCode[i]);
					codedOption.setDescription(optionDescription[i]);
					codedOption.setRetired(false);
					codedOption.setRetiredBy(null);
					codedOption.setDateRetired(null);
					codedOption.setQuestionType(codedType);
					//codedType.addFacilityDataCodedOption(codedOption);
					codedType.getOptions().add(codedOption);
				}
        	}

			for (Integer retiredId : needRetiring) {
				FacilityDataCodedOption option = existingOptions.get(retiredId);
				option.setRetired(true);
				option.setRetiredBy(Context.getAuthenticatedUser());
				option.setDateRetired(now);
				//codedType.addFacilityDataCodedOption(option);
				codedType.getOptions().add(option);
			}
    		
    	}
    	
    	BindingResult result = new BeanPropertyBindingResult(questionType, "questionType");
        new FacilityDataQuestionTypeValidator().validate(questionType, result);
        if (result.hasErrors()) {
            return "/module/facilitydata/questionTypeForm";
        }
        Context.getService(FacilityDataService.class).saveQuestionType(questionType);
        return "redirect:questionType.list";
    }

    public FacilityDataService getService() {
    	return Context.getService(FacilityDataService.class);
    }
    
    public FacilityDataQuestionType getQuestionType(Integer id, Class<? extends FacilityDataQuestionType> type) throws Exception {
        if (id == null) {
        	return type.newInstance();
            
        }
        return getService().getQuestionType(id);
    }
}