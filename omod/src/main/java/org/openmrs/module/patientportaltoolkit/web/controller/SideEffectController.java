package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maurya on 02/06/2015.
 */

@Controller
public class SideEffectController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping( value = "/patientportaltoolkit/getallsideeffects")
     @ResponseBody
     public Object getAllSideEffects()
    {
        return ToolkitResourceUtil.generateSideEffects(Context.getService(SideEffectService.class).getAllSideEffects());
    }

    @RequestMapping( value = "/patientportaltoolkit/getsideeffectsforpatient/{patientId}")
    @ResponseBody
    public Object getAllSideEffectsforPatient(@PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateConcepts(Context.getService(SideEffectService.class).getAllSideEffectsForPatient(patient));
    }
}
