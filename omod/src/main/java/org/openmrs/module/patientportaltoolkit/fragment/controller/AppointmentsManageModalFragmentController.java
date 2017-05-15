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
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by maurya on 2/4/16.
 */
public class AppointmentsManageModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SEND_FEEDBACK", pageRequest.getRequest()));
    }

    /*
    public void sendFeedback(FragmentModel model, 
                             @RequestParam(value = "reminderID", required = true) String reminderID, 
                             @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate, 
                             @RequestParam(value = "doctorName", required = true) String doctorName, 
                             @RequestParam(value = "comments", required = true) String comments, 
                             HttpServletRequest servletRequest) {
        System.out.println("HELLO!");
        System.out.println(reminderID);
        System.out.println(markCompletedDate);
        System.out.println(doctorName);
        System.out.println(comments);
        //MailHelper.sendMail("Testing Subject",feedback, Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL));
    }
    */
    
    
    public void markCompleted(FragmentModel model, 
                              @RequestParam(value = "reminderId", required = true) String reminderId, 
                              @RequestParam(value = "conceptId", required = true) String conceptId, 
                              @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate, 
                              @RequestParam(value = "doctorName", required = true) String doctorName, 
                              @RequestParam(value = "comments", required = true) String comments, 
                              @RequestParam(value = "personUuid", required = true) String personUuid, 
                              @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate, 
                              HttpServletRequest servletRequest) {
        
        /*
        System.out.println("Data Received by appointmentsManageModal/markCompleted.action:");
        System.out.println("reminderId : " + reminderId);
        System.out.println("markCompletedDate : " + markCompletedDate);
        System.out.println("doctorName : " + doctorName);
        System.out.println("comments : " + comments);
        System.out.println("personUuid : " + personUuid);
        System.out.println("formatedTargetDate : " + formatedTargetDate);
        */
        
        // Get person this action is requested by
        //User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////
        /*
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        */
        
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
        
        
        //////////////
        // Mark this reminder completed
        //////////////
        // Get reminder by reminderId, if reminderId is not associated with a dbsaved reminder then generate a reminder based on the guideline data provided
        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId, patient, conceptId, targetDate);
        Context.getService(ReminderService.class).markCompletedReminder(reminder, completedDate, doctorName, comments);

    }
    
    public void modifyCompleted(FragmentModel model, 
                              @RequestParam(value = "reminderId", required = true) String reminderId, 
                              @RequestParam(value = "markCompletedDate", required = true) String markCompletedDate, 
                              @RequestParam(value = "doctorName", required = true) String doctorName, 
                              @RequestParam(value = "comments", required = true) String comments, 
                              @RequestParam(value = "personUuid", required = true) String personUuid, 
                              HttpServletRequest servletRequest) {
        
        /*
        System.out.println("Data Received by appointmentsManageModal/modifyCompleted.action:");
        System.out.println("reminderId : " + reminderId);
        System.out.println("markCompletedDate : " + markCompletedDate);
        System.out.println("doctorName : " + doctorName);
        System.out.println("comments : " + comments);
        System.out.println("personUuid : " + personUuid);
        */
        
        // Get person this action is requested by
        //User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        //Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////
        /*
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        */
        
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
        
        //////////////
        // Modify this reminder
        //////////////
        Reminder reminder = Context.getService(ReminderService.class).getRemindersById(reminderId);
        Context.getService(ReminderService.class).modifyCompletedReminder(reminder, completedDate, doctorName, comments);

    }
    
    public void modifyAppointment(FragmentModel model, 
                              @RequestParam(value = "reminderId", required = true) String reminderId, 
                              @RequestParam(value = "newTargetDate", required = true) String formatedNewTargetDate, 
                              @RequestParam(value = "personUuid", required = true) String personUuid, 
                              @RequestParam(value = "conceptId", required = true) String conceptId, 
                              @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate, 
                              HttpServletRequest servletRequest) {
        
        
        /*
        System.out.println("Data Received by appointmentsManageModal/modifyAppointment.action:");
        System.out.println("reminderId : " + reminderId);
        System.out.println("newTargetDate : " + formatedNewTargetDate);
        System.out.println("personUuid : " + personUuid);
        System.out.println("conceptId : " + conceptId);
        System.out.println("formatedTargetDate : " + formatedTargetDate);
        */
        
        // Get person this action is requested by
        //User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////        
        /*
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        */
        
        /////////////
        // Format Date Correctly
        /////////////
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
        
        //////////////
        // Modify this reminder
        //////////////
        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId, patient, conceptId, oldTargetDate);
        Context.getService(ReminderService.class).modifyTargetDate(reminder, newTargetDate);

    }
    
    public void removeAppointment(FragmentModel model, 
                              @RequestParam(value = "reminderId", required = true) String reminderId, 
                              @RequestParam(value = "personUuid", required = true) String personUuid, 
                              @RequestParam(value = "conceptId", required = true) String conceptId, 
                              @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate, 
                              HttpServletRequest servletRequest) {
        
        
        /*
        System.out.println("Data Received by appointmentsManageModal/removeAppointment.action:");
        System.out.println("reminderId : " + reminderId);
        System.out.println("personUuid : " + personUuid);
        System.out.println("conceptId : " + conceptId);
        System.out.println("formatedTargetDate : " + formatedTargetDate);
        */
        
        // Get person this action is requested by
        //User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////        
        /*
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        */
        
        /////////////
        // Format Date Correctly
        /////////////
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date oldTargetDate = new Date();
        try {
            oldTargetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //////////////
        // Modify this reminder
        //////////////
        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId, patient, conceptId, oldTargetDate);
        Context.getService(ReminderService.class).removeReminder(reminder);

    }
    public void addAppointment(FragmentModel model, 
                              @RequestParam(value = "personUuid", required = true) String personUuid, 
                              @RequestParam(value = "conceptId", required = true) String conceptId, 
                              @RequestParam(value = "formatedTargetDate", required = true) String formatedTargetDate, 
                              HttpServletRequest servletRequest) {
        
        
        System.out.println("Data Received by appointmentsManageModal/removeAppointment.action:");
        System.out.println("personUuid : " + personUuid);
        System.out.println("conceptId : " + conceptId);
        System.out.println("formatedTargetDate : " + formatedTargetDate);
        
        // Get person this action is requested by
        //User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////        
        /*
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        */
        
        /////////////
        // Format Date Correctly
        /////////////
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date targetDate = new Date();
        try {
            targetDate = format.parse(formatedTargetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //////////////
        // Modify this reminder
        //////////////
        Reminder reminder = Context.getService(ReminderService.class).generateReminderFromGuidelineData(patient, conceptId, targetDate);
        Context.getService(ReminderService.class).addReminder(reminder);

    }
    
}
