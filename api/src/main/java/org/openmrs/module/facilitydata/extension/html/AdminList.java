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

package org.openmrs.module.facilitydata.extension.html;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * Extension for the administration page to add links for the facilitydata module
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see Extension#getMediaType()
	 */
    @Override
    public MEDIA_TYPE getMediaType() {
        return Extension.MEDIA_TYPE.html;
    }

	@Override
	public String getRequiredPrivilege() {
		return "Enter Facility Data Reports,View Facility Data Reports,Manage Facility Data Reports";
	}

	/**
	 * @see AdministrationSectionExt#getTitle()
	 */
    @Override
    public String getTitle() {
        return "facilitydata.title";
    }

	/**
	 * @see AdministrationSectionExt#getLinks()
	 */
    @Override
    public Map<String, String> getLinks() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("module/facilitydata/dashboard.list", "facilitydata.dashboard");
        return Collections.unmodifiableMap(map);
    }
}