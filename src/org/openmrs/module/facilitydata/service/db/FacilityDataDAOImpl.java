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

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;
import org.openmrs.module.facilitydata.model.FacilityDataValue;

@SuppressWarnings({"unchecked", "override", "DesignForExtension", "ChainedMethodCall"})
public class FacilityDataDAOImpl implements FacilityDataDAO {
    private SessionFactory sessionFactory;
    private static final Logger log = Logger.getLogger(FacilityDataDAOImpl.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a form schema
     *
     * @param formSchema
     * @return the saved schema
     */
    public FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema) {
        sessionFactory.getCurrentSession().saveOrUpdate(formSchema);
        return formSchema;
    }

    /**
     * Get a specified form achema
     *
     * @param id the id of the schema to retrieve
     * @return the schema or null if it does not exist.
     */
    public FacilityDataFormSchema getFacilityDataFormSchema(Integer id) {
        return (FacilityDataFormSchema) sessionFactory.getCurrentSession().get(FacilityDataFormSchema.class, id);

    }

    /**
     * Gets a specified form schema using its {@link UUID}.
     *
     * @param uuid the UUID of the schema to retrieve.
     * @return the schema or null if it does exist.
     */
    public FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid) {
        return (FacilityDataFormSchema) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormSchema.class)
                .add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    /**
     * Get all Form Schemas in the system.
     *
     * @return a {@link List} containing all schemas.
     */
    public List<FacilityDataFormSchema> getAllFacilityDataFormSchemas() {
        return (List<FacilityDataFormSchema>) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormSchema.class)
                .addOrder(Order.asc("name")).list();
    }

    /**
     * Delete a schema
     *
     * @param formSchema the schema to delete.
     */
    public void deleteFacilityDataFormSchema(FacilityDataFormSchema formSchema) {
        sessionFactory.getCurrentSession().delete(formSchema);
    }

    /**
     * Save a section
     *
     * @param formSection the section to save
     * @return the saved section
     */
    public FacilityDataFormSection saveFacilityDataFormSection(FacilityDataFormSection formSection) {
        sessionFactory.getCurrentSession().saveOrUpdate(formSection);
        return formSection;
    }

    /**
     * Get a section with the specified id
     *
     * @param id
     * @return the specified section with that id
     */
    public FacilityDataFormSection getFacilityDataFormSection(Integer id) {
        return (FacilityDataFormSection) sessionFactory.getCurrentSession().get(FacilityDataFormSection.class, id);
    }

    /**
     * Retrieve the section using its {@link UUID}.
     *
     * @param uuid
     * @return the section with the specified uuid
     */
    public FacilityDataFormSection getFacilityDataFormSectionByUUID(String uuid) {
        return (FacilityDataFormSection) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormSection.class)
                .add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    /**
     * Get all sections which are currently saved.
     *
     * @return a {@link List} containing all sections saved.
     */
    public List<FacilityDataFormSection> getAllFacilityDataFormSections() {
        return (List<FacilityDataFormSection>) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormSection.class)
                .addOrder(Order.asc("name")).list();
    }

    /**
     * Delete the specified form section.
     *
     * @param formSection
     */
    public void deleteFacilityDataFormSection(FacilityDataFormSection formSection) {
        sessionFactory.getCurrentSession().delete(formSection);
    }

    /**
     * Save the Form Question
     *
     * @param formQuestion the form question to save
     * @return the saved question
     */
    public FacilityDataFormQuestion saveFacilityDataFormQuestion(FacilityDataFormQuestion formQuestion) {
        sessionFactory.getCurrentSession().saveOrUpdate(formQuestion);
        return formQuestion;
    }

    /**
     * Get the form question using its id.
     *
     * @param id the id
     * @return the form question with the specified id or null if it does not exist.
     */
    public FacilityDataFormQuestion getFacilityDataFormQuestion(Integer id) {
        return (FacilityDataFormQuestion) sessionFactory.getCurrentSession().get(FacilityDataFormQuestion.class, id);
    }

    /**
     * Get the form question using its uuid
     *
     * @param uuid the {@link UUID}
     * @return the form question with the specified UUID or null if it does not exist.
     */
    public FacilityDataFormQuestion getFacilityDataFormQuestionByUUID(String uuid) {
        return (FacilityDataFormQuestion) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormQuestion.class)
                .add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }


    /**
     * Get all form questions.
     *
     * @return a {@link List} containing all form questions
     */
    public List<FacilityDataFormQuestion> getAllFacilityDataFormQuestions() {
        return (List<FacilityDataFormQuestion>) sessionFactory.getCurrentSession().createCriteria(FacilityDataFormQuestion.class)
                .addOrder(Order.asc("name")).list();
    }

    /**
     * Delete a form question.
     *
     * @param formQuestion the form question to delete
     */
    public void deleteFacilityDataFormQuestion(FacilityDataFormQuestion formQuestion) {
        sessionFactory.getCurrentSession().delete(formQuestion);
    }

    /**
     * Save a <code>FacilityDataQuestion</code> to the database.
     *
     * @param question the <code>FacilityDataQuestion</code> to be saved
     * @return the <code>FacilityDataQuestion</code> which was just saved.
     */
    public FacilityDataQuestion saveQuestion(FacilityDataQuestion question) {
        sessionFactory.getCurrentSession().saveOrUpdate(question);
        return question;

    }

    /**
     * Get a <code>FacilityDataQuestion</code>
     *
     * @param id
     * @return the <code>FacilityDataQuestion</code> or null if it does not exist
     */
    public FacilityDataQuestion getQuestion(Integer id)  {
        return (FacilityDataQuestion) sessionFactory.getCurrentSession().get(FacilityDataQuestion.class, id);
    }

    /**
     * Get a question using its UUID
     *
     * @param uuid
     * @return the question pertaining to that specific UUID.
     */
    public FacilityDataQuestion getQuestionByUUID(String uuid)  {
        return (FacilityDataQuestion) sessionFactory.getCurrentSession().createCriteria(FacilityDataQuestion.class)
                .add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    /**
     * Get all {@link FacilityDataQuestion}s.
     *
     * @return
     */
    public List<FacilityDataQuestion> getAllFacilityDataQuestions()  {
        return (List<FacilityDataQuestion>) sessionFactory.getCurrentSession().createCriteria(FacilityDataQuestion.class)
                .addOrder(Order.asc("dateCreated")).list();
    }

    /**
     * Delete a <code>FacilityDataQuestion</code>
     *
     * @param question the <code>FacilityDataQuestion</code> object to be deleted
     */
    public void deleteQuestion(FacilityDataQuestion question) {
        sessionFactory.getCurrentSession().delete(question);
    }

    /**
     * Save form data to the database
     *
     * @param value the <code>FacilityDataValue</code> object to save.
     * @return the saved object
     */
    public FacilityDataValue saveFacilityDataValue(FacilityDataValue value) {
        sessionFactory.getCurrentSession().saveOrUpdate(value);
        return value;
    }

    /**
     * Get a <code>FacilityDataValue</code> object with a given id.
     *
     * @param id
     * @return an object with that id or null if it does not exist
     */
    public FacilityDataValue getFacilityDataValue(Integer id) {
        return (FacilityDataValue) sessionFactory.getCurrentSession().get(FacilityDataValue.class, id);

    }

    /**
     * Return all {@link FacilityDataValue} references.
     *
     * @return a list containing all references.
     */
    public List<FacilityDataValue> getAllFacilityDataValues() {
        return sessionFactory.getCurrentSession().createCriteria(FacilityDataValue.class)
                .addOrder(Order.asc("dateCreated")).list();
    }

    /**
     * Delete a <code>FacilityDataValue</code> from the database
     *
     * @param value the object containing the form data entered for this report.
     */
    public void deleteFacilityValue(FacilityDataValue value) {
        sessionFactory.getCurrentSession().delete(value);
    }
}