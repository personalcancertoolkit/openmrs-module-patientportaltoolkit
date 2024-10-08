/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.page.controller.help;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 3/7/16.
 */
public class WalkthroughsPageController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        Context.getService(PatientPortalMiscService.class).logEvent("VIDEO_WALKTHROUGHS_PAGE_VIEWED", null);
        log.info(PPTLogAppender.appendLog("REQUEST_FEEDBACK_PAGE", pageRequest.getRequest()));

        model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
        model.addAttribute("pptutil", new PatientPortalUtil());
        model.addAttribute("contextUser", Context.getAuthenticatedUser());
    }
}
