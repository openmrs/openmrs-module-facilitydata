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

import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.model.StockQuestion;
import org.openmrs.module.facilitydata.model.BooleanCodedQuestion;

import java.util.List;

/**
 * Base class for all {@link CodedQuestion} question types.
 */
public abstract class CodedQuestion extends FacilityDataQuestion {
    /**
     * Specify a {@link List} of coded options.
     * <br/>
     * <p/>
     * These coded options will be stored in the {@link FacilityDataValue} valueText field as-is.
     *
     * @return an immutable list containing the coded options.
     * @see StockQuestion
     * @see BooleanCodedQuestion
     * @see FacilityDataValue
     */
    public abstract List<String> getCodedOptions();
}
