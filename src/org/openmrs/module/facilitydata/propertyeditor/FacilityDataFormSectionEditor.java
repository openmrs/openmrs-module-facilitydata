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
package org.openmrs.module.facilitydata.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.springframework.util.StringUtils;

public class FacilityDataFormSectionEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	FacilityDataService svc = Context.getService(FacilityDataService.class);
        if (StringUtils.hasText(text)) {
            setValue(svc.getFacilityDataFormSection(Integer.parseInt(text)));
        } else {
            setValue(null);
        }
    }

    @Override
    public String getAsText() {
        FacilityDataFormSection section = (FacilityDataFormSection) getValue();
        if (section != null) {
            return section.toString();
        } else {
            return "";
        }
    }
}
