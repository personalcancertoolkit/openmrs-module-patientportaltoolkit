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
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.User;
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
        
  
        
        System.out.println("Data Received by appointmentsManageModal/markCompleted.action:");
        System.out.println("reminderId : " + reminderId);
        System.out.println("markCompletedDate : " + markCompletedDate);
        System.out.println("doctorName : " + doctorName);
        System.out.println("comments : " + comments);
        System.out.println("personUuid : " + personUuid);
        System.out.println("formatedTargetDate : " + formatedTargetDate);
        
        
        // Get person this action is requested by
        User userRequestedBy = Context.getAuthenticatedUser();
        
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        ///////////////
        // Ensure requester (userRequestedBy) can modify information for requestee (userRequestedFor)
        ///////////////
        System.out.println("RequestedUserId:" + userRequestedFor.getSystemId());
        System.out.println("RequestedUserName:"+ userRequestedFor.getUsername());
        System.out.println("RequestedByID:"+ userRequestedBy.getUsername());
        System.out.println("RequestedByUserName:"+ userRequestedBy.getUsername());
        
        
        
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
        System.out.println("reg:");
        System.out.println(Context.getService(ReminderService.class).getRemindersById(reminderId));
        
        
        System.out.println("robust:");
        // Get reminder by reminderId, if reminderId is not associated with a dbsaved reminder then generate a reminder based on the guideline data provided
        Reminder reminder = Context.getService(ReminderService.class).getReminderByIdOrGuidelineData(reminderId, patient, conceptId, targetDate);
        System.out.println(reminder);
        
        Context.getService(ReminderService.class).markCompletedReminder(reminder, completedDate, doctorName, comments);
        System.out.println("HELLO!");

    }
    
    
}
