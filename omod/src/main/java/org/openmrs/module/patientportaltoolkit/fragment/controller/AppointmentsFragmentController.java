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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by maurya on 10/29/15.
 */
public class AppointmentsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model) {
        User user = Context.getAuthenticatedUser();
        List<Reminder> alertableReminders = new ArrayList<>();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        if(Context.getAuthenticatedUser().getPerson().isPatient()) {
            Patient patient = Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
            List<Reminder> reminders = Context.getService(ReminderService.class).getReminders(patient);
            model.addAttribute("reminders", reminders);
            for (Reminder r : reminders) {
                if (r.getCompleteDate() == null) {
                    alertableReminders.add(r);
                }
            }
        }
        model.addAttribute("alertablereminders",alertableReminders);
    }


    public void markCompleted(FragmentModel model, @RequestParam(value = "reminderId", required = true) String reminderId, @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate, @RequestParam(value = "doctorName", required = true) String doctorName, @RequestParam(value = "comments", required = true) String comments) {

        //System.out.println("121212121212"+markCompletedDate);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(markCompletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Context.getService(ReminderService.class).markCompletedReminder(reminderId, date, doctorName, comments);

    }

    public void markScheduled(FragmentModel model, @RequestParam(value = "reminderId", required = true) String reminderId, @RequestParam(value = "markScheduledDate", required = true) String markScheduledDate) {

        //System.out.println("121212121212"+markCompletedDate);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(markScheduledDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Context.getService(ReminderService.class).markScheduledReminder(reminderId,date);

    }

}
