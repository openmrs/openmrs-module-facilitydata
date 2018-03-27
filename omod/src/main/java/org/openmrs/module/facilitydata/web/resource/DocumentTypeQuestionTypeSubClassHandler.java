package org.openmrs.module.facilitydata.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.DocumentTypeFacilityDataQuestionType;
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

@SubClassHandler(supportedClass = DocumentTypeFacilityDataQuestionType.class, supportedOpenmrsVersions = {  "2.0.*,2.1.*" })

public class DocumentTypeQuestionTypeSubClassHandler   extends BaseDelegatingSubclassHandler<FacilityDataQuestionType, DocumentTypeFacilityDataQuestionType> implements DelegatingSubclassHandler<FacilityDataQuestionType, DocumentTypeFacilityDataQuestionType> {
    @Override
    public String getTypeName() {
        return "document";
    }

    @Override
    public PageableResult getAllByType(RequestContext requestContext) throws ResourceDoesNotSupportOperationException {
        return null;
    }

    @Override
    public DocumentTypeFacilityDataQuestionType newDelegate() {
        return new DocumentTypeFacilityDataQuestionType();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription d;
        QuestionTypeResourceController questionTypeResourceController;
        if (representation instanceof DefaultRepresentation) {
            questionTypeResourceController = (QuestionTypeResourceController)((RestService) Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

            d = questionTypeResourceController.getRepresentationDescription(representation);
            d.addProperty("documentType");
          //  d.addProperty("v", Representation.REF);
            return d;
        } else if (representation instanceof FullRepresentation) {

            questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

            d = questionTypeResourceController.getRepresentationDescription(representation);
            d.addProperty("documentType");


            return d;
        } else {
            return null;
        }
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        QuestionTypeResourceController questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
        DelegatingResourceDescription d = questionTypeResourceController.getCreatableProperties();
        d.addRequiredProperty("documentType");

        d.removeProperty("type");
        return d;
    }
}
