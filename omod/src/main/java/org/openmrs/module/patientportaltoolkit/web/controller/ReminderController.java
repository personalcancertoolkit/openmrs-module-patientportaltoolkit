package org.openmrs.module.patientportaltoolkit.web.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maurya on 08/06/2015.
 */
@Controller
public class ReminderController {

    @RequestMapping( value = "/patientportaltoolkit/getremindersforpatient/{patientId}")
    @ResponseBody
    public Object getAllRemindersforPatient(@PathVariable( "patientId" ) String patientId)
    {
        Patient patient= Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateReminders(Context.getService(ReminderService.class).getReminders(patient));
    }
}
