package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.Reminder;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface ReminderDAO {

    void deleteReminder(Reminder reminder);

    List<Reminder> getAllRemindersByPatient(Patient patient);

    void saveReminder(Reminder reminder);
}