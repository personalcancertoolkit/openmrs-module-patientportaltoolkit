package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 7/13/16.
 */
public class HeaderForAppFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {

        model.addAttribute("username", Context.getAuthenticatedUser().getUsername());
        log.info(PPTLogAppender.appendLog("REQUEST_NAVIGATION_FRAGMENT", pageRequest.getRequest()));

    }

}
