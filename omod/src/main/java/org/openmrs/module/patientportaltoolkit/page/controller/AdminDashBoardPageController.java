package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageModel;

public class AdminDashBoardPageController {

    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model, PageRequest pageRequest) {

        log.info(PPTLogAppender.appendLog("REQUEST_ADMINDASHBOARD_PAGE", pageRequest.getRequest()));
    }
}



