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
package org.openmrs.module.facilitydata.dao;

import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.api.db.DAOException;

import java.util.List;
import java.util.UUID;


public interface FacilityDataDAO {

    
    /**
     * Saves a form schema
     * @param formSchema
     * @return the saved schema
     */
    FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema);

    /**
     * Get a specified form achema
     * @param id the id of the schema to retrieve
     * @return the schema or null if it does not exist.
     */
    FacilityDataFormSchema getFacilityDataFormSchema(Integer id);

    /**
     * Gets a specified form schema using its {@link UUID}.
     * @param uuid the UUID of the schema to retrieve.
     * @return the schema or null if it does exist.
     */
    FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid);

    /**
     * Get all Form Schemas in the system.
     * @return a {@link List} containing all schemas.

     */
    List<FacilityDataFormSchema> getAllFacilityDataFormSchemas();

    /**
     * Delete a schema
     * @param formSchema the schema to delete.
     */
    void deleteFacilityDataFormSchema(FacilityDataFormSchema formSchema);

    /**
     * Save a section
     * @param formSection the section to save
     * @return the saved section
     */
    FacilityDataFormSection saveFacilityDataFormSection(FacilityDataFormSection formSection);

    /**
     *  Get a section with the specified id
     * @param id
     * @return the specified section with that id
     */
    FacilityDataFormSection getFacilityDataFormSection(Integer id);

    /**
     * Retrieve the section using its {@link UUID}.
     * @param uuid
     * @return the section with the specified uuid
     */
    FacilityDataFormSection getFacilityDataFormSectionByUUID(String uuid);

    /**
     * Get all sections which are currently saved.
     * @return a {@link List} containing all sections saved.
     */
    List<FacilityDataFormSection> getAllFacilityDataFormSections();

    /**
     *  Delete the specified form section.
     * @param formSection
     */
    void deleteFacilityDataFormSection(FacilityDataFormSection formSection);

    /**
     * Save the Form Question
     * @param formQuestion the form question to save
     * @return the saved question
     */
    FacilityDataFormQuestion saveFacilityDataFormQuestion(FacilityDataFormQuestion formQuestion);

    /**
     *  Get the form question using its id.
     * @param id the id
     * @return the form question with the specified id or null if it does not exist.   
     */
    FacilityDataFormQuestion getFacilityDataFormQuestion(Integer id);

    /**
     *  Get the form question using its uuid
     * @param uuid the {@link UUID}
     * @return the form question with the specified UUID or null if it does not exist.
     */
    FacilityDataFormQuestion getFacilityDataFormQuestionByUUID(String uuid);

    /**
     *  Get all form questions.
     * @return a {@link List} containing all form questions
     */
    List<FacilityDataFormQuestion> getAllFacilityDataFormQuestions();

    /**
     * Delete a form question.
     * @param formQuestion the form question to delete
     */
    void deleteFacilityDataFormQuestion(FacilityDataFormQuestion formQuestion);


    /**
     * Save a <code>FacilityDataQuestion</code> to the database.
     *
     * @param question the <code>FacilityDataQuestion</code> to be saved
     * @return the <code>FacilityDataQuestion</code> which was just saved.
     */
    FacilityDataQuestion saveQuestion(FacilityDataQuestion question);

    /**
     * Get a <code>FacilityDataQuestion</code>
     *
     * @param id
     * @return the <code>FacilityDataQuestion</code> or null if it does not exist
     */
    FacilityDataQuestion getQuestion(Integer id);

     /**
     * Get a question using its UUID
     * @param uuid
     * @return the question pertaining to that specific UUID.
     */
    FacilityDataQuestion getQuestionByUUID(String uuid);

    /**
     * Get all questions.
     * @return all {@link FacilityDataQuestion}.
     */
    List<FacilityDataQuestion> getAllFacilityDataQuestions();

    /**
     * Delete a <code>FacilityDataQuestion</code>
     *
     * @param question the <code>FacilityDataQuestion</code> object to be deleted
     */
    void deleteQuestion(FacilityDataQuestion question);

    /**
     * Save form data to the database
     *
     * @param value the <code>FacilityDataValue</code> object to save.
     * @return the saved object
     */
    FacilityDataValue saveFacilityDataValue(FacilityDataValue value);

    /**
     * Get a <code>FacilityDataValue</code> object with a given id.
     *
     * @param id
     * @return an object with that id or null if it does not exist
     */
    FacilityDataValue getFacilityDataValue(Integer id);

    /**
     * Return all {@link FacilityDataValue} references.
     * @return a list containing all references.
     */
    List<FacilityDataValue> getAllFacilityDataValues();
    
    /**
     * Delete a <code>FacilityDataValue</code> from the database
     *
     * @param value the object containing the form data entered for this report.
     */
    void deleteFacilityValue(FacilityDataValue value);
}

