package org.openmrs.module.facilitydata.util;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.facilitydata.model.FacilityDataForm;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;

/**
 * Encapsulates the arguments for querying and retrieving a subset of FacilityDataValues
 */
public class FacilityDataQuery {
	
	//***** PROPERTIES *****
	
	private FacilityDataForm form;
	private FacilityDataFormSchema schema;
	private FacilityDataQuestion question;
	private Location facility;
	private Date fromDate;
	private Date toDate;
	private Date enteredFromDate;
	private Date enteredToDate;
	
	//***** CONSTRUCTORS *****
    
	/**
	 * Default Constructor
	 */
	public FacilityDataQuery() {}
	
	//***** PROPERTY ACCESS *****

	/**
	 * @return the form
	 */
	public FacilityDataForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(FacilityDataForm form) {
		this.form = form;
	}

	/**
	 * @return the schema
	 */
	public FacilityDataFormSchema getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(FacilityDataFormSchema schema) {
		this.schema = schema;
	}

	/**
	 * @return the question
	 */
	public FacilityDataQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(FacilityDataQuestion question) {
		this.question = question;
	}

	/**
	 * @return the facility
	 */
	public Location getFacility() {
		return facility;
	}

	/**
	 * @param facility the facility to set
	 */
	public void setFacility(Location facility) {
		this.facility = facility;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the enteredFromDate
	 */
	public Date getEnteredFromDate() {
		return enteredFromDate;
	}

	/**
	 * @param enteredFromDate the enteredFromDate to set
	 */
	public void setEnteredFromDate(Date enteredFromDate) {
		this.enteredFromDate = enteredFromDate;
	}

	/**
	 * @return the enteredToDate
	 */
	public Date getEnteredToDate() {
		return enteredToDate;
	}

	/**
	 * @param enteredToDate the enteredToDate to set
	 */
	public void setEnteredToDate(Date enteredToDate) {
		this.enteredToDate = enteredToDate;
	}
}
