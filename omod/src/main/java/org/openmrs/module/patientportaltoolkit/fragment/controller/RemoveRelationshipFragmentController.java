package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Maurya on 23/06/2015.
 */
public class RemoveRelationshipFragmentController {
    public void controller() {

    }

    public void removeRelationship(FragmentModel model,@RequestParam(value = "relationshipId", required = true) String relationshipId) {
        PatientPortalRelationService patientPortalRelationService= Context.getService(PatientPortalRelationService.class);
        patientPortalRelationService.deletePatientPortalRelation(relationshipId,Context.getAuthenticatedUser());
    }
}

