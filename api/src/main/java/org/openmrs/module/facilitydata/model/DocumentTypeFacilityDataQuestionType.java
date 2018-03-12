package org.openmrs.module.facilitydata.model;


import org.openmrs.module.facilitydata.model.enums.DocumentType;

import javax.persistence.*;

@Entity
@Table(name = "facilitydata_question_type")
@DiscriminatorValue("org.openmrs.module.facilitydata.model.DocumentTypeFacilityDataQuestionType")
public class DocumentTypeFacilityDataQuestionType extends FacilityDataQuestionType {


    //***** CONSTRUCTORS *****
    public DocumentTypeFacilityDataQuestionType() {super();}

    //***** PROPERTIES *****
    @Column(name="document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    //***** PROPERTY ACCESS *****


    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
