package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
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

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/formschema", supportedClass = FacilityDataFormSchema.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })

public class FormSchemaResourceController extends MetadataDelegatingCrudResource<FacilityDataFormSchema> {

    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());

    @Override
    public FacilityDataFormSchema getByUniqueId(String s) {
        return facilityDataService.getFacilityDataFormSchemaByUUID(s);
    }

    @Override
    public FacilityDataFormSchema newDelegate() {
        return new FacilityDataFormSchema();
    }

    @Override
    public FacilityDataFormSchema save(FacilityDataFormSchema facilityDataFormSchema) {
        return facilityDataService.saveFacilityDataFormSchema(facilityDataFormSchema);
    }

    @Override
    public void purge(FacilityDataFormSchema facilityDataFormSchema, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        System.out.println("QuestionResourceController :::  getRepresentationDescription() ");
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("form");
            description.addProperty("validFrom");
            description.addProperty("validTo");
            description.addProperty("sections");

            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("form");
            description.addProperty("validFrom");
            description.addProperty("validTo");
            description.addProperty("sections");
            return description;
        }
        return null;
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return new AlreadyPaged(context, facilityDataService.getAllFacilityDataFormSchemas(),false);

    }
}
