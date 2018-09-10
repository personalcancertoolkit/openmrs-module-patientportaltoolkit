package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageModel;
import java.util.List;

public class AdminDashBoardPageController {

    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model, PageRequest pageRequest) {

        List<CancerCommunityResources> cancerComunityData = Context.getService(CancerCommunityResourcesService.class).getCancerCommunityResourcesService();
        if(cancerComunityData != null) {
            model.addAttribute("CancerCommunityData", cancerComunityData);
        }
        else {
            model.addAttribute("CancerCommunityData", null);
        }
        log.info(PPTLogAppender.appendLog("REQUEST_ADMINDASHBOARD_PAGE", pageRequest.getRequest()));
    }
}



