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
package org.openmrs.module.facilitydata.service.db;

import org.openmrs.Location;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.util.FacilityDataQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Core Facility Data DB Layer
 */
public interface FacilityDataDAO {
	
    /**
     * Saves a form
     * @param form
     * @return the saved form
     * @should save and return the specified form
     */
    public FacilityDataForm saveFacilityDataForm(FacilityDataForm form);

    /**
     * Get a specified form
     * @param id the id of the form to retrieve
     * @return the form or null if it does not exist.
     * @should return the form with the passed id or null if it doesn't exist
     */
    public FacilityDataForm getFacilityDataForm(Integer id);

    /**
     * Gets a specified form using its {@link UUID}.
     * @param uuid the UUID of the form to retrieve.
     * @return the form or null if it does exist.
     * @should return the form with the specified uuid or null if it doesn't exist
     */
    public FacilityDataForm getFacilityDataFormByUUID(String uuid);

    /**
     * Get all Forms in the system.
     * @return a {@link List} containing all forms.
     * @throws IllegalArgumentException if passed a null parameter
     * @should get all forms
     */
    public List<FacilityDataForm> getAllFacilityDataForms();

    /**
     * Delete a form
     * @param form the form to delete.
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the specified form
     */
    public void deleteFacilityDataForm(FacilityDataForm form);

