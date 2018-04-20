package org.openmrs.module.facilitydata.web.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.*;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Resource(name = RestConstants.VERSION_1 + "/facilitydata/report", supportedClass = FacilityDataReport.class, supportedOpenmrsVersions = { "2.0.*,2.1.*" })
public class ReportResourceController extends DataDelegatingCrudResource<FacilityDataReport> {


    private FacilityDataService facilityDataService= Context.getService(FacilityDataService.class);
    Log log = LogFactory.getLog(this.getClass());

 /*   @RequestMapping(value="/get",method = RequestMethod.GET)
    public @ResponseBody SimpleObject test(){

        return new SimpleObject().add("message","success");
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody FacilityDataReport getReport(String uuid, Date startDate, Date endDate, Location location, List<FacilityDataValue> list){
        FacilityDataFormSchema schema=new FacilityDataFormSchema();
        schema.setUuid(uuid);

        FacilityDataReport report = facilityDataService.getReport(schema,startDate,endDate,location );
        for (FacilityDataFormSection section : schema.getSections()) {
            for (FacilityDataFormQuestion question : section.getQuestions()) {

            }
        }



        facilityDataService.saveReport(report);



        return report;
    }*/


    @Override
    public FacilityDataReport getByUniqueId(String s) {
        FacilityDataFormSchema schema = new FacilityDataFormSchema();
        schema.setSchemaId(4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // SimpleDateFormat sdf=new SimpleDateFormat("DD-MM-yyyy");
        Date startDate= null;
        try {
            startDate = sdf.parse("2018-03-15");
            System.out.println("Date :: "+startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Date startDate=new Date();
        Location location =new Location();
        location.setId(2);
        location.setLocationId(2);
        return facilityDataService.getReport(schema,startDate,startDate,location);
        //throw new ResourceDoesNotSupportOperationException("Resource doesnot support this operation ");

    }

    @Override
    protected void delete(FacilityDataReport facilityDataReport, String s, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException("Resource doesnot support this operation");

    }

    @Override
    public FacilityDataReport newDelegate() {
        return new FacilityDataReport();
    }

    @Override
    public FacilityDataReport save(FacilityDataReport facilityDataReport) {
        return facilityDataService.saveReport(facilityDataReport);
    }

    @Override
    public void purge(FacilityDataReport facilityDataReport, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException("Resource doesnot support this operation");

    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {

        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("schema");
            description.addProperty("facility");
            description.addProperty("startDate");
            description.addProperty("endDate");
            description.addProperty("values");


            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("schema");
            description.addProperty("facility");
            description.addProperty("startDate");
            description.addProperty("endDate");
            description.addProperty("values");

            return description;
        }
        return null;
    }

    @PropertyGetter(value = "facility")
    public static Object getFacility(FacilityDataReport instance){
        return  instance.getLocation();

    }

    @PropertySetter(value="facility")
    public static void setFacility(FacilityDataReport instance, Location facility){

        instance.setLocation(facility);

    }

    @PropertySetter(value="schema")
    public static void setFacility(FacilityDataReport instance, FacilityDataFormSchema schema){

        instance.setSchema(schema  );

    }
    @PropertyGetter(value = "schema")
    public static Object getSchema(FacilityDataReport instance){
        return  instance.getSchema();

    }
  //  @PropertyGetter(value = "values")
    public static Object getValues(FacilityDataReport instance){
        return null;

    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription d = new DelegatingResourceDescription();
        d.addRequiredProperty("schema");
        d.addRequiredProperty("facility");
        d.addRequiredProperty("startDate");
        d.addRequiredProperty("endDate");
        d.addRequiredProperty("values");

        return d;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription d = new DelegatingResourceDescription();
        d.addRequiredProperty("schema");
        d.addRequiredProperty("facility");
        d.addRequiredProperty("startDate");
        d.addRequiredProperty("endDate");
     //   d.addRequiredProperty("values");

        return d;
    }

  //  @PropertySetter("values")
    public static void setValues(FacilityDataReport instance, Set<FacilityDataValue> values) {
        Iterator var2 = values.iterator();

        while(var2.hasNext()) {
            FacilityDataValue o = (FacilityDataValue)var2.next();
            //instance.addValue(o);
        }

    }

    @PropertyGetter("display")
    public String getDisplay(FacilityDataReport instance){
        return  "i m display";
    }
}
