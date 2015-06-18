package org.openmrs.module.patientportaltoolkit.fragment.controller;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by Maurya on 17/06/2015.
 */
public class SideEffectsFragmentController {

    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null)
        model.addAttribute("concepts",Context.getService(SideEffectService.class).getAllSideEffectsForPatient(patient));
        else
            model.addAttribute("concepts",null);
    }
}