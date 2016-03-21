package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maurya on 17/06/2015.
 */
public class ConnectionsFragmentController {

    public void controller(PageModel model) {
        model.addAttribute("relationships", Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPerson(Context.getAuthenticatedUser().getPerson()));
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        model.addAttribute("user",Context.getAuthenticatedUser());
    }
}
