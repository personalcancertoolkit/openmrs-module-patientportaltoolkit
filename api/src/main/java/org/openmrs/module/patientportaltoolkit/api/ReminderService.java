/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface ReminderService extends OpenmrsService {

    @Transactional(readOnly = true)
    Reminder getRemindersById(String Id);

    @Transactional(readOnly = true)
    List<Reminder> getAllRemindersByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<Reminder> getRemindersCompleted(Patient patient);

    @Transactional
    List<Reminder> getReminders(Patient pat);

    @Transactional
    Reminder markCompletedReminder(Reminder reminder);

    @Transactional
    Reminder saveReminder(Reminder Reminder);

    @Transactional
    Reminder markCompletedReminder(String reminderID,Date markCompleteDate,String doctorsName, String comments, Patient patient, Concept followupConcept);

/*    @Transactional
    Reminder markScheduledReminder(String reminderId, Date date);*/

    @Transactional
    GuidelineConditionSet generateGuidelineConditionSet(Patient patient);

    @Transactional
    List<Reminder> generateRemindersbyGuidelineConditions(Patient patient);

}
