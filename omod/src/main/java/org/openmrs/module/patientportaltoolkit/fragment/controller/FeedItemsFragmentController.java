package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by Maurya on 17/06/2015.
 */
public class FeedItemsFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("ReQUEST_FEEDITEMS_FRAGMENT", pageRequest.getRequest()));
            model.addAttribute("journals", Context.getService(JournalEntryService.class).getJournalEntryForPerson(Context.getAuthenticatedUser(),true));
    }
}
