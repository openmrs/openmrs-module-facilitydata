package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydatamodule/questiontype", supportedClass = FacilityDataQuestionType.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class QuestionTypeResourceController extends MetadataDelegatingCrudResource<FacilityDataQuestionType> {

    private  FacilityDataService facilityDataService=Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());


    @Override
    public FacilityDataQuestionType getByUniqueId(String s) {
        log.debug("Searching Question Type by UUID :: "+s);
        return facilityDataService.getQuestionTypeByUUID(s);
    }

    @Override
    public FacilityDataQuestionType newDelegate() {
        return new CodedFacilityDataQuestionType();
    }

    @Override
    public FacilityDataQuestionType save(FacilityDataQuestionType facilityDataQuestionType) {

        return facilityDataService.saveQuestionType(facilityDataQuestionType);
    }

    @Override
    public void purge(FacilityDataQuestionType facilityDataQuestionType, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("display");
            description.addProperty("name");
            description.addProperty("description");

            description.addProperty("retired");
            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("display");
            description.addProperty("name");
            description.addProperty("description");

        }
        return null;
    }


}
