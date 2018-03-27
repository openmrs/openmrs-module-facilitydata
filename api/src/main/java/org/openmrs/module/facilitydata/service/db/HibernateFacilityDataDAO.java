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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataQuery;
import org.openmrs.module.facilitydata.util.FacilityDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Core implementation of the DAO
 */
@Repository("facilitydata.hibernatefacilitydatadao")
public class HibernateFacilityDataDAO implements FacilityDataDAO {
	
	//***** PROPERTIES *****
	@Autowired
    private DbSessionFactory  dbSessionFactory;

    //***** INSTANCE METHODS *****
    
	/**
	 * @see FacilityDataDAO#saveFacilityDataForm(FacilityDataForm)
	 */
	public FacilityDataForm saveFacilityDataForm(FacilityDataForm form) {
		
		getCurrentSession().saveOrUpdate(form);
        return form;
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataForm(Integer)
	 */
	public FacilityDataForm getFacilityDataForm(Integer id) {
		
		return (FacilityDataForm) getCurrentSession().get(FacilityDataForm.class, id);
	}

	private DbSession getCurrentSession() {
		
		return dbSessionFactory.getCurrentSession();
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataFormByUUID(String)
	 */
	public FacilityDataForm getFacilityDataFormByUUID(String uuid) {

		Criteria c = getCurrentSession().createCriteria(FacilityDataForm.class);
        return (FacilityDataForm) c.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/**
	 * @see FacilityDataDAO#getAllFacilityDataForms()
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataForm> getAllFacilityDataForms() {

		Criteria c = getCurrentSession().createCriteria(FacilityDataForm.class);
        return c.addOrder(Order.asc("name")).list();
	}

	/**
	 * @see FacilityDataDAO#deleteFacilityDataForm(FacilityDataForm)
	 */
	public void deleteFacilityDataForm(FacilityDataForm form) {
	
		getCurrentSession().delete(form);
	}

	/**
	 * @see FacilityDataDAO#saveFacilityDataFormSchema(FacilityDataFormSchema)
	 */
	public FacilityDataFormSchema saveFacilityDataFormSchema(FacilityDataFormSchema formSchema) {
        getCurrentSession().saveOrUpdate(formSchema);
        return formSchema;
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataFormSchema(Integer)
	 */
	public FacilityDataFormSchema getFacilityDataFormSchema(Integer id) {
		return (FacilityDataFormSchema) getCurrentSession().get(FacilityDataFormSchema.class, id);
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataFormSchemaByUUID(String)
	 */
	public FacilityDataFormSchema getFacilityDataFormSchemaByUUID(String uuid) {
        Criteria c = getCurrentSession().createCriteria(FacilityDataFormSchema.class);
        return (FacilityDataFormSchema) c.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/**
	 * @see FacilityDataDAO#getAllFacilityDataFormSchemas()
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataFormSchema> getAllFacilityDataFormSchemas() {
		System.out.println("====================================================");
		System.out.println("Current Session ::::: "+getCurrentSession());
		System.out.println("====================================================");
       
		Criteria c = getCurrentSession().createCriteria(FacilityDataFormSchema.class);
        return c.addOrder(Order.asc("name")).list();
	}

	/**
	 * @see FacilityDataDAO#deleteFacilityDataFormSchema(Integer)
	 */
	public void deleteFacilityDataFormSchema(Integer schemaId) {
		
       
		String q = "select form from facilitydata_form_schema where schema_id = " + schemaId;
		Object formId = getCurrentSession().createSQLQuery(q).uniqueResult();
		
		String q1 = "delete from facilitydata_form_question where section in " +
						"(select form_section_id from facilitydata_form_section where schema_id = " + schemaId + ")";
		String q2 = "delete from facilitydata_form_section where schema_id = " + schemaId;
		String q3 = "delete from facilitydata_form_schema where schema_id = " + schemaId;
		
		getCurrentSession().createSQLQuery(q1).executeUpdate();
		getCurrentSession().createSQLQuery(q2).executeUpdate();
		getCurrentSession().createSQLQuery(q3).executeUpdate();
		
		String anyQuery = "select count(*) from facilitydata_form_schema where form = " + formId;
		String result = getCurrentSession().createSQLQuery(anyQuery).uniqueResult().toString();
		if ("0".equals(result)) {
			String deleteFormQuery = "delete from facilitydata_form where form_id = " + formId;
			getCurrentSession().createSQLQuery(deleteFormQuery).executeUpdate();
		}
	}

	/**
	 * @see FacilityDataDAO#saveQuestionType(FacilityDataQuestionType)
	 */
	public FacilityDataQuestionType saveQuestionType(FacilityDataQuestionType questionType) {

       
		getCurrentSession().saveOrUpdate(questionType);
		//getCurrentSession().persist(questionType);
		return questionType;
	}

	/**
	 * @see FacilityDataDAO#getQuestionType(java.lang.Integer)
	 */
	public FacilityDataQuestionType getQuestionType(Integer id) {

       
		return (FacilityDataQuestionType) getCurrentSession().get(FacilityDataQuestionType.class, id);
	}

	/**
	 * @see FacilityDataDAO#getQuestionTypeByUUID(String)
	 */
	public FacilityDataQuestionType getQuestionTypeByUUID(String uuid) {

       
		Criteria c = getCurrentSession().createCriteria(FacilityDataQuestionType.class);
        return (FacilityDataQuestionType) c.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/**
	 * @see FacilityDataDAO#getAllQuestionTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataQuestionType> getAllQuestionTypes() {

       Criteria c = getCurrentSession().createCriteria(FacilityDataQuestionType.class);
       //c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return c.addOrder(Order.asc("name")).list();
	}

	/**
	 * @see FacilityDataDAO#deleteQuestionType(FacilityDataQuestionType)
	 */
	public void deleteQuestionType(FacilityDataQuestionType questionType) {

       
		getCurrentSession().delete(questionType);
	}

	/**
	 * @see FacilityDataDAO#saveQuestion(FacilityDataQuestion)
	 */
	public FacilityDataQuestion saveQuestion(FacilityDataQuestion question) {

       
		getCurrentSession().saveOrUpdate(question);
        return question;
	}

	/**
	 * @see FacilityDataDAO#getQuestion(Integer)
	 */
	public FacilityDataQuestion getQuestion(Integer id) {

       
		return (FacilityDataQuestion) getCurrentSession().get(FacilityDataQuestion.class, id);
	}

	/**
	 * @see FacilityDataDAO#getQuestionByUUID(String)
	 */
	public FacilityDataQuestion getQuestionByUUID(String uuid) {

       
		Criteria c = getCurrentSession().createCriteria(FacilityDataQuestion.class);
        return (FacilityDataQuestion) c.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/**
	 * @see FacilityDataDAO#getAllQuestions()
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataQuestion> getAllQuestions() {
		
		Criteria c = getCurrentSession().createCriteria(FacilityDataQuestion.class);
        return c.addOrder(Order.asc("dateCreated")).list();
	}

	/**
	 * @see FacilityDataDAO#deleteQuestion(FacilityDataQuestion)
	 */
	public void deleteQuestion(FacilityDataQuestion question) {
		getCurrentSession().delete(question);
	}

	/**
	 * @see FacilityDataDAO#saveFacilityDataValue(FacilityDataValue)
	 */
	public FacilityDataValue saveFacilityDataValue(FacilityDataValue value) {
		getCurrentSession().saveOrUpdate(value);
        return value;
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataValue(Integer)
	 */
	public FacilityDataValue getFacilityDataValue(Integer id) {
		return (FacilityDataValue) getCurrentSession().get(FacilityDataValue.class, id);
	}

	/**
	 * @see FacilityDataDAO#getFacilityDataValues(FacilityDataFormSchema, Date, Date, Location)
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataValue> getFacilityDataValues(FacilityDataFormSchema schema, Date fromDate, Date toDate, Location facility) {
		Criteria c = getCurrentSession().createCriteria(FacilityDataValue.class);
		c.createCriteria("question").createCriteria("section").add(Restrictions.eq("schema", schema));
		c.add(Restrictions.eq("fromDate", fromDate));
		c.add(Restrictions.eq("toDate", toDate));
		c.add(Restrictions.eq("facility", facility));
		c.add(Restrictions.eq("voided", Boolean.FALSE));
		return c.list();
	}
	
	/**
	 * @see FacilityDataService#getCodedOptionBreakdown()
	 */
	public Map<Integer, Integer> getCodedOptionBreakdown() {
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		String s = "select value_coded, count(*) from facilitydata_value where value_coded is not null group by value_coded";
		Query query = getCurrentSession().createSQLQuery(s);
		for (Object entry : query.list()) {
			Object[] row = (Object[]) entry;
			m.put(new Integer(row[0].toString()), new Integer(row[1].toString()));
		}
		return m;
	}
	
	/**
	 * @see FacilityDataService#getQuestionBreakdown()
	 */
	public Map<Integer, Integer> getQuestionBreakdown() {
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		String s = "select question, count(*) from facilitydata_form_question group by question";
		Query query = getCurrentSession().createSQLQuery(s);
		for (Object entry : query.list()) {
			Object[] row = (Object[]) entry;
			m.put(new Integer(row[0].toString()), new Integer(row[1].toString()));
		}
		return m;
	}
	
	/**
	 * @see FacilityDataService#getQuestionTypeBreakdown()
	 */
	public Map<Integer, Integer> getQuestionTypeBreakdown() {
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		String s = "select question_type, count(*) from facilitydata_question group by question_type";
		Query query = getCurrentSession().createSQLQuery(s);
		for (Object entry : query.list()) {
			Object[] row = (Object[]) entry;
			m.put(new Integer(row[0].toString()), new Integer(row[1].toString()));
		}
		return m;
	}
	
	/**
	 * @see FacilityDataService#getFormQuestionBreakdown()
	 */
	public Map<Integer, Integer> getFormQuestionBreakdown() {
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		String s = "select question, count(*) from facilitydata_value group by question";
		Query query = getCurrentSession().createSQLQuery(s);
		for (Object entry : query.list()) {
			Object[] row = (Object[]) entry;
			m.put(new Integer(row[0].toString()), new Integer(row[1].toString()));
		}
		return m;
	}
	
	/**
	 * @see FacilityDataDAO#getMinOrMaxEnteredStartDateForSchema(FacilityDataFormSchema, String)
	 */
	public Date getMinOrMaxEnteredStartDateForSchema(FacilityDataFormSchema schema, String aggregation) {
		Date d = null;
		String s = "select " + aggregation + "(from_date) " +
				   "from facilitydata_value v, facilitydata_form_question q, facilitydata_form_section s " +
				   "where v.question = q.form_question_id " +
				   "and q.section = s.form_section_id " +
				   "and s.schema_id = :schemaId " +
				   "and v.voided = 0 ";
		Query query = getCurrentSession().createSQLQuery(s);
		query.setParameter("schemaId", schema.getSchemaId());
		for (Object entry : query.list()) {
			d = (Date) entry;
		}
		return d;
	}
	
	/**
	 * @see FacilityDataDAO#getNumberOfQuestionsAnswered(FacilityDataForm, Date, Date)
	 */
	public Map<Integer, Map<String, Integer>> getNumberOfQuestionsAnswered(FacilityDataForm form, Date fromDate, Date toDate) {
		Map<Integer, Map<String, Integer>> ret = new HashMap<Integer, Map<String, Integer>>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String s = "select v.facility, v.from_date, count(*) " +
				   "from facilitydata_value v, facilitydata_form_question q, facilitydata_form_section s, facilitydata_form_schema f " +
				   "where v.question = q.form_question_id " +
				   "and q.section = s.form_section_id " +
				   "and s.schema_id = f.schema_id " +
				   "and f.form = :formId " +
				   "and v.from_date >= :fromDate " +
				   "and v.to_date <= :toDate " +
				   "and v.voided = 0 " +
				   "group by v.facility, v.from_date";
		
		Query query = getCurrentSession().createSQLQuery(s);
		query.setParameter("formId", form.getId());
		query.setParameter("fromDate", df.format(fromDate));
		query.setParameter("toDate", df.format(toDate));
		for (Object entry : query.list()) {
			
			Object[] row = (Object[]) entry;
			Integer locationId = new Integer(row[0].toString());
			Map<String, Integer> locationRow = ret.get(locationId);
			if (locationRow == null) {
				locationRow = new HashMap<String, Integer>();
				ret.put(locationId, locationRow);
			}
			locationRow.put(df.format((Date)row[1]), new Integer(row[2].toString()));
		}
		
		return ret;
	}
	
	/**
	 * @see FacilityDataDAO#getNumberOfValuesRecordedByQuestion(FacilityDataFormSchema, Location, Date, Date)
	 */
	public Map<Integer, Integer> getNumberOfValuesRecordedByQuestion(FacilityDataFormSchema schema, Location facility, Date fromDate, Date toDate) {
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		
		String s = "select q.question, count(*) " +
				   "from facilitydata_value v, facilitydata_form_question q, facilitydata_form_section s " +
				   "where v.question = q.form_question_id " +
				   "and q.section = s.form_section_id " +
				   "and s.schema_id = :schemaId " +
				   (facility == null ? "" : "and v.facility = :facility ") +
				   (fromDate == null ? "" : "and v.from_date >= :fromDate ") +
				   (toDate == null ? "" : "and v.to_date <= :toDate ") +
				   "and v.voided = 0 " +
				   "group by q.question";
		
		Query query = getCurrentSession().createSQLQuery(s);
		query.setParameter("schemaId", schema.getId());
		if (facility != null) {
			query.setParameter("facility", facility);
		}
		if (fromDate != null) {
			query.setParameter("fromDate", fromDate);
		}
		if (toDate != null) {
			query.setParameter("toDate", toDate);
		}
		for (Object entry : query.list()) {
			Object[] row = (Object[]) entry;
			ret.put(new Integer(row[0].toString()), new Integer(row[1].toString()));
		}
		
		return ret;
	}
	
	/**
	 * @see FacilityDataDAO#evaluateFacilityDataQuery(FacilityDataQuery)
	 */
	@SuppressWarnings("unchecked")
	public List<FacilityDataValue> evaluateFacilityDataQuery(FacilityDataQuery query) {
		Criteria c = getCurrentSession().createCriteria(FacilityDataValue.class);
		if (query.getForm() != null) {
			c.createCriteria("question").createCriteria("section").createCriteria("schema").add(Restrictions.eq("form", query.getForm()));
		}
		if (query.getSchema() != null) {
			c.createCriteria("question").createCriteria("section").add(Restrictions.eq("schema", query.getSchema()));
		}
		if (query.getQuestion() != null) {
			c.createCriteria("question").add(Restrictions.eq("question", query.getQuestion()));
		}
		if (query.getFacility() != null) {
			c.add(Restrictions.in("facility", FacilityDataUtil.getAllLocationsInHierarchy(query.getFacility())));
		}
		if (query.getFromDate() != null) {
			c.add(Restrictions.ge("fromDate", query.getFromDate()));
		}
		if (query.getToDate() != null) {
			c.add(Restrictions.le("toDate", query.getToDate()));
		}
		if (query.getEnteredFromDate() != null) {
			c.add(Restrictions.ge("dateCreated", query.getEnteredFromDate()));
		}
		if (query.getEnteredToDate() != null) {
			c.add(Restrictions.le("dateCreated", FacilityDataUtil.getEndOfDayIfTimeExcluded(query.getEnteredToDate())));
		}		
		c.add(Restrictions.eq("voided", Boolean.FALSE));

		return c.list();
	}

	@Override
	public FacilityDataCodedOption saveCodedOption(FacilityDataCodedOption facilityDataCodedOption) {
		return (FacilityDataCodedOption) getDbSessionFactory().getCurrentSession().save(facilityDataCodedOption);
	}

	@Override
	public FacilityDataCodedOption getCodedoptionByUuid(String s) {
		Criteria c =getDbSessionFactory().getCurrentSession().createCriteria(FacilityDataCodedOption.class);
		c.add(Restrictions.eq("uuid",s));
		FacilityDataCodedOption object=(FacilityDataCodedOption)c.uniqueResult();
		return object;
	}

	@Override
	public FacilityDataFormSection saveFacilityDataFormSection(FacilityDataFormSection facilityDataFormSection) {
		return (FacilityDataFormSection) getDbSessionFactory().getCurrentSession().save(facilityDataFormSection);
	}

	@Override
	public FacilityDataFormSection getFacilityDataFormSectionByUUID(String s) {
		Criteria c =getDbSessionFactory().getCurrentSession().createCriteria(FacilityDataFormSection.class);
		c.add(Restrictions.eq("uuid",s));
		FacilityDataFormSection object=(FacilityDataFormSection)c.uniqueResult();
		return object;

	}

	@Override
	public FacilityDataFormQuestion saveFacilityDataFormQuestion(FacilityDataFormQuestion facilityDataFormQuestion) {
		return (FacilityDataFormQuestion) getDbSessionFactory().getCurrentSession().save(facilityDataFormQuestion);
	}

	@Override
	public FacilityDataFormQuestion getFacilityDataFormQuestion(String s) {
		Criteria c =getDbSessionFactory().getCurrentSession().createCriteria(FacilityDataFormQuestion.class);
		c.add(Restrictions.eq("uuid",s));
		FacilityDataFormQuestion object=(FacilityDataFormQuestion)c.uniqueResult();
		return object;
	}

	@Override
	public FacilityDataValue getFacilityDataValueByUuid(String s) {
		Criteria c =getDbSessionFactory().getCurrentSession().createCriteria(FacilityDataValue.class);
		c.add(Restrictions.eq("uuid",s));
		FacilityDataValue object=(FacilityDataValue)c.uniqueResult();
		return object;
	}

	/**
	 * @see FacilityDataDAO#deleteFacilityDataValue(FacilityDataValue)
	 */
	public void deleteFacilityDataValue(FacilityDataValue value) {
		getCurrentSession().delete(value);
	}

	public DbSessionFactory getDbSessionFactory() {
		return dbSessionFactory;
	}

	public void setDbSessionFactory(DbSessionFactory dbSessionFactory) {
		this.dbSessionFactory = dbSessionFactory;
	}
    
    //***** PROPERTY ACCESS *****
    

	
	
/*	*//**
		 * Gets the current hibernate session while taking care of the hibernate 3 and 4 differences.
		 * @author muhammad.ahmed@ihsinformatics.com
		 * @return the current hibernate session.
		 *//*
		private org.hibernate.Session getCurrentSession() {
			try {
				return getCurrentSession();
			}
			catch (NoSuchMethodError ex) {
				try {
					Method method = sessionFactory.getClass().getMethod("getCurrentSession", null);
					return (org.hibernate.Session)method.invoke(sessionFactory, null);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Failed to get the current hibernate session", e);
					//log.error("Failed to get the hibernate session", e);
				}
			}
		}
    */
    
}