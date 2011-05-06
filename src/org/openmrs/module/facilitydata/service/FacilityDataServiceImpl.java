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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.service.db.FacilityDataDAO;

/**
 * Core implementation of the FacilityDataService
 */
public class FacilityDataServiceImpl extends BaseOpenmrsService implements FacilityDataService {

	//***** PROPERTIES *****
	
    private FacilityDataDAO dao;

    //***** INSTANCE METHODS *****
    
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
	 * @see FacilityDataService#deleteFacilityDataFormSchema(FacilityDataFormSchema)
	 */
	public void deleteFacilityDataFormSchema(FacilityDataFormSchema formSchema) {
		dao.deleteFacilityDataFormSchema(formSchema);
	}

	/**
	 * @see FacilityDataService#saveCodedQuestionType(FacilityDataQuestionType)
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
	 * @see FacilityDataService#unretireQuestion(FacilityDataQuestionType)
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
	 * @see FacilityDataService#retireFacilityDataQuestion(FacilityDataQuestion, String)
	 */
	public FacilityDataQuestion retireQuestion(FacilityDataQuestion question, String reason) {
		question.setRetired(true);
		question.setRetireReason(reason);
		return saveQuestion(question);
	}

	/**
	 * @see FacilityDataService#unretireFacilityDataQuestion(FacilityDataQuestion)
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
	 * @see FacilityDataService#unvoidFacilityDataFormValue(FacilityDataValue)
	 */
	public FacilityDataValue unvoidFacilityDataValue(FacilityDataValue value) {
		value.setVoided(false);
		value.setDateVoided(null);
		value.setVoidReason(null);
		return saveFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#deleteFacilityValue(FacilityDataValue)
	 */
	public void deleteFacilityDataValue(FacilityDataValue value) {
		dao.deleteFacilityDataValue(value);
	}

	/**
	 * @see FacilityDataService#saveReport(FacilityDataReport)
	 */
	public FacilityDataReport saveReport(FacilityDataReport report) {
		for (FacilityDataValue v : report.getValues()) {
			v = dao.saveFacilityDataValue(v);
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
        r.setValues(new HashSet<FacilityDataValue>(l));
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
	 * @see FacilityDataService#getNumberOfQuestionsAnswered(FacilityDataFormSchema, Date, Date)
	 */
	public Map<Integer, Map<String, Integer>> getNumberOfQuestionsAnswered(FacilityDataFormSchema schema, Date fromDate, Date toDate) {
		return dao.getNumberOfQuestionsAnswered(schema, fromDate, toDate);
	}
	
	//***** PROPERTY ACCESS *****

	/**
	 * dao the dao to set
	 */
    public void setDao(FacilityDataDAO dao) {
        this.dao = dao;
    }
}