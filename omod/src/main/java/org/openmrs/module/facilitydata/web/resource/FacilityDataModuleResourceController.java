package org.openmrs.module.facilitydata.web.resource;

import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/facilitydatamodule")
public class FacilityDataModuleResourceController extends MainResourceController {
    @Override
    public String getNamespace() {
        return  RestConstants.VERSION_1 + "/facilitydatamodule" ;
    }
}
