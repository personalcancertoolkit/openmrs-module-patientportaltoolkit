package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Maurya on 17/06/2015.
 */
public class ConnectionsFragmentController {

    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null)
            model.addAttribute("relationships", Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPatient(patient));
        else
            model.addAttribute("relationships",null);
    }
}
