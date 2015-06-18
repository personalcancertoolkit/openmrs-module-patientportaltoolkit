package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by Maurya on 17/06/2015.
 */
public class FeedItemsFragmentController {

    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null)
            model.addAttribute("journals", Context.getService(JournalEntryService.class).getJournalEntryForPerson(patient,true));
        else
            model.addAttribute("journals",null);
    }
}
