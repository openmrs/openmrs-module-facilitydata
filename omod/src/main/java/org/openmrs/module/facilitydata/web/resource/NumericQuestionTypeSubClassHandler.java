package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.NumericFacilityDataQuestionType;
import org.openmrs.module.facilitydata.service.FacilityDataService;
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
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

import java.util.ArrayList;
import java.util.List;

@SubClassHandler(supportedClass = NumericFacilityDataQuestionType.class, supportedOpenmrsVersions = {  "2.0.*,2.1.*" })
public class NumericQuestionTypeSubClassHandler extends BaseDelegatingSubclassHandler<FacilityDataQuestionType, NumericFacilityDataQuestionType> implements DelegatingSubclassHandler<FacilityDataQuestionType, NumericFacilityDataQuestionType> {


/*    private FacilityDataService facilityDataService=Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());*/

    public NumericQuestionTypeSubClassHandler() {
        //RESTWS-439

        System.out.println("NumericQuestionTypeSubClassHandler :::  NumericQuestionTypeSubClassHandler() ");
        allowedMissingProperties.add("minValue");
        allowedMissingProperties.add("maxValue");
        allowedMissingProperties.add("allowDecimals");

    }

    @Override
    public String getTypeName() {

        System.out.println("NumericQuestionTypeSubClassHandler :::  getTypeName() ");
        return "number";
    }

    @Override
    public PageableResult getAllByType(RequestContext requestContext) throws ResourceDoesNotSupportOperationException {
        System.out.println("NumericQuestionTypeSubClassHandler :::  getAllByType() ");

        FacilityDataService facilityDataService=Context.getService(FacilityDataService.class);
       // throw new RuntimeException(" I 8 this");
        List<NumericFacilityDataQuestionType> list=new ArrayList<>();
       for(FacilityDataQuestionType n:facilityDataService.getAllQuestionTypes()){
           if(n instanceof  NumericFacilityDataQuestionType){
               list.add((NumericFacilityDataQuestionType) n);
           }
       }
        return new NeedsPaging<NumericFacilityDataQuestionType>(list, requestContext);
    }

    @Override
    public NumericFacilityDataQuestionType newDelegate() {

        System.out.println("NumericQuestionTypeSubClassHandler :::  newDelegate() ");
       //throw new RuntimeException(" I 8 that");

       return new NumericFacilityDataQuestionType();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription d;
        QuestionTypeResourceController questionTypeResourceController;
        System.out.println("NumericQuestionTypeSubClassHandler :::  getRepresentationDescription() ");
        if (representation instanceof DefaultRepresentation) {
            questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

            d = questionTypeResourceController.getRepresentationDescription(representation);

            d.addProperty("minValue");
            d.addProperty("maxValue");
            d.addProperty("allowDecimals");

           // d.addProperty("v", Representation.REF);
            return d;
        } else if (representation instanceof FullRepresentation) {

            questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
            d = questionTypeResourceController.getRepresentationDescription(representation);

             d = questionTypeResourceController.getRepresentationDescription(representation);
            d.addProperty("minValue");
            d.addProperty("maxValue");
            d.addProperty("allowDecimals");

            return d;
        } else {
            return null;
        }
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        QuestionTypeResourceController questionTypeResourceController = (QuestionTypeResourceController)((RestService)Context.getService(RestService.class)).getResourceBySupportedClass(FacilityDataQuestionType.class);
        DelegatingResourceDescription d = questionTypeResourceController.getCreatableProperties();
        d.addRequiredProperty("minValue");
        d.addRequiredProperty("maxValue");
        d.addRequiredProperty("allowDecimals");
        d.removeProperty("type");
        return d;

    }
}
