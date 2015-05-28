package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Object> entries = (List<Object>) Context.getService(JournalEntryService.class).getJournalEntryForPerson(person,true);
        return entries;

    }

    @RequestMapping( value = "/patientportaltoolkit/getalljournals")
    @ResponseBody
    public Object getAllPatientPortalJournals()
            throws Exception
    {

        List<Object> entries = (List<Object>) Context.getService(JournalEntryService.class).getAllJournalEntries();
        return entries;

    }

    @RequestMapping( value = "/patientportaltoolkit/createjournal", method = RequestMethod.POST)
    @ResponseBody
    public void createPatientPortalJournals( @RequestBody String journalObject)
            throws Exception
    {
        JournalEntryService journalEntryService = Context.getService(JournalEntryService.class);
        JournalEntry journalEntry=ToolkitResourceUtil.transformJournal(journalObject);
        journalEntry.setCreator(Context.getAuthenticatedUser().getPerson());
        journalEntryService.saveJournalEntry(journalEntry);
    }
}
