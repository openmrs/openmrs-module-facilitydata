package org.openmrs.module.facilitydata.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;

/**
 * Constants for use within the module
 */
public class FacilityDataConstants {
	
	// PRIVILEGE CONSTANTS
	
    public static final String ENTER = "Enter Facility Data Reports";
    public static final String VIEW = "View Facility Data Reports";
    public static final String MANAGE = "Manage Facility Data Reports";
    
    // GLOBAL PROPERTIES
    
    public static List<Location> getSupportedFacilities() {
    	List<Location> ret = Context.getLocationService().getAllLocations();
    	String limitToFacilitiesGp = Context.getAdministrationService().getGlobalProperty("facilitydata.unsupportedFacilities");
    	if (StringUtils.isNotBlank(limitToFacilitiesGp)) {
    		List<String> locIds = Arrays.asList(limitToFacilitiesGp.split(","));
    		for (Iterator<Location> i = ret.iterator(); i.hasNext();) {
    			Location l = i.next();
    			if (locIds.contains(l.getLocationId().toString())) {
    				i.remove();
    			}
    		}
    	}
    	return ret;
    }
}
