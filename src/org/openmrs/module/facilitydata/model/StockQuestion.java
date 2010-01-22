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


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Stock Question type is a special {@link CodedQuestion}.
 * Example Stock question would be vaccinations that are on site: their stock status.
 */
public class StockQuestion extends CodedQuestion {
    private static final String delimiter = ":";

    /**
     * Number of days out of stock
     */
    private Integer days;

    /**
     * Reason and/or comments.
     */
    private String reason;

    public StockQuestion() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public List<String> getCodedOptions() {
        return Collections.unmodifiableList(Arrays.asList("not_stocked_out", "stocked_out", "expired", "not_applicable"));
    }


    public boolean validate(FacilityDataValue value) {
        if (value.getValueText().equals("not_stocked_out") || value.getValueText().equals("not_applicable"))
            return true;
        else {
            String[] split = value.getComments().split(":");
            if (split.length == 2) {
                try {
                    int days = Integer.parseInt(split[0]);
                    this.days = days;
                } catch (NumberFormatException e) {
                    // no days
                }
                this.reason = split[1];
            } else {
                // we need to check if just does was entered *OR* just a reason
                split = value.getComments().split(":");
                try {
                    int days = Integer.parseInt(split[0]);
                    this.days = days;
                } catch (NumberFormatException e) {
                    // no days
                }
                return true;
            }
            return true;
        }

    }
}