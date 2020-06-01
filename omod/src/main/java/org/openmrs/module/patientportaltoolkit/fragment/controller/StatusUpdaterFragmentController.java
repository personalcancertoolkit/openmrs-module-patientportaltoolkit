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
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Maurya on 19/06/2015.
 */
public class StatusUpdaterFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_STATUSUPDATER_FRAGMENT", pageRequest.getRequest()));
    }
    public String savePost(@RequestParam(value = "title", required = true) String title,
                       @RequestParam(value = "content", required = true) String content, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SAVE_POST", servletRequest, "title:", title, "content:", content));
        JournalEntry journalEntry = new JournalEntry(title,content);
        Context.getService(JournalEntryService.class).saveJournalEntry(journalEntry);
        //log.info("Save Post/Journal of -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        return null;
    }
    public void deletePost(@RequestParam(value = "remove-postId", required = true) String removePostId, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("remove Post", servletRequest, "removePostId:", removePostId));
        Context.getService(JournalEntryService.class).softDelete(Context.getService(JournalEntryService.class).getJournalEntry(removePostId));
    }
}
