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

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.module.facilitydata.model.enums.AggregationMethod;

/**
 * The base class for all question types.
 */
public abstract class FacilityDataQuestion extends BaseOpenmrsMetadata {
    private Integer id;
    /**
     * Aggregation method. e.g. the last value entered or the sum of all values (if numeric.)
     */
    private AggregationMethod aggregationMethod;


    public FacilityDataQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AggregationMethod getAggregationMethod() {
        return aggregationMethod;
    }

    public void setAggregationMethod(AggregationMethod aggregationMethod) {
        this.aggregationMethod = aggregationMethod;
    }

    /**
     * Used to validate the input given.
     *
     * @param value
     */
    public boolean validate(FacilityDataValue value) {
        return true; // do nothing
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacilityDataQuestion)) return false;

        FacilityDataQuestion that = (FacilityDataQuestion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s",getName());
    }
}