/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
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
    Reminder getReminderByIdOrGuidelineData(String reminderId, Patient patient, String conceptId, Date targetDate);

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
    Reminder markCompletedReminder(Reminder reminder, Date markCompleteDate, String doctorsName, String comments);

    @Transactional
    Reminder modifyCompletedReminder(Reminder reminder, Date markCompleteDate, String doctorsName, String comments);
    
    @Transactional
    Reminder modifyTargetDate(Reminder reminder, Date newTargetDate);
    
    @Transactional
    Reminder removeReminder(Reminder reminder);
    
    @Transactional
    Reminder addReminder(Reminder reminder);
    
/*    
    @Transactional
    Reminder markScheduledReminder(String reminderId, Date date);
*/

    @Transactional
    GuidelineConditionSet generateGuidelineConditionSet(Patient patient);

    @Transactional
    List<Reminder> generateRemindersbyGuidelineConditions(Patient patient);
    
    @Transactional
    Reminder generateReminderFromGuidelineData(Patient patient, String conceptId, Date targetDate);
    
    @Transactional
    Reminder generateReminderFromGuidelineData(Patient patient, Concept followupConcept, Date targetDate);

}
