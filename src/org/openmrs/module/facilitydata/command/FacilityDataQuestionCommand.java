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
package org.openmrs.module.facilitydata.command;

import org.openmrs.module.facilitydata.model.enums.AggregationMethod;
import org.openmrs.module.facilitydata.model.FacilityDataQuestion;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used for validating input from the page to create a {@link FacilityDataQuestion}.
 */
public class FacilityDataQuestionCommand extends BaseCommand {    
    private String type;
    private AggregationMethod method;
    private Double minValue;
    private Double maxValue;
    private Boolean allowDecimals;
    private List<String> types = Collections.unmodifiableList(Arrays.asList("NumericQuestion", "StockQuestion", "BooleanCodedQuestion"));
    private List<AggregationMethod> methods = Collections.unmodifiableList(Arrays.asList(AggregationMethod.LAST, AggregationMethod.SUM));

    public FacilityDataQuestionCommand() {       
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AggregationMethod getMethod() {
        return method;
    }

    public void setMethod(AggregationMethod method) {
        this.method = method;
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

    public Boolean getAllowDecimals() {
        return allowDecimals;
    }

    public void setAllowDecimals(Boolean allowDecimals) {
        this.allowDecimals = allowDecimals;
    }

    public List<AggregationMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<AggregationMethod> methods) {
        this.methods = methods;
    }
}