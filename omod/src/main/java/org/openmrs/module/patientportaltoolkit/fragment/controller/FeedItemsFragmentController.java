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
            model.addAttribute("journals", Context.getService(JournalEntryService.class).getJournalEntryForPerson(Context.getAuthenticatedUser(),true));
    }
}
