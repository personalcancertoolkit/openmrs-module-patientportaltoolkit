package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 17/06/2015.
 */
public class ConnectionsFragmentController {

    public void controller(PageModel model) {
        model.addAttribute("relationships", Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPerson(Context.getAuthenticatedUser().getPerson()));
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("user",Context.getAuthenticatedUser());
    }

    public void saveRelationshipfromEdit(FragmentModel model, @RequestParam(value = "relationshipId", required = true) String relationshipId,
                                        @RequestParam(value = "personRelationType", required = true) String personRelationType,
                                        @RequestParam(value = "personRelationSecurityLayer", required = true) String personRelationSecurityLayer) {
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();

        PatientPortalRelation ppr=Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(relationshipId);
        ppr.setRelationType(personRelationType);
        ppr.setShareType(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid(personRelationSecurityLayer));
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);

        //return "Success";
    }
}
