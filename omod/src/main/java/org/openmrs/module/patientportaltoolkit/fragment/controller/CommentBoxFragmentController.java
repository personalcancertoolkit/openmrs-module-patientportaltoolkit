/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
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

/**
 * Created by Maurya on 19/06/2015.
 */
public class CommentBoxFragmentController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(FragmentModel model,
                           @FragmentParam(value="parentId", required=true) String parentId, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_COMMENTBOX_FRAGMENT", pageRequest.getRequest(), "parentId:",parentId));
        model.addAttribute("parentId",parentId);
    }

    public void saveComment(FragmentModel model,@RequestParam(value = "commentContent", required = true) String content,@RequestParam(value = "parentId", required = true) String parentId, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("SAVE_NEW_COMMENT", pageRequest.getRequest(), "commentContent:", content, "parentId:",parentId));
        //log.info("Save New Comment by -" + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")");
        String title=Context.getAuthenticatedUser().getGivenName()+" "+Context.getAuthenticatedUser().getFamilyName();
        JournalEntry journalEntry = new JournalEntry(title,content);
        journalEntry.setParentEntryId(Context.getService(JournalEntryService.class).getJournalEntry(parentId).getId());
        Context.getService(JournalEntryService.class).saveJournalEntry(journalEntry);
    }
}
