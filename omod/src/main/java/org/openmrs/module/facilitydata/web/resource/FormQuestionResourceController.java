package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormQuestion;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/formquestion", supportedClass = FacilityDataFormQuestion.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })

public class FormQuestionResourceController  extends MetadataDelegatingCrudResource<FacilityDataFormQuestion>
{

    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());

    @Override
    public FacilityDataFormQuestion getByUniqueId(String s) {
        return facilityDataService.getFacilityDataFormQuestion(s);
    }

    @Override
    public FacilityDataFormQuestion newDelegate() {
        return new FacilityDataFormQuestion();
    }

    @Override
    public FacilityDataFormQuestion save(FacilityDataFormQuestion facilityDataFormQuestion) {
        return facilityDataService.saveFacilityDataFormQuestion(facilityDataFormQuestion);
    }

    @Override
    public void purge(FacilityDataFormQuestion facilityDataFormQuestion, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        System.out.println("QuestionResourceController :::  getRepresentationDescription() ");
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("section");
            description.addProperty("questionNumber");
            description.addProperty("question");


            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("section");
            description.addProperty("questionNumber");
            description.addProperty("question");

            return description;
        }
        return null;
    }
}
