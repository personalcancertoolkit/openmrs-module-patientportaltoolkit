/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

        model.addAttribute("journals", Context.getService(JournalEntryService.class)
                .getJournalEntryForPerson(Context.getAuthenticatedUser(), true));
        model.addAttribute("user", Context.getAuthenticatedUser());
    }
}
