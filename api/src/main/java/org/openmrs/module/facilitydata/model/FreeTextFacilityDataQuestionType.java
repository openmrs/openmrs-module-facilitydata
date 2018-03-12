package org.openmrs.module.facilitydata.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Base class for Text Free question types
 */
@Entity
@Table(name = "facilitydata_question_type")
@DiscriminatorValue("org.openmrs.module.facilitydata.model.FreeTextFacilityDataQuestionType")
public class FreeTextFacilityDataQuestionType extends FacilityDataQuestionType {

	 //***** CONSTRUCTORS *****
	public FreeTextFacilityDataQuestionType() {super();}
	
	//***** PROPERTIES *****
	@Column(name="question_text")
	private String questionText;

	
	   //***** PROPERTY ACCESS *****
	
	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}
	
	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}



	
	
}
