package org.openmrs.module.patientportaltoolkit.web.rest.v1_0.controller;

import org.openmrs.module.patientportaltoolkit.web.rest.PatientPortalToolkitRestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maurya on 9/9/16.
 *
 */
@Controller
@RequestMapping("/rest/" + PatientPortalToolkitRestConstants.REST_NAMESPACE)
public class JournalEntryResourceController extends MainResourceController {
    /**
     * @see MainResourceController#getNamespace()
     */
    @Override
    public String getNamespace() {
        return PatientPortalToolkitRestConstants.REST_NAMESPACE;
    }

}
