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
package org.openmrs.module.facilitydata.activator;

import org.openmrs.module.Activator;
import org.apache.log4j.Logger;


public class FacilityDataModuleActivator implements Activator {

    private static final Logger log = Logger.getLogger(FacilityDataModuleActivator.class);

    public void startup() {
        log.info("Starting FacilityData Module");
    }


    public void shutdown() {
        log.info("Shutting down FacilityData Module");
    }
}