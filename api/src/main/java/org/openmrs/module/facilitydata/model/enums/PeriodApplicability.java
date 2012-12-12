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
package org.openmrs.module.facilitydata.model.enums;

/**
 * Defines how a question relates to the period of the report within which it is being asked.
 * For example, the question "Number of patients enrolled by end of period" would have a {@link PeriodApplicability.AT_END_OF_PERIOD},
 * whereas the question "Number of patients enrolled during the period" would have a {@link PeriodApplicability.DURING_PERIOD}
 */
public enum PeriodApplicability {
    AT_START_OF_PERIOD,
    DURING_PERIOD,
    AT_END_OF_PERIOD
}
