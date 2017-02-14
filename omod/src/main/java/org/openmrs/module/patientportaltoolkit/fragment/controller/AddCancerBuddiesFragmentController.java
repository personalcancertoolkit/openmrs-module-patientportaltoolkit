package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 2/13/17.
 */
public class AddCancerBuddiesFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_ADDCANCERBUDDIES_FRAGMENT", pageRequest.getRequest()));
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
    }
}
