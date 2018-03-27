package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormSection;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/formsection", supportedClass = FacilityDataFormSection.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })

public class FormSectionResourceController   extends MetadataDelegatingCrudResource<FacilityDataFormSection> {

    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());

    @Override
    public FacilityDataFormSection getByUniqueId(String s) {
        return facilityDataService.getFacilityDataFormSectionByUUID(s);
    }

    @Override
    public FacilityDataFormSection newDelegate() {
        return new FacilityDataFormSection();
    }

    @Override
    public FacilityDataFormSection save(FacilityDataFormSection facilityDataFormSection) {
        return facilityDataService.saveFacilityDataFormSection(facilityDataFormSection);
    }

    @Override
    public void purge(FacilityDataFormSection facilityDataFormSection, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        System.out.println("QuestionResourceController :::  getRepresentationDescription() ");
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("schema");
            description.addProperty("questions");
            description.addProperty("sectionNumber");
            description.addProperty("sections");

            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("schema");
            description.addProperty("questions");
            description.addProperty("sectionNumber");

            return description;
        }
        return null;
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return new AlreadyPaged(context, null,false);

    }
}
