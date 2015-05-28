package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Maurya.
 */
public class PatientPortalRelationController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping( value = "/patientportaltoolkit/getrelationsforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalJournalsForPatient( @PathVariable( "patientId" ) String patientId)
            throws Exception
    {
        Person patient= Context.getPatientService().getPatientByUuid(patientId);
        List<Object> relations = Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPatient(patient);
        return relations;
    }
}
