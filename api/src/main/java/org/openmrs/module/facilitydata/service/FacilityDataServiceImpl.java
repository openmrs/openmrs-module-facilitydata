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
package org.openmrs.module.facilitydata.service;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.service.db.FacilityDataDAO;
import org.openmrs.module.facilitydata.util.FacilityDataQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Core implementation of the FacilityDataService
 */
public class FacilityDataServiceImpl extends BaseOpenmrsService implements FacilityDataService {

	//***** PROPERTIES *****
	
    private FacilityDataDAO dao;

    //***** INSTANCE METHODS *****
    
    /**
     * @see FacilityDataService#saveFacilityDataF`orm(FacilityDataForm)
     */
    public FacilityDataForm saveFacilityDataForm(FacilityDataForm formSchema) {
        return dao.saveFacilityDataForm(formSchema);
    }

	/**
	 * @see FacilityDataService#getFacilityDataForm(Integer)
	 */
	public FacilityDataForm getFacilityDataForm(Integer id) {
		return dao.getFacilityDataForm(id);
	}

	/**
	 * @see FacilityDataService#getFacilityDataFormByUUID(String)
	 */
	public FacilityDataForm getFacilityDataFormByUUID(String uuid) {
		return getFacilityDataFormByUUID(uuid);
	}

	/**
	 * @see FacilityDataService#getAllFacilityDataForms()
	 */
	public List<FacilityDataForm> getAllFacilityDataForms() {
		return dao.getAllFacilityDataForms();
	}

	/**
	 * @see FacilityDataService#retireFacilityDataForm(FacilityDataForm, String)
	 */
	public FacilityDataForm retireFacilityDataForm(FacilityDataForm schema, String reason) {
		schema.setRetired(true);
		schema.setRetireReason(reason);
		return saveFacilityDataForm(schema);
	}

	/**
	 * @see FacilityDataService#unretireFacilityDataForm(FacilityDataForm)
	 */
	public FacilityDataForm unretireFacilityDataForm(FacilityDataForm schema) {
		schema.setRetired(false);
		schema.setDateRetired(new Date());
		schema.setRetireReason(null);
		return saveFacilityDataForm(schema);
	}

	/**
	 * @see FacilityDataService#deleteFacilityDataForm(FacilityDataForm)
	 */
	public void deleteFacilityDataForm(FacilityDataForm formSchema) {
		dao.deleteFacilityDataForm(formSchema);
	}
    
