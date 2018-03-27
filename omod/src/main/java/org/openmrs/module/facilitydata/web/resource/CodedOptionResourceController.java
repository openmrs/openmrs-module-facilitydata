package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataCodedOption;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/codedoption", supportedClass = FacilityDataCodedOption.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class CodedOptionResourceController extends MetadataDelegatingCrudResource<FacilityDataCodedOption> {

    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());
    @Override
    public FacilityDataCodedOption getByUniqueId(String s) {
        return facilityDataService.getCodedoptionByUuid(s);
    }

    @Override
    public FacilityDataCodedOption newDelegate() {
        return new FacilityDataCodedOption();
    }

    @Override
    public FacilityDataCodedOption save(FacilityDataCodedOption facilityDataCodedOption) {
        return facilityDataService.saveCodedOption(facilityDataCodedOption);
    }

    @Override
    public void purge(FacilityDataCodedOption facilityDataCodedOption, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        System.out.println("QuestionTypeResourceController :::  getRepresentationDescription() ");
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");

            description.addProperty("name");
            description.addProperty("description");
            description.addProperty("code");
            description.addProperty("optionOrder");
            description.addProperty("retired");

            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            //  description.addProperty("display");
            description.addProperty("name");
            description.addProperty("description");

            description.addProperty("code");
            description.addProperty("optionOrder");

            description.addProperty("creator");
            description.addProperty("dateCreated");
            description.addProperty("changedBy");
            description.addProperty("dateChanged");


            description.addProperty("retired");
            description.addProperty("dateRetired");
            description.addProperty("retiredBy");
            description.addProperty("retireReason");

            description.addProperty("fieldStyle");
            return description;
        }
        return null;
    }


}
