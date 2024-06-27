/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.util;

import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.EventLog;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;

public class AppointmentReminderUtil {

    public static final String EMAIL_ATTRIBUTE = "Email";

    public static void run() {

        String nowDateString = (new Date()).toString();

        System.out.println(nowDateString + "| Starting AppointmentReminderUtil run");

        // If the most recent run happened today, quit early. No need to run again today
        if (reminderProcessHasBeenRunToday()) {
            System.out.println(nowDateString + "| Quiting early because AppointmentReminderUtil has already run today");
            return;
        }

        // Assume that the emails will get sent successfully and every patient that
        // needs to be notified will be
        boolean allPatientsThatNeedToBeNotifiedHaveBeen = true;

        // Get today's date
        DateTime todayDate = new LocalDate().toDateTimeAtStartOfDay();
        List<AppointmentReminder> apptReminders = getApptReminders();

        // Get a list of all active patients with their appointments/visits
        List<PatientWithVisit> patientsWithVisits = getPatientsWithTheirVisits();

        for (PatientWithVisit patientWithVisit : patientsWithVisits) {
            Patient patient = patientWithVisit.patient;
            boolean hasVisitThatRequiresNotification = false;

            for (AppointmentReminder apptReminder : apptReminders) {
                if (hasVisitThatRequiresNotification == false) {

                    // There is potential for overlap with historical and upcoming appointments if
                    // the window for searching for visits is opened wide enough.
                    // The initial intent is to look for visits as near as a week in the future and
                    // a month in the future and two weeks in the past. With a two day window
                    // around any of these, the potential for overlap does not exist.

                    DateTime predictedApptDate;
                    if (apptReminder.type == AppointmentReminder.HISTORICAL) {
                        predictedApptDate = todayDate.minusDays(apptReminder.numberOfDays);
                    } else {
                        predictedApptDate = todayDate.plusDays(apptReminder.numberOfDays);
                    }

                    DateTime reminderNotifyWindowOpen = predictedApptDate.minusDays(1);
                    // Adding two days instead of one to account for starting todaysDate at the
                    // start of the day
                    DateTime reminderNotifyWindowClose = predictedApptDate.plusDays(2);

                    for (VisitDetail visit : patientWithVisit.visits) {
                        if (hasVisitThatRequiresNotification == false) {

                            if ((reminderNotifyWindowOpen.toDate().before(visit.targetDate)
                                    || reminderNotifyWindowOpen.toDate().equals(visit.targetDate))
                                    && (reminderNotifyWindowClose.toDate().after(visit.targetDate))
                                    && visit.status == Reminder.CREATED_STATUS) {

                                hasVisitThatRequiresNotification = true;

                                System.out.println(nowDateString + "| AppointmentReminderUtil. Patient with Id: "
                                        + patient.getPersonId()
                                        + " and UUID: " + patient.getUuid()
                                        + " requires notification for visit with targetDate of: "
                                        + visit.targetDate.toString());
                            }
                        }
                    }
                }
            }

            // Check if the patient has already been notified
            if (hasVisitThatRequiresNotification && !isPatientAlreadyNotified(patient)) {
                boolean emailSuccessfullySent = sendEmailNotificationTo(patient);
                if (emailSuccessfullySent) {
                    sendSphereNotificationTo(patient);
                    markPatientAsNotified(patient);
                } else {
                    allPatientsThatNeedToBeNotifiedHaveBeen = false;
                }
            }
        }

        if (allPatientsThatNeedToBeNotifiedHaveBeen) {
            // Keep track that the run today was successful, so that we don't run it again
            // today
            Context.getService(PatientPortalMiscService.class).logEvent(EventLog.APPOINTMENT_REMINDER_RUN, null);
            System.out.println(nowDateString + "| Completed AppointmentReminderUtil run");
        } else {
            System.out.println(
                    nowDateString
                            + "| AppointmentReminderUtil run completed, but not all patients that needed to be notified were successfully notified. Will attempt another run soon");
        }
    }

    private static List<AppointmentReminder> getApptReminders() {
        AppointmentReminder r1 = new AppointmentReminder();
        r1.type = AppointmentReminder.UPCOMING;
        r1.description = "One Month";
        r1.numberOfDays = 30;

        AppointmentReminder r2 = new AppointmentReminder();
        r2.type = AppointmentReminder.UPCOMING;
        r2.description = "One Week";
        r2.numberOfDays = 7;

        AppointmentReminder r3 = new AppointmentReminder();
        r3.type = AppointmentReminder.HISTORICAL;
        r3.description = "Two Weeks";
        r3.numberOfDays = 14;

        List<AppointmentReminder> apptReminders = new ArrayList<AppointmentReminder>();
        apptReminders.add(r1);
        apptReminders.add(r2);
        apptReminders.add(r3);
        return apptReminders;
    }

    // private static List<PatientWithVisit> getTestPatientWithVisits() {

    // Patient patient =
    // Context.getPatientService().getPatientByUuid("cb2618ed-ebbc-429c-8d20-f282d2fe61fb");
    // List<PatientWithVisit> patientsWithVisits = new ArrayList<>();

    // try {
    // List<VisitDetail> visits = generateVisits(patient);
    // PatientWithVisit pwv = new PatientWithVisit(patient, visits);
    // patientsWithVisits.add(pwv);
    // } catch (Exception e) {
    // // generateVisits usually throws an exception if the patient does not have a
    // // surgery date. If a patient does not have a surgery date, the visit dates
    // // cannot be computed, and as such, we will ignore that patient
    // }

