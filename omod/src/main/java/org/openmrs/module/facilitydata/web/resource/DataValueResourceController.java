package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/datavalue", supportedClass = FacilityDataValue.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })

public class DataValueResourceController extends DataDelegatingCrudResource<FacilityDataValue>{


    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());

    @Override
    public FacilityDataValue getByUniqueId(String s) {
        return facilityDataService.getFacilityDataValueByUuid(s);
    }

    @Override
    protected void delete(FacilityDataValue facilityDataValue, String s, RequestContext requestContext) throws ResponseException {
        facilityDataService.voidFacilityDataValue(facilityDataValue,s);
    }

    @Override
    public FacilityDataValue newDelegate() {
        return new FacilityDataValue() ;
    }

    @Override
    public FacilityDataValue save(FacilityDataValue facilityDataValue) {
        return facilityDataService.saveFacilityDataValue(facilityDataValue);
    }

    @Override
    public void purge(FacilityDataValue facilityDataValue, RequestContext requestContext) throws ResponseException {
        facilityDataService.deleteFacilityDataValue(facilityDataValue);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        return null;
    }
}