    /**
     * Saves a form schema
     * @param formSchema
     * @return the saved schema
     * @throws IllegalArgumentException if passed a null parameter
     * @should throw an exception if passed a null parameter
     * @should save the specified formSchema
     * @should return the saved schema
     */
    public FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema);

    /**
     * Get a specified form schema
     * @param id the id of the schema to retrieve
     * @return the schema or null if it does not exist.
     * @throws IllegalArgumentException if passed a null parameter
     * @should throw an exception if passed a null parameter
     * @should return the specified object with that id or null if it doesn't exist
     */
    public FacilityDataFormSchema getFacilityDataFormSchema(Integer id);

    /**
     * Gets a specified form schema using its {@link UUID}.
     * @param uuid the UUID of the schema to retrieve.
     * @return the schema or null if it does exist.
     * @throws IllegalArgumentException if passed a null parameter
     * @should throw an exception if passed a null parameter
     * @should return the schema with the specified uuid or null if it doesn't exist
     */
    public FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid);

    /**
     * Get all Form Schemas in the system.
     * @return a {@link List} containing all schemas.
     * @throws IllegalArgumentException if passed a null parameter
     * @should get all schemas
     */
    public List<FacilityDataFormSchema> getAllFacilityDataFormSchemas();
    
    /**
     * Delete a schema
     * @param schemaId the schema to delete.
     * @throws IllegalArgumentException if passed a null parameter
     * @should throw an exception if passed a null parameter
     * @should delete the specified schema
     */
    public void deleteFacilityDataFormSchema(Integer schemaId);
 

    /**
     * Save a <code>FacilityDataQuestionType</code> to the database.
     * @param questionType the <code>FacilityDataQuestionType</code> to be saved
     * @return the <code>FacilityDataQuestionType</code> which was just saved.
     * @should save and return the passed <code>FacilityDataQuestionType</code>
     */
    public FacilityDataQuestionType saveQuestionType(FacilityDataQuestionType questionType);

    /**
     * Get a <code>FacilityDataQuestionType</code>
     * @param id
     * @return the <code>FacilityDataQuestionType</code> or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the <code>FacilityDataQuestionType</code> with the passed id or null if it does not exist
     */
    public FacilityDataQuestionType getQuestionType(Integer id);

    /**
     * Get a <code>FacilityDataQuestionType</code> using its UUID
     * @param uuid
     * @return the <code>FacilityDataQuestionType</code> pertaining to that specific UUID.
     * @throws IllegalArgumentException if passed a null parameter or empty String
     * @should return the <code>FacilityDataQuestionType</code> with the specified uuid or null if it does not exist
     */
    public FacilityDataQuestionType getQuestionTypeByUUID(String uuid);

    /**
     * Get all FacilityDataQuestionTypes
     * @return a list containing all FacilityDataQuestionType
     * @throws IllegalArgumentException if passed a null parameter
     * @should return a list of all FacilityDataQuestionType.
     */
    public List<FacilityDataQuestionType> getAllQuestionTypes();

    /**
     * Delete a <code>FacilityDataQuestionType</code>
     * @param questionType the <code>FacilityDataQuestionType</code> object to be deleted
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the <code>FacilityDataQuestionType</code> object
     */
    public void deleteQuestionType(FacilityDataQuestionType questionType);
    
    /**
     * Save a <code>FacilityDataQuestion</code> to the database.
     * @param question the <code>FacilityDataQuestion</code> to be saved
     * @return the <code>FacilityDataQuestion</code> which was just saved.
     * @throws IllegalArgumentException if passed a null <code>FacilityDataQuestion</code>
     * @should throw an exception if the <code>FacilityDataQuestion</code> passed is null or if a null parameter is passed.
     * @should return the saved <code>FacilityDataQuestion</code>
     */
    public FacilityDataQuestion saveQuestion(FacilityDataQuestion question);

    /**
     * Get a <code>FacilityDataQuestion</code>
     *
     * @param id
     * @return the <code>FacilityDataQuestion</code> or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the <code>FacilityDataQuestion</code> with that id
     * @should return null if a <code>FacilityDataQuestion</code> with that id does not exist
     * @should throw an exception if passed in a null parameter
     */
    public FacilityDataQuestion getQuestion(Integer id);

    /**
     * Get a question using its UUID
     * @param uuid
     * @return the question pertaining to that specific UUID.
     * @throws IllegalArgumentException if passed a null parameter or empty String
     * @should throw an error if passed an empty string or a null parameter
     * @should return the question with the specified uuid or null.
     */
    public FacilityDataQuestion getQuestionByUUID(String uuid);

    /**
     * Get all questions
     * @return a list containing all questions
     * @throws IllegalArgumentException if passed a null parameter
     * @should return a list of all sections.
     */
    public List<FacilityDataQuestion> getAllQuestions();

    /**
     * Delete a <code>FacilityDataQuestion</code>
     * @param question the <code>FacilityDataQuestion</code> object to be deleted
     * @throws IllegalArgumentException if passed a null parameter
     * @should delete the <code>FacilityDataQuestion</code> object
     * @should throw an exception if a null paremeter is passed
     */
    public void deleteQuestion(FacilityDataQuestion question);

    /**
     * Save form data to the database
     * @param value the <code>FacilityDataValue</code> object to save.
     * @return the saved object
     * @throws IllegalArgumentException if passed a null parameter
     * @should save the object to the database
     * @should throw an exception if passed a null parameter
     */
    public FacilityDataValue saveFacilityDataValue(FacilityDataValue value);

    /**
     * Get a <code>FacilityDataValue</code> object with a given id.
     * @param id
     * @return an object with that id or null if it does not exist
     * @throws IllegalArgumentException if passed a null parameter
     * @should return the object if it one exists with that given id
     * @should return null if an object with that object does not exist
     * @should throw an exception if passed a null parameter
     */
    public FacilityDataValue getFacilityDataValue(Integer id);
    
    /**
     * @return all {@link FacilityDataValue}s that match the passed parameters
     */
    public List<FacilityDataValue> getFacilityDataValues(FacilityDataFormSchema schema, Date fromDate, Date toDate, Location facility);

    /** 
    * Delete a value from the database.
    * @param value the object containing the form data entered for this report.
    * @throws IllegalArgumentException if passed a null parameter
    * @should throw an exception if passed a null parameter
    * @should delete the given object from the database
    */
    public void deleteFacilityDataValue(FacilityDataValue value);
    
    /**
     * @return a Map from FacilityDataCodedOption id to a count of answers for that option
     */
    public Map<Integer, Integer> getCodedOptionBreakdown();
    
    /**
     * @return a Map from FacilityDataFormQuestion id to a count of values for that question
     */
    public Map<Integer, Integer> getFormQuestionBreakdown();
    
    /**
     * @return a Map from FacilityDataQuestion id to a count of form questions for that question
     */
    public Map<Integer, Integer> getQuestionBreakdown();
    
    /**
     * @return a Map from FacilityDataQuestionType id to a count of questions for that type
     */
    public Map<Integer, Integer> getQuestionTypeBreakdown();
    
	/**
	 * @return the earliest or latest start date of the value in the database for the passed schema
	 */
	public Date getMinOrMaxEnteredStartDateForSchema(FacilityDataFormSchema schema, String aggregation);
    
	/**
	 * @return a Map from Location Id to a Map of Date String to Integer, 
	 * where Date String is the start date of a value, and Integer is the count of values
	 */
	public Map<Integer, Map<String, Integer>> getNumberOfQuestionsAnswered(FacilityDataForm form, Date fromDate, Date toDate);
	
	/**
	 * @return a Map of Form Question ID to Integer count of values entered
	 */
	public Map<Integer, Integer> getNumberOfValuesRecordedByQuestion(FacilityDataFormSchema schema, Location facility, Date fromDate, Date toDate);

	/**
	 * @return a List of {@link FacilityDataValue} that match the passed query parameters.  Null parameters are ignored.
	 */
	public List<FacilityDataValue> evaluateFacilityDataQuery(FacilityDataQuery query);

    FacilityDataCodedOption saveCodedOption(FacilityDataCodedOption facilityDataCodedOption);

    FacilityDataCodedOption getCodedoptionByUuid(String s);

    FacilityDataFormSection saveFacilityDataFormSection(FacilityDataFormSection facilityDataFormSection);

    FacilityDataFormSection getFacilityDataFormSectionByUUID(String s);

    FacilityDataFormQuestion saveFacilityDataFormQuestion(FacilityDataFormQuestion facilityDataFormQuestion);

    FacilityDataFormQuestion getFacilityDataFormQuestion(String s);

    FacilityDataValue getFacilityDataValueByUuid(String s);
}

