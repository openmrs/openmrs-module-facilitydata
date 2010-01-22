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
package org.openmrs.module.facilitydata.model;

import com.google.common.collect.Lists;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.module.facilitydata.model.enums.FacilityDataFrequency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This represents an overall report form.
 */
public class FacilityDataFormSchema extends BaseOpenmrsMetadata {

    /**
     * Primary key identifier
     */
    private Integer id;

    /**
     * The frequency to which this form is to be filled out.
     * e.g. Daily, Quarterly, etc.
     */
    private FacilityDataFrequency frequency;

    /**
     * Start date to which this form is valid.
     */

    private Date validFrom;

    /**
     * End date to which this form is valid.
     */

    private Date validTo;

    /**
     * A {@link List} containing the {@link FacilityDataFormSection}s.
     */
    private List<FacilityDataFormSection> formSections = Lists.newArrayList();

    public FacilityDataFormSchema() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FacilityDataFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(FacilityDataFrequency frequency) {
        this.frequency = frequency;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public List<FacilityDataFormSection> getFormSections() {
        return formSections;
    }

    public void setFormSections(List<FacilityDataFormSection> formSections) {
        this.formSections = formSections;
    }

    public void addFormSection(FacilityDataFormSection section) {
        if (formSections == null) {
            formSections = new ArrayList<FacilityDataFormSection>();
        }
        formSections.add(section);
        section.addSectionToSchema(this);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        if (getName() == null) {
            return super.toString();
        }
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacilityDataFormSchema)) return false;

        FacilityDataFormSchema schema = (FacilityDataFormSchema) o;

        if (id != null ? !id.equals(schema.id) : schema.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
