package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.notification.MessageException;
import org.openmrs.notification.MessageService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maurya on 10/29/15.
 */
public class AppointmentsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model) {
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        Patient patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        List<Reminder> reminders=Context.getService(ReminderService.class).getReminders(patient);
        model.addAttribute("reminders",reminders);
        List<Reminder> alertableReminders = new ArrayList<>();
        for (Reminder r : reminders){
            if(r.getCompleteDate() == null) {
                alertableReminders.add(r);
            }
        }
        model.addAttribute("alertablereminders",alertableReminders);
    }


    public void markCompleted(FragmentModel model, @RequestParam(value = "reminderId", required = true) String reminderId) {

            Context.getService(ReminderService.class).markCompletedReminder(Context.getService(ReminderService.class).getRemindersById(reminderId));

    }

}
