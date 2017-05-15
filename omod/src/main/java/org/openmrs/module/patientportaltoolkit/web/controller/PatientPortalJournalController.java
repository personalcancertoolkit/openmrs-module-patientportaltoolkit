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

    @RequestMapping( value = "/patientportaltoolkit/getjournalsforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalJournalsForPatient( @PathVariable( "patientId" ) String patientId)
            throws Exception
    {
        Person person=Context.getPersonService().getPersonByUuid(patientId);
        List<Object> entries = (List<Object>) ToolkitResourceUtil.generateJournals(Context.getService(JournalEntryService.class).getJournalEntryForPerson(Context.getUserService().getUsersByPerson(person,false).get(0), true));
        return entries;

    }

    @RequestMapping( value = "/patientportaltoolkit/getalljournals")
    @ResponseBody
    public Object getAllPatientPortalJournals()
            throws Exception
    {

        List<Object> entries = (List<Object>) ToolkitResourceUtil.generateJournals(Context.getService(JournalEntryService.class).getAllJournalEntries());
        return entries;

    }

    @RequestMapping( value = "/patientportaltoolkit/createjournal", method = RequestMethod.POST)
    @ResponseBody
    public void createPatientPortalJournals( @RequestBody String journalObject)
            throws Exception
    {
        JournalEntryService journalEntryService = Context.getService(JournalEntryService.class);
        JournalEntry journalEntry=ToolkitResourceUtil.transformJournal(journalObject);
        journalEntry.setCreator(Context.getAuthenticatedUser());
        journalEntryService.saveJournalEntry(journalEntry);
    }
}
