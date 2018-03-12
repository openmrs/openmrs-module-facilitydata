package org.openmrs.module.facilitydata.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.openmrs.module.facilitydata.model.enums.Frequency;

/**
 * Represents a particular form, which might be represented by different schemas over time
 */

@Entity
@Table(name = "facilitydata_form")
public class FacilityDataForm extends BaseFacilityMetaData {
	
	//***** PROPERTIES *****
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="form_id")
	private Integer formId;
	
	@Column(name="frequency")
	@Enumerated(EnumType.STRING)
	private Frequency frequency;
	
	
	@OneToMany(fetch=FetchType.EAGER,orphanRemoval = true,cascade=CascadeType.ALL)
    @JoinColumn(name = "schema_id")
    private Set<FacilityDataFormSchema> schemas;
    
    //***** CONSTRUCTORS *****
    
    public FacilityDataForm() {}
    
    //***** INSTANCE METHODS *****

	/**
	 * @return a list of dependencies for the Metadata Sharing Module
	 */
	public List<Object> getPriorityDependenciesForMetadataSharing() {
		List<Object> list = new ArrayList<Object>();
		for (FacilityDataFormSchema schema : getSchemas()) {
			for (FacilityDataFormSection section : schema.getSections()) {
				for (FacilityDataFormQuestion question : section.getQuestions()) {
					list.add(question.getQuestion());
				}
			}
		}
		return list;
	}

    //***** PROPERTY ACCESS *****
    
	/**
	 * @return the frequency
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the schemas
	 */
	public Set<FacilityDataFormSchema> getSchemas() {
		if (schemas == null) {
			 schemas = new HashSet<FacilityDataFormSchema>();
		}
		return schemas;
	}

	/**
	 * @param schemas the schemas to set
	 */
	public void setSchemas(Set<FacilityDataFormSchema> schemas) {
		this.schemas = schemas;
	}

	@Override
	public Integer getId() {
		
		return formId;
	}

	@Override
	public void setId(Integer id) {
		this.formId=id;
	}
	
	
}
