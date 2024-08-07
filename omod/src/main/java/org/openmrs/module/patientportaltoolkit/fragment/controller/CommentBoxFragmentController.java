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
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Maurya on 19/06/2015.
 */
public class CommentBoxFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model,
            @FragmentParam(value = "parentId", required = true) String parentId, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_COMMENTBOX_FRAGMENT", pageRequest.getRequest(), "parentId:",
                parentId));
        model.addAttribute("parentId", parentId);
    }

    public void saveComment(FragmentModel model,
            @RequestParam(value = "commentContent", required = true) String content,
            @RequestParam(value = "parentId", required = true) String parentId, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SAVE_NEW_COMMENT", servletRequest, "commentContent:", content, "parentId:",
                parentId));

        String title = Context.getAuthenticatedUser().getGivenName() + " "
                + Context.getAuthenticatedUser().getFamilyName();
        JournalEntry journalEntry = new JournalEntry(title, content);
        journalEntry.setParentEntryId(Context.getService(JournalEntryService.class).getJournalEntry(parentId).getId());
        Context.getService(JournalEntryService.class).saveJournalEntry(journalEntry);
    }
}
