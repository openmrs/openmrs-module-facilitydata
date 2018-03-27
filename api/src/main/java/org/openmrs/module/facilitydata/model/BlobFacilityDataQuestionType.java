package org.openmrs.module.facilitydata.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "facilitydata_question_type")
@DiscriminatorValue("org.openmrs.module.facilitydata.model.BlobFacilityDataQuestionType")
public class BlobFacilityDataQuestionType extends FacilityDataQuestionType  {

    //***** CONSTRUCTORS *****
    public BlobFacilityDataQuestionType() {super();}


}
