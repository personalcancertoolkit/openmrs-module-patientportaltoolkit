/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
@Controller
public class PatientPortalJournalController {

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/patientportaltoolkit/getjournalsforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalJournalsForPatient(@PathVariable("patientId") String patientId)
            throws Exception {
        Person person = Context.getPersonService().getPersonByUuid(patientId);

        @SuppressWarnings("unchecked")
        List<Object> entries = (List<Object>) ToolkitResourceUtil
                .generateJournals(Context.getService(JournalEntryService.class).getJournalEntryForPerson(
                        Context.getUserService().getUsersByPerson(person, false).get(0), true));
        return entries;

    }

    @RequestMapping(value = "/patientportaltoolkit/getalljournals")
    @ResponseBody
    public Object getAllPatientPortalJournals()
            throws Exception {

        @SuppressWarnings("unchecked")
        List<Object> entries = (List<Object>) ToolkitResourceUtil
                .generateJournals(Context.getService(JournalEntryService.class).getAllJournalEntries());
        return entries;

    }

    @RequestMapping(value = "/patientportaltoolkit/createjournal", method = RequestMethod.POST)
    @ResponseBody
    public void createPatientPortalJournals(@RequestBody String journalObject)
            throws Exception {
        JournalEntryService journalEntryService = Context.getService(JournalEntryService.class);
        JournalEntry journalEntry = ToolkitResourceUtil.transformJournal(journalObject);
        journalEntry.setCreator(Context.getAuthenticatedUser());
        journalEntryService.saveJournalEntry(journalEntry);
    }
}
