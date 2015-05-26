package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<JournalEntry> entries = Context.getService(JournalEntryService.class).getJournalEntryForPerson(person,true);
        return entries;

    }

    @RequestMapping( value = "/patientportaltoolkit/getjournals")
    @ResponseBody
    public Object getPatientPortalJournals()
            throws Exception
    {

        List<JournalEntry> entries = Context.getService(JournalEntryService.class).getAllJournalEntries();
        return entries;

    }
}
