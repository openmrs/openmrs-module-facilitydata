package org.openmrs.module.facilitydata.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.BlobFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.SubClassHandler;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@SubClassHandler(supportedClass = BlobFacilityDataQuestionType.class, supportedOpenmrsVersions = {  "2.0.*,2.1.*" })

public class BlobQuestionTypeSubClassHandler   extends BaseDelegatingSubclassHandler<FacilityDataQuestionType, BlobFacilityDataQuestionType> implements DelegatingSubclassHandler<FacilityDataQuestionType, BlobFacilityDataQuestionType> {

    @Override
    public String getTypeName() {
        return "blob";
    }

    @Override
    public PageableResult getAllByType(RequestContext requestContext) throws ResourceDoesNotSupportOperationException {
        return null;
    }

    @Override
    public BlobFacilityDataQuestionType newDelegate() {
        return new BlobFacilityDataQuestionType();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription d;
        QuestionTypeResourceController questionTypeResourceController;
        if (representation instanceof DefaultRepresentation) {
            questionTypeResourceController = (QuestionTypeResourceController)((RestService) Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

            d = questionTypeResourceController.getRepresentationDescription(representation);

           // d.addProperty("v", Representation.REF);
            return d;
        } else if (representation instanceof FullRepresentation) {

            questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

            d = questionTypeResourceController.getRepresentationDescription(representation);



            return d;
        } else {
            return null;
        }
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        QuestionTypeResourceController questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
        DelegatingResourceDescription d = questionTypeResourceController.getCreatableProperties();


        d.removeProperty("type");
        return d;
    }
}
