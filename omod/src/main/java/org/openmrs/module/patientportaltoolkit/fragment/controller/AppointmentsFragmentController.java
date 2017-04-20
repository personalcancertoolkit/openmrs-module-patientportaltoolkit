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

package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.openmrs.*;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.GuidelineService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalPersonAttributesService;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.openmrs.notification.MessageException;
import org.openmrs.notification.MessageService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    public void controller(PageModel model, PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        List<Reminder> alertableReminders = new ArrayList<>();
        Person person = (Person) model.get("person");
        org.openmrs.api.PersonService personService=Context.getPersonService();
        if(person.isPatient()) {
            Patient patient = Context.getPatientService().getPatientByUuid(person.getUuid());
            List<Reminder> reminders =  new ArrayList<>();
            reminders= Context.getService(ReminderService.class).generateRemindersbyGuidelineConditions(patient);
            model.addAttribute("reminders", reminders);
            if (reminders!=null) {
                for (Reminder r : reminders) {
                    if (r.getCompleteDate() == null) {
                        alertableReminders.add(r);
                    }
                }
            }
        }
        log.info(PPTLogAppender.appendLog("REQUEST_APPOINTMENTS_FRAGMENT", pageRequest.getRequest()));
        //log.info( "~REQUEST_APPOINTMENTS~"+ user.getUsername() + "~REQ_FOR:" + Context.getUserService().getUsersByPerson(person,false).get(0).getUsername() );
        model.addAttribute("alertablereminders",alertableReminders);
    }


    public void markCompleted(PageModel model, @RequestParam(value = "reminderId", required = true) String reminderId, @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate, @RequestParam(value = "doctorName", required = true) String doctorName, @RequestParam(value = "comments", required = true) String comments,@RequestParam(value = "personUuid", required = true) String personUuid, @RequestParam(value = "followupId", required = true) String followupId, HttpServletRequest servletRequest) {

        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //Person person = Context.getPersonService().getPerson( Context.getService(ReminderService.class).getRemindersById(reminderId).getPatient().getPersonId());
        log.info(PPTLogAppender.appendLog("MARK_COMPLETED_APPOINTMENTS", servletRequest));
       // log.info("~MARK_COMPLETED_APPOINTMENTS~"+ Context.getAuthenticatedUser().getUsername()+ "~REQ_FOR:"+ Context.getUserService().getUsersByPerson(person,false).get(0).getUsername());
        //System.out.println("121212121212"+markCompletedDate);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(markCompletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Temporary hack - need to fix why we are getting "null"
        if (reminderId.equals("null"))
            reminderId=null;
        int followupIdint = Integer.parseInt(followupId.trim());
        Concept followUpConcept = Context.getConceptService().getConcept(followupIdint);
        Context.getService(ReminderService.class).markCompletedReminder(reminderId, date, doctorName, comments, Context.getPatientService().getPatient(person.getId()),followUpConcept);

    }

 /*   public void markScheduled(FragmentModel model, @RequestParam(value = "reminderId", required = true) String reminderId, @RequestParam(value = "markScheduledDate", required = true) String markScheduledDate, HttpServletRequest servletRequest) {

        Person person = Context.getPersonService().getPerson( Context.getService(ReminderService.class).getRemindersById(reminderId).getPatient().getPersonId());
        log.info(PPTLogAppender.appendLog("MARK_SCHEDULED_APPOINTMENTS", servletRequest));
        //log.info("~MARK_SCHEDULED_APPOINTMENTS~" + Context.getAuthenticatedUser().getUsername()+ "~REQ_FOR:"+ Context.getUserService().getUsersByPerson(person,false).get(0).getUsername());
        //System.out.println("121212121212"+markCompletedDate);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(markScheduledDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Context.getService(ReminderService.class).markScheduledReminder(reminderId,date);

    }*/

}