    // return patientsWithVisits;
    // }

    private static List<PatientWithVisit> getPatientsWithTheirVisits() {

        List<Patient> patients = Context.getPatientService().getAllPatients(false);
        List<PatientWithVisit> patientsWithVisits = new ArrayList<>();

        for (Patient patient : patients) {
            try {
                List<VisitDetail> visits = generateVisits(patient);
                PatientWithVisit pwv = new PatientWithVisit(patient, visits);
                patientsWithVisits.add(pwv);
            } catch (Exception e) {
                // generateVisits usually throws an exception if the patient does not have a
                // surgery date. If a patient does not have a surgery date, the visit dates
                // cannot be computed, and as such, we will ignore that patient
            }
        }
        return patientsWithVisits;
    }

    private static boolean isPatientAlreadyNotified(Patient patient) {

        User user = Context.getUserService().getUsersByPerson(patient, false).get(0);
        EventLog latest = Context.getService(PatientPortalMiscService.class)
                .getLatestAppointmentReminderNotificationSentForUser(user);

        DateTime startOfToday = new LocalDate().toDateTimeAtStartOfDay();
        DateTime startOfCheckWindow = startOfToday.minusDays(2);

        if (latest == null || startOfCheckWindow.toDate().after(latest.getCreatedAt())) {
            return false;
        }
        return true;
    }

    private static void markPatientAsNotified(Patient patient) {
        User user = Context.getUserService().getUsersByPerson(patient, false).get(0);
        Context.getService(PatientPortalMiscService.class).logAppointmentReminderNotificationSentEvent(user);
    }

    private static boolean sendSphereNotificationTo(Patient patient) {
        boolean successfullySent = false;
        String content = "Hello " + patient.getGivenName() + ",<br/><br/>" +

                "Please check out the \"Follow-Up Care\" calendar on \"My Medical Profile\" to be aware of any " +
                "potential upcoming follow-up appointments. <br/><br/>" +
                "While there, you can mark any appointments as Completed and adjust the dates on upcoming ones so " +
                "that they match your actual calendar. <br/><br/>" +
                "<a href='http://sphere.regenstrief.org/openmrs/patientportaltoolkit/home.page#followUpCare'>View Follow-up Care Calendar</a><br/><br/>"
                +

                "Thank you,<br/>" +
                "The SPHERE Team";

        try {
            Message newMessage = new Message("Appointment Reminder", content, patient,
                    patient);
            Context.getService(MessageService.class).saveMessage(newMessage);
            successfullySent = true;

        } catch (Exception e) {
            System.out.println(
                    "Patient with UUID: " + patient.getUuid()
                            + " could not send SPHERE message in AppointmentReminderUtil");
            e.printStackTrace();
        }
        return successfullySent;
    }

    private static boolean sendEmailNotificationTo(Patient patient) {
        boolean successfullySent = false;
        String content = "Hello " + patient.getGivenName() + ",\n\n" +
                "You may be due for a recommended follow-up appointment with your doctor in a week to a month.\n\n" +
                "Or, you may have visited your doctor recently for a recommended follow-up appointment.\n\n" +
                "Please know that we don't have access to your medical records, so we don't know specific details.\n\n"
                +
                "Log in to the SPHERE website (sphere.regenstrief.org) and check your Messages for more information about these appointments.\n\n"
                +
                "Thank you,\n" +
                "The SPHERE Team";

        try {
            String destinationEmailAddress = patient.getAttribute("Email").toString();
            MailHelper.sendMail(
                    "SPHERE Appointment Reminder",
                    content,
                    destinationEmailAddress,
                    false);
            successfullySent = true;

        } catch (Exception e) {
            System.out.println(
                    "Patient with UUID: " + patient.getUuid() + " could not send email in AppointmentReminderUtil");

        }
        return successfullySent;
    }

    private static List<VisitDetail> generateVisits(Patient patient) {
        List<VisitDetail> visitDetails = new ArrayList<>();
        List<Reminder> reminders = Context.getService(ReminderService.class).getReminders(patient);
        for (Reminder reminder : reminders) {
            VisitDetail detail = new VisitDetail(
                    reminder.getFollowProcedureName(),
                    reminder.getTargetDate(),
                    reminder.getStatus());
            visitDetails.add(detail);
        }
        return visitDetails;
    }

    private static boolean reminderProcessHasBeenRunToday() {
        EventLog latestRun = Context.getService(PatientPortalMiscService.class)
                .getLatestAppointmentReminderHasRunEventLog();
        DateTime startOfToday = new LocalDate().toDateTimeAtStartOfDay();

        // If there is no latest run, the process has never been run
        // If the start of today is after the latest run, then it hasn't been run today
        if (latestRun == null || startOfToday.toDate().after(latestRun.getCreatedAt())) {
            return false;
        }
        return true;
    }

}

class VisitDetail {
    public Date targetDate;
    public String procedureName;
    public Integer status;

    public VisitDetail(String procedureName, Date targetDate, Integer status) {
        this.targetDate = targetDate;
        this.procedureName = procedureName;
        this.status = status;
    }
}

class PatientWithVisit {
    public Patient patient;
    public List<VisitDetail> visits;

    public PatientWithVisit(Patient patient, List<VisitDetail> visits) {
        this.patient = patient;
        this.visits = visits;
    }
}

class AppointmentReminder {

    public static String UPCOMING = "Upcoming";
    public static String HISTORICAL = "Historical";

    public String type;
    public Integer numberOfDays;
    public String description;
}
