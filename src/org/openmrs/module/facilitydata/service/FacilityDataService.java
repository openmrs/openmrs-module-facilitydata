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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openmrs.Location;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.facilitydata.model.FacilityDataForm;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.util.FacilityDataConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * Core Service for FacilityData
 */
public interface FacilityDataService extends OpenmrsService {
	
    /**
     * Saves a form
     * @param form
     * @return the saved form
     * @should save and return the specified form
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataForm saveFacilityDataForm(FacilityDataForm form);

    /**
     * Get a specified form
     * @param id the id of the form to retrieve
     * @return the form or null if it does not exist.
     * @should return the form with the passed id or null if it doesn't exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataForm getFacilityDataForm(Integer id);

    /**
     * Gets a specified form using its {@link UUID}.
     * @param uuid the UUID of the form to retrieve.
     * @return the form or null if it does exist.
     * @should return the form with the specified uuid or null if it doesn't exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataForm getFacilityDataFormByUUID(String uuid);

    /**
     * Get all Form Schemas in the system.
     * @return a {@link List} containing all forms.
     * @throws IllegalArgumentException if passed a null parameter
     * @should get all forms
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public List<FacilityDataForm> getAllFacilityDataForms();

    /**
     * Retire a form
     * @param form the form
     * @should retire the given form then save it.
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataForm retireFacilityDataForm(FacilityDataForm form, String reason);

    /**
     * Un-retire a form
     * @param form the form
     * @should unretire the given form then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataForm unretireFacilityDataForm(FacilityDataForm form);

    /**
     * Delete a form
     * @param form the form to delete.
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the specified form
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public void deleteFacilityDataForm(FacilityDataForm form);

    /**
     * Saves a form schema
     * @param formSchema
     * @return the saved schema
     * @should save and return the specified formSchema
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema);

    /**
     * Get a specified form schema
     * @param id the id of the schema to retrieve
     * @return the schema or null if it does not exist.
     * @should return the schema with the passed id or null if it doesn't exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataFormSchema getFacilityDataFormSchema(Integer id);

    /**
     * Gets a specified form schema using its {@link UUID}.
     * @param uuid the UUID of the schema to retrieve.
     * @return the schema or null if it does exist.
     * @should return the schema with the specified uuid or null if it doesn't exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid);

    /**
     * Get all Form Schemas in the system.
     * @return a {@link List} containing all schemas.
     * @throws IllegalArgumentException if passed a null parameter
     * @should get all schemas
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public List<FacilityDataFormSchema> getAllFacilityDataFormSchemas();

    /**
     * Retire a schema
     * @param schema the schema
     * @should retire the given schema then save it.
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataFormSchema retireFacilityDataFormSchema(FacilityDataFormSchema schema, String reason);

    /**
     * Un-retire a schema
     * @param schema the schema
     * @should unretire the given schema then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataFormSchema unretireFacilityDataFormSchema(FacilityDataFormSchema schema);

    /**
     * Delete a schema
     * @param formSchema the schema to delete.
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the specified schema
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public void deleteFacilityDataFormSchema(Integer schemaId);

    /**
     * Save a <code>FacilityDataQuestionType</code> to the database.
     * @param question the <code>FacilityDataQuestionType</code> to be saved
     * @return the <code>FacilityDataQuestionType</code> which was just saved.
     * @should save and return the passed <code>FacilityDataQuestionType</code>
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestionType saveQuestionType(FacilityDataQuestionType questionType);

    /**
     * Get a <code>FacilityDataQuestionType</code>
     * @param id
     * @return the <code>FacilityDataQuestionType</code> or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the <code>FacilityDataQuestionType</code> with the passed id or null if it does not exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataQuestionType getQuestionType(Integer id);

    /**
     * Get a <code>FacilityDataQuestionType</code> using its UUID
     * @param uuid
     * @return the <code>FacilityDataQuestionType</code> pertaining to that specific UUID.
     * @throws IllegalArgumentException if passed a null parameter or empty String
     * @should return the <code>FacilityDataQuestionType</code> with the specified uuid or null if it does not exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataQuestionType getQuestionTypeByUUID(String uuid);

    /**
     * Get all FacilityDataQuestionTypes
     * @return a list containing all FacilityDataQuestionType
     * @throws IllegalArgumentException if passed a null parameter
     * @should return a list of all FacilityDataQuestionType.
     */
    @Transactional
    @Authorized({FacilityDataConstants.VIEW})
    public List<FacilityDataQuestionType> getAllQuestionTypes();