    /**
     * @see FacilityDataService#saveFacilityDataFormSchema(FacilityDataFormSchema)
     */
    public FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema) {
        return dao.saveFacilityDataFormSchema(formSchema);
    }

	/**
	 * @see FacilityDataService#getFacilityDataFormSchema(Integer)
	 */
	public FacilityDataFormSchema getFacilityDataFormSchema(Integer id) {
		return dao.getFacilityDataFormSchema(id);
	}

	/**
	 * @see FacilityDataService#getFacilityDataFormSchemaByUUID(String)
	 */
	public FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid) {
		return getFacilityDataFormSchemaByUUID(uuid);
	}

	/**
	 * @see FacilityDataService#getAllFacilityDataFormSchemas()
	 */
	public List<FacilityDataFormSchema> getAllFacilityDataFormSchemas() {
		return dao.getAllFacilityDataFormSchemas();
	}

	/**
	 * @see FacilityDataService#retireFacilityDataFormSchema(FacilityDataFormSchema, String)
	 */
	public FacilityDataFormSchema retireFacilityDataFormSchema(FacilityDataFormSchema schema, String reason) {
		schema.setRetired(true);
		schema.setRetireReason(reason);
		return saveFacilityDataFormSchema(schema);
	}

	/**
	 * @see FacilityDataService#unretireFacilityDataFormSchema(FacilityDataFormSchema)
	 */
	public FacilityDataFormSchema unretireFacilityDataFormSchema(FacilityDataFormSchema schema) {
		schema.setRetired(false);
		schema.setDateRetired(new Date());
		schema.setRetireReason(null);
		return saveFacilityDataFormSchema(schema);
	}

	/**
	 * @see FacilityDataService#deleteFacilityDataFormSchema(Integer)
	 */
	public void deleteFacilityDataFormSchema(Integer schemaId) {
		dao.deleteFacilityDataFormSchema(schemaId);
	}

	/**
	 * @see FacilityDataService#saveQuestionType(FacilityDataQuestionType)
	 */
	public FacilityDataQuestionType saveQuestionType(FacilityDataQuestionType questionType) {
		return dao.saveQuestionType(questionType);
	}

	/**
	 * @see FacilityDataService#getQuestionType(java.lang.Integer)
	 */
	public FacilityDataQuestionType getQuestionType(Integer id) {
		return dao.getQuestionType(id);
	}

	/**
	 * @see FacilityDataService#getQuestionTypeByUUID(String)
	 */
	public FacilityDataQuestionType getQuestionTypeByUUID(String uuid) {
		return dao.getQuestionTypeByUUID(uuid);
	}

	/**
	 * @see FacilityDataService#getAllQuestionTypes()
	 */
	public List<FacilityDataQuestionType> getAllQuestionTypes() {
		return dao.getAllQuestionTypes();
	}

	/**
	 * @see FacilityDataService#retireQuestionType(FacilityDataQuestionType, String)
	 */
	public FacilityDataQuestionType retireQuestionType(FacilityDataQuestionType questionType, String reason) {
		questionType.setRetired(true);
		questionType.setRetireReason(reason);
		return saveQuestionType(questionType);
	}

	/**
	 * @see FacilityDataService#unretireQuestionType(FacilityDataQuestionType)
	 */
	public FacilityDataQuestionType unretireQuestionType(FacilityDataQuestionType questionType) {
		questionType.setRetired(false);
		questionType.setDateRetired(new Date());
		questionType.setRetireReason(null);
		return saveQuestionType(questionType);
	}

	/**
	 * @see FacilityDataService#deleteQuestionType(FacilityDataQuestionType)
	 */
	public void deleteQuestionType(FacilityDataQuestionType questionType) {
		dao.deleteQuestionType(questionType);
	}

	/**
	 * @see FacilityDataService#saveQuestion(FacilityDataQuestion)
	 */
	public FacilityDataQuestion saveQuestion(FacilityDataQuestion question) {
		return dao.saveQuestion(question);
	}

	/**
	 * @see FacilityDataService#getQuestion(Integer)
	 */
	public FacilityDataQuestion getQuestion(Integer id) {
		return dao.getQuestion(id);
	}

	/**
	 * @see FacilityDataService#getQuestionByUUID(String)
	 */
	public FacilityDataQuestion getQuestionByUUID(String uuid) {
		return dao.getQuestionByUUID(uuid);
	}

	/**
	 * @see FacilityDataService#getAllQuestions()
	 */
	public List<FacilityDataQuestion> getAllQuestions() {
		return dao.getAllQuestions();
	}

	/**
	 * @see FacilityDataService#retireQuestion(FacilityDataQuestion, String)
	 */
	public FacilityDataQuestion retireQuestion(FacilityDataQuestion question, String reason) {
		question.setRetired(true);
		question.setRetireReason(reason);
		return saveQuestion(question);
	}

	/**
	 * @see FacilityDataService#unretireQuestion(FacilityDataQuestion)
	 */
	public FacilityDataQuestion unretireQuestion(FacilityDataQuestion question) {
		question.setRetired(false);
		question.setDateRetired(null);
		question.setRetireReason(null);
		return saveQuestion(question);
	}

	/**
	 * @see FacilityDataService#deleteQuestion(FacilityDataQuestion)
	 */
	public void deleteQuestion(FacilityDataQuestion question) {
		dao.deleteQuestion(question);
	}

	/**
	 * @see FacilityDataService#saveFacilityDataValue(FacilityDataValue)
	 */
	public FacilityDataValue saveFacilityDataValue(FacilityDataValue value) {
		return dao.saveFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#getFacilityDataValue(Integer)
	 */
	public FacilityDataValue getFacilityDataValue(Integer id) {
		return dao.getFacilityDataValue(id);
	}

	/**
	 * @see FacilityDataService#voidFacilityDataValue(FacilityDataValue, String)
	 */
	public FacilityDataValue voidFacilityDataValue(FacilityDataValue value, String reason) {
		value.setVoided(true);
		value.setDateVoided(new Date());
		value.setVoidReason(reason);
		return saveFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#unvoidFacilityDataValue(FacilityDataValue)
	 */
	public FacilityDataValue unvoidFacilityDataValue(FacilityDataValue value) {
		value.setVoided(false);
		value.setDateVoided(null);
		value.setVoidReason(null);
		return saveFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#deleteFacilityDataValue(FacilityDataValue)
	 */
	public void deleteFacilityDataValue(FacilityDataValue value) {
		dao.deleteFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#saveReport(FacilityDataReport)
	 */
	public FacilityDataReport saveReport(FacilityDataReport report) {
		for (FacilityDataValue v : report.getValues().values()) {
			 v = Context.getService(FacilityDataService.class).saveFacilityDataValue(v);
		}
        return report;
	}

	/**
	 * @see FacilityDataService#getReport(FacilityDataFormSchema, Date, Date, Location)
	 */
	public FacilityDataReport getReport(FacilityDataFormSchema schema, Date startDate, Date endDate, Location location) {
        FacilityDataReport r = new FacilityDataReport();
        r.setSchema(schema);
        r.setStartDate(startDate);
        r.setEndDate(endDate);
        r.setLocation(location);
        List<FacilityDataValue> l = dao.getFacilityDataValues(schema, startDate, endDate, location);
        for (FacilityDataValue value : l) {
        	r.addValue(value);
        }
        return r;
	}
	
	/**
	 * @see FacilityDataService#getCodedOptionBreakdown()
	 */
	public Map<Integer, Integer> getCodedOptionBreakdown() {
		return dao.getCodedOptionBreakdown();
	}
	
	/**
	 * @see FacilityDataService#getFormQuestionBreakdown()
	 */
	public Map<Integer, Integer> getFormQuestionBreakdown() {
		return dao.getFormQuestionBreakdown();
	}
	
	/**
	 * @see FacilityDataService#getQuestionBreakdown()
	 */
	public Map<Integer, Integer> getQuestionBreakdown() {
		return dao.getQuestionBreakdown();
	}
	
	/**
	 * @see FacilityDataService#getQuestionTypeBreakdown()
	 */
	public Map<Integer, Integer> getQuestionTypeBreakdown() {
		return dao.getQuestionTypeBreakdown();
	}
	
	/**
	 * @see FacilityDataService#getMinEnteredStartDateForSchema(FacilityDataFormSchema)
	 */
	public Date getMinEnteredStartDateForSchema(FacilityDataFormSchema schema) {
		return dao.getMinOrMaxEnteredStartDateForSchema(schema, "min");
	}

	/**
	 * @see FacilityDataService#getMaxEnteredStartDateForSchema(FacilityDataFormSchema)
	 */
	public Date getMaxEnteredStartDateForSchema(FacilityDataFormSchema schema) {
		return dao.getMinOrMaxEnteredStartDateForSchema(schema, "max");
	}

	/**
	 * @see FacilityDataService#getNumberOfQuestionsAnswered(FacilityDataForm, Date, Date)
	 */
	public Map<Integer, Map<String, Integer>> getNumberOfQuestionsAnswered(FacilityDataForm form, Date fromDate, Date toDate) {
		return dao.getNumberOfQuestionsAnswered(form, fromDate, toDate);
	}
	
	/**
	 * @see FacilityDataService#getNumberOfValuesRecordedByQuestion(FacilityDataFormSchema, Location, Date, Date)
	 */
	public Map<Integer, Integer> getNumberOfValuesRecordedByQuestion(FacilityDataFormSchema schema, Location facility, Date fromDate, Date toDate) {
		return dao.getNumberOfValuesRecordedByQuestion(schema, facility, fromDate, toDate);
	}
	
	/**
	 * @see FacilityDataService#evaluateFacilityDataQuery(FacilityDataQuery)
	 */
	public List<FacilityDataValue> evaluateFacilityDataQuery(FacilityDataQuery query) {
		return dao.evaluateFacilityDataQuery(query);
	}

	@Override
	public FacilityDataCodedOption saveCodedOption(FacilityDataCodedOption facilityDataCodedOption) {
		return dao.saveCodedOption(facilityDataCodedOption);
	}

	@Override
	public FacilityDataCodedOption getCodedoptionByUuid(String s) {
		return dao.getCodedoptionByUuid(s);
	}

	@Override
	public FacilityDataFormSection saveFacilityDataFormSection(FacilityDataFormSection facilityDataFormSection) {
		return dao.saveFacilityDataFormSection(facilityDataFormSection);
	}

	@Override
	public FacilityDataFormSection getFacilityDataFormSectionByUUID(String s) {
		return dao.getFacilityDataFormSectionByUUID(s);
	}

	@Override
	public FacilityDataFormQuestion saveFacilityDataFormQuestion(FacilityDataFormQuestion facilityDataFormQuestion) {
		return dao.saveFacilityDataFormQuestion(facilityDataFormQuestion);
	}

	@Override
	public FacilityDataFormQuestion getFacilityDataFormQuestion(String s) {
		return dao.getFacilityDataFormQuestion(s);
	}

	@Override
	public FacilityDataValue getFacilityDataValueByUuid(String s) {
		return dao.getFacilityDataValueByUuid(s);
	}

	//***** PROPERTY ACCESS *****

	/**
	 * dao the dao to set
	 */
    public void setDao(FacilityDataDAO dao) {
        this.dao = dao;
    }
}