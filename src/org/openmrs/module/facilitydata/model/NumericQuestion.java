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


/**
 * The Numeric question type. This question type will typically show a text field.
 * Example Numeric Question: "How many children were seen today?"
 */
public class NumericQuestion extends FacilityDataQuestion {
    private Integer id;

    /**
     * Minimum accepted value
     * Default: 0.0
     *
     * @see Double
     */
    private Double minValue = 0.0;

    /**
     * Maximum accepted value
     * Default: Double.MAX_VALUE
     *
     * @see Double#MAX_VALUE
     */
    private Double maxValue = Double.MAX_VALUE;

    /**
     * Whether or not decimals are allowed.
     */
    private boolean allowDecimals;

    public NumericQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public boolean validate(FacilityDataValue value) {
        return true; //TODO: complete
    }

    public boolean isAllowDecimals() {
        return allowDecimals;
    }

    public void setAllowDecimals(boolean allowDecimals) {
        this.allowDecimals = allowDecimals;
    }
}