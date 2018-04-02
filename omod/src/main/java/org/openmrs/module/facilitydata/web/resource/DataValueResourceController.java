package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataValue;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
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
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("fromDate");
            description.addProperty("toDate");
            description.addProperty("question");
            description.addProperty("valueNumeric");
            description.addProperty("valueCoded");
            description.addProperty("valueText");
            description.addProperty("valueBlob");
            description.addProperty("comments");
            description.addProperty("documentValue");
            description.addProperty("display");

        /*    description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);*/
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("fromDate");
            description.addProperty("toDate");
            description.addProperty("question");
            description.addProperty("valueNumeric");
            description.addProperty("valueCoded");
            description.addProperty("valueText");
            description.addProperty("valueBlob");
            description.addProperty("comments");
            description.addProperty("documentValue");
            description.addProperty("display");

            return description;
        }
        return null;
    }

    @PropertyGetter("display")
    public String getDisplay(FacilityDataValue value){
         return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("coded",
                 value.getValueCoded()).append("numeric", value.getValueNumeric()).append("text",value.getValueText()
         ).append("Document",value.getDocumentValue()).append("facility",value.getFacility()).build();
    }
}
