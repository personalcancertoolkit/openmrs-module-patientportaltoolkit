package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by maurya on 3/7/16.
 */
public class CommunitiesPageController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model) {

        log.info("Feedback/Contact Page Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")");
        model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
        model.addAttribute("pptutil",new PatientPortalUtil());

    }
}
