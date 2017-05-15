/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.Reminder;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface ReminderDAO {

    void deleteReminder(Reminder reminder);

    List<Reminder> getAllRemindersByPatient(Patient patient);

    Reminder saveReminder(Reminder reminder);

    public Reminder getReminder(Integer id);

    public List<Reminder> getReminders(Patient pat);

    public Reminder getReminder(Patient pat, Concept careType, Date targetDate);

    /**
     * Get reminders recommended by patient's providers
     *
     * @param pat a given patient
     * @return list of reminders/follow up care recommended
     */
    List<Reminder> getRemindersByProvider(Patient pat);

    public List<Reminder> getRemindersCompleted(Patient pat);
}