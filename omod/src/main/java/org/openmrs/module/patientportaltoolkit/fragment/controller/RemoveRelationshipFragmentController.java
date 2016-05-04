package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Maurya on 23/06/2015.
 */
public class RemoveRelationshipFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void removeRelationship(FragmentModel model,@RequestParam(value = "relationshipId", required = true) String relationshipId) {
        PatientPortalRelationService patientPortalRelationService= Context.getService(PatientPortalRelationService.class);
        patientPortalRelationService.deletePatientPortalRelation(relationshipId,Context.getAuthenticatedUser());
        log.info("Relation/Connection Removed for relation id -" + relationshipId + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}