    /**
     * Retire a FacilityDataQuestionType
     * @param FacilityDataQuestionType
     * @should retire a FacilityDataQuestionType then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestionType retireQuestionType(FacilityDataQuestionType questionType, String reason);

    /**
     * Un-retire a FacilityDataQuestionType
     * @param questionType
     * @should unretire a FacilityDataQuestionType then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestionType unretireQuestionType(FacilityDataQuestionType questionType);

    /**
     * Delete a <code>FacilityDataQuestionType</code>
     * @param question the <code>FacilityDataQuestionType</code> object to be deleted
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the <code>FacilityDataQuestionType</code> object
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public void deleteQuestionType(FacilityDataQuestionType questionType);
    
    /**
     * Save a <code>FacilityDataQuestion</code> to the database.
     * @param question the <code>FacilityDataQuestion</code> to be saved
     * @return the <code>FacilityDataQuestion</code> which was just saved.
     * @should save and return the passed <code>FacilityDataQuestion</code>
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestion saveQuestion(FacilityDataQuestion question);

    /**
     * Get a <code>FacilityDataQuestion</code>
     * @param id
     * @return the <code>FacilityDataQuestion</code> or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the <code>FacilityDataQuestion</code> with the passed id or null if it does not exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataQuestion getQuestion(Integer id);

    /**
     * Get a question using its UUID
     * @param uuid
     * @return the question pertaining to that specific UUID.
     * @throws IllegalArgumentException if passed a null parameter or empty String
     * @should return the question with the specified uuid or null if it does not exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataQuestion getQuestionByUUID(String uuid);

    /**
     * Get all questions
     * @return a list containing all questions
     * @throws IllegalArgumentException if passed a null parameter
     * @should return a list of all sections.
     */
    @Transactional
    @Authorized({FacilityDataConstants.VIEW})
    public List<FacilityDataQuestion> getAllQuestions();

    /**
     * Retire a question
     * @param question
     * @should retire a question then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestion retireQuestion(FacilityDataQuestion question, String reason);

    /**
     * Un-retire a question
     * @param question
     * @should unretire a question then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public FacilityDataQuestion unretireQuestion(FacilityDataQuestion question);

    /**
     * Delete a <code>FacilityDataQuestion</code>
     * @param question the <code>FacilityDataQuestion</code> object to be deleted
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the <code>FacilityDataQuestion</code> object
     */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public void deleteQuestion(FacilityDataQuestion question);

    /**
     * Save form data to the database
     * @param value the <code>FacilityDataValue</code> object to save.
     * @return the saved object
     * @throws IllegalArgumentException if passed a null parameter
     * @should save the value to the database
     */
    @Transactional
    @Authorized({FacilityDataConstants.ENTER})
    public FacilityDataValue saveFacilityDataValue(FacilityDataValue value);

    /**
     * Get a <code>FacilityDataValue</code> object with a given id.
     * @param id
     * @return an object with that id or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the value with the passed id or null if it does not exist
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataValue getFacilityDataValue(Integer id);

    /**
     * Void a FacilityDataValue
     * @param value
     * @should void the passed value and then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.ENTER})
    public FacilityDataValue voidFacilityDataValue(FacilityDataValue value, String reason);

    /**
     * Un-void a FacilityDataValue
     * @param value
     * @should unvoid the passed value and then save it
     */
    @Transactional
    @Authorized({FacilityDataConstants.ENTER})
    public FacilityDataValue unvoidFacilityDataValue(FacilityDataValue value);

    /** 
    * Delete a value from the database.
    * @param value the object containing the form data entered for this report.
    * @throws IllegalArgumentException if passed a null parameter
    * @should delete the given object from the database
    */
    @Transactional
    @Authorized({FacilityDataConstants.MANAGE})
    public void deleteFacilityDataValue(FacilityDataValue value);

    /**
     * Used to save the data that is entered into the reports.
     * @param question the associated question being answered.
     * @param value the existing answer -- if any.
     * @param val the value. 
     * @param comments the comment such as why, etc.
     * @param location the facility
     * @param fromDate the start of the period.
     * @param toDate the end of the period.
     * @should save the passed report
     */
    @Transactional
    @Authorized({FacilityDataConstants.ENTER})
    public FacilityDataReport saveReport(FacilityDataReport report);

    /**
     * Utility method to get all values associated with each question in a {@link FacilityDataFormSchema}.
     * @param schema the schema
     * @param startDate
     * @param endDate
     * @param location  the location of the clinic
     * @return an instance of {@link FacilityDataReport}.
     * @should get the report given the passed parameters
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public FacilityDataReport getReport(FacilityDataFormSchema schema, Date startDate, Date endDate, Location location);
    
    /**
     * @return a Map from FacilityDataCodedOption id to a count of answers for that option
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public Map<Integer, Integer> getCodedOptionBreakdown();
    
    /**
     * @return a Map from FacilityDataFormQuestion id to a count of values for that FormQuestion
     */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
    public Map<Integer, Integer> getFormQuestionBreakdown();
    
	/**
	 * @return the most recent start date of the value in the database for the passed schema
	 */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
	public Date getMaxEnteredStartDateForSchema(FacilityDataFormSchema schema);
    
	/**
	 * @return a Map from Location Id to a Map of Date String to Integer, 
	 * where Date String is the start date of a value, and Integer is the count of values
	 */
    @Transactional(readOnly = true)
    @Authorized({FacilityDataConstants.VIEW})
	public Map<Integer, Map<String, Integer>> getNumberOfQuestionsAnswered(FacilityDataForm form, Date fromDate, Date toDate);

}
