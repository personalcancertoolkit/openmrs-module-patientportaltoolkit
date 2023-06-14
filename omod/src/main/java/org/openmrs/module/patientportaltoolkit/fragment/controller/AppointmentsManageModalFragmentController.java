/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by maurya on 2/4/16.
 */
public class AppointmentsManageModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SEND_FEEDBACK", pageRequest.getRequest()));
    }

    public void markCompleted(FragmentModel model,
            @RequestParam(value = "reminderId", required = true) String reminderId,
            @RequestParam(value = "conceptId", required = true) String conceptId,
            @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate,
            @RequestParam(value = "doctorName", required = true) String doctorName,
            @RequestParam(value = "comments", required = true) String comments,
            @RequestParam(value = "personUuid", required = true) String personUuid,
            @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate,
            HttpServletRequest servletRequest) {

        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        Patient patient = Context.getPatientService().getPatient(person.getId());

        /////////////
        // Format Date Correctly
        /////////////
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date completedDate = new Date();
        try {
            completedDate = format.parse(markCompletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date targetDate = new Date();
        try {
            targetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId,
                patient, conceptId, targetDate);
        Context.getService(ReminderService.class).markCompletedReminder(reminder, completedDate, doctorName, comments);

    }

    public void modifyCompleted(FragmentModel model,
            @RequestParam(value = "reminderId", required = true) String reminderId,
            @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate,
            @RequestParam(value = "doctorName", required = true) String doctorName,
            @RequestParam(value = "comments", required = true) String comments,
            @RequestParam(value = "personUuid", required = true) String personUuid,
            HttpServletRequest servletRequest) {

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date completedDate = new Date();
        try {
            completedDate = format.parse(markCompletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reminder reminder = Context.getService(ReminderService.class).getRemindersById(reminderId);
        Context.getService(ReminderService.class).modifyCompletedReminder(reminder, completedDate, doctorName,
                comments);

    }

    public void modifyAppointment(FragmentModel model,
            @RequestParam(value = "reminderId", required = true) String reminderId,
            @RequestParam(value = "newTargetDate", required = true) String formatedNewTargetDate,
            @RequestParam(value = "personUuid", required = true) String personUuid,
            @RequestParam(value = "conceptId", required = true) String conceptId,
            @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate,
            HttpServletRequest servletRequest) {

        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        Patient patient = Context.getPatientService().getPatient(person.getId());

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date newTargetDate = new Date();
        try {
            newTargetDate = format.parse(formatedNewTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date oldTargetDate = new Date();
        try {
            oldTargetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId,
                patient, conceptId, oldTargetDate);
        Context.getService(ReminderService.class).modifyTargetDate(reminder, newTargetDate);

        // Add to the event log
        try {
            PatientPortalUtil ppUtil = new PatientPortalUtil();
            HashMap<String, String> map = new HashMap<>();
            map.put("reminderId", reminder.getId().toString());
            map.put("patientId", reminder.getPatient().getId().toString());
            map.put("procedure", reminder.getFollowProcedureName());
            map.put("oldTargetDate", ppUtil.formatDate(oldTargetDate));
            map.put("newTargetDate", ppUtil.formatDate(newTargetDate));

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = objectWriter.writeValueAsString(map);

            Context.getService(PatientPortalMiscService.class).logEvent("EDITED_FOLLOWUP_APPOINTMENT", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAppointment(FragmentModel model,
            @RequestParam(value = "reminderId", required = true) String reminderId,
            @RequestParam(value = "personUuid", required = true) String personUuid,
            @RequestParam(value = "conceptId", required = true) String conceptId,
            @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate,
            HttpServletRequest servletRequest) {

        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        Patient patient = Context.getPatientService().getPatient(person.getId());

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date oldTargetDate = new Date();
        try {
            oldTargetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId,
                patient, conceptId, oldTargetDate);
        Context.getService(ReminderService.class).removeReminder(reminder);
    }

    public void addAppointment(FragmentModel model,
            @RequestParam(value = "personUuid", required = true) String personUuid,
            @RequestParam(value = "conceptId", required = true) String conceptId,
            @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate,
            HttpServletRequest servletRequest) {

        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        Patient patient = Context.getPatientService().getPatient(person.getId());

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date targetDate = new Date();
        try {
            targetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reminder reminder = Context.getService(ReminderService.class)
                .generateReminderFromGuidelineData(patient, conceptId, targetDate);
        Context.getService(ReminderService.class).addReminder(reminder);

    }
}
