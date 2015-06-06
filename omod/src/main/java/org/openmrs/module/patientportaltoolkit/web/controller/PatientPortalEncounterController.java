package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */

@Controller
public class PatientPortalEncounterController {

    protected final Log log = LogFactory.getLog(getClass());
    @RequestMapping( value = "/patientportaltoolkit/getallencountertypes")
    @ResponseBody
    public Object getPatientPortalEncounterTypes()
    {
        return ToolkitResourceUtil.generateEncounterTypes(Context.getEncounterService().getAllEncounterTypes());
    }

    @RequestMapping( value = "/patientportaltoolkit/getallencountersforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalEncountersforPatient( @PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateEncounters(Context.getEncounterService().getEncountersByPatient(patient));
    }
}
