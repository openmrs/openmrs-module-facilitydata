package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.BasePageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/questiontype", supportedClass = FacilityDataQuestionType.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class QuestionTypeResourceController extends MetadataDelegatingCrudResource<FacilityDataQuestionType> {

    private  FacilityDataService facilityDataService=Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());


    @Override
    public FacilityDataQuestionType getByUniqueId(String s) {

        return facilityDataService.getQuestionTypeByUUID(s);
    }

    @Override
    public FacilityDataQuestionType newDelegate() {

        throw new ResourceDoesNotSupportOperationException("Can't save with parent type, please define its child in type attribute");
    }

    @Override
    public FacilityDataQuestionType save(FacilityDataQuestionType facilityDataQuestionType) {
        System.out.println("QuestionTypeResourceController :::  save() ");

        return facilityDataService.saveQuestionType(facilityDataQuestionType);
    }

    @Override
    public void purge(FacilityDataQuestionType facilityDataQuestionType, RequestContext requestContext) throws ResponseException {
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

            description.addProperty("retired");
          //  description.addSelfLink();
            //description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
          //  description.addProperty("display");
            description.addProperty("name");
            description.addProperty("description");
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


    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription d = new DelegatingResourceDescription();
        d.addRequiredProperty("name");
        d.addProperty("fieldStyle");
        d.addProperty("description");
        return d;
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {

        return new AlreadyPaged(context, facilityDataService.getAllQuestionTypes(),false);
    }

    public boolean hasTypesDefined() {
        return true;
    }
}


