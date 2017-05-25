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

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

import org.json.*;

/**
 * Created by maurya on 2/27/17.
 */
public class PreventiveCareManageModalFragmentController {
    public void controller(PageModel model, PageRequest pageRequest) {
        
    }
    
    public void markPreventiveCareCompleted(FragmentModel model, 
                                            @RequestParam(value = "eventId") String eventId, 
                                            @RequestParam(value = "personUuid", required = true) String personUuid, 
                                            @RequestParam(value = "formatedTargetDate") String formatedTargetDate, 
                                            @RequestParam(value = "conceptId") String conceptId, 
                                            @RequestParam(value = "jsonData") String jsonData, 
                                            HttpServletRequest servletRequestest) throws ParseException {
        //System.out.println(conceptId);
        //System.out.println(jsonData);
        ///////////////////////////////////
        // Define Patient
        ///////////////////////////////////
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        //////////////////////////////////////////////////////////////////////////////
        // Save this event details as an encounter
        //////////////////////////////////////////////////////////////////////////////
        Encounter savedEncounter = saveEncounterForPreventiveCareEvent(conceptId, jsonData, patient);
        
        //////////////////////////////////////////////////////////////////////////////
        // Record this submission in preventiveCareEvents database
        //////////////////////////////////////////////////////////////////////////////
        ///////////////
        // Find Completed Date from Questions
        //      WARNING : Assuming that there is only one datetime datatype question response and that that response is the completed date.
        ///////////////
        JSONArray questions = new JSONArray(jsonData);
        String markCompletedDate = null;
        for (int i = 0; i < questions.length(); i++) {
            //String uuid = questions.getJSONObject(i).getString("uuid");
            String datatype = questions.getJSONObject(i).getString("datatype");
            String response = questions.getJSONObject(i).getString("response");
            if(datatype.equals("DT")){
                markCompletedDate = response;
                break;
            }
        }
        
        /////////////
        // Format Dates Correctly
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
        // Mark this event completed
        //////////////
        // Get event by id, if id is not associated with a dbsaved event then generate a reminder based on the guideline data provided
        PreventativeCareEvent event = Context.getService(PreventativeCareService.class).getEventByIdOrGuidelineData(eventId, patient, conceptId, targetDate);
        Context.getService(PreventativeCareService.class).markCompletedEvent(event, completedDate, savedEncounter);
    }
    
    public void modifyCompleted(FragmentModel model, 
                                @RequestParam(value = "eventId") String eventId, 
                                @RequestParam(value = "personUuid", required = true) String personUuid, 
                                @RequestParam(value = "formatedTargetDate") String formatedTargetDate, 
                                @RequestParam(value = "conceptId") String conceptId, 
                                @RequestParam(value = "jsonData") String jsonData, 
                                HttpServletRequest servletRequestest) throws ParseException {
        /*
            Logic: creates a new encounter and updates the encounter id correlated to the event. Deletes previous encounter in the process.
        */
         
        System.out.println(conceptId);
        System.out.println(jsonData);
        ///////////////////////////////////
        // Define Patient
        ///////////////////////////////////
        // Get patient this action is requested for
        Person person = Context.getPersonService().getPersonByUuid(personUuid);
        //User userRequestedFor = Context.getUserService().getUsersByPerson(person,false).get(0);
        Patient patient = Context.getPatientService().getPatient(person.getId());
        
        //////////////////////////////////////////////////////////////////////////////
        // Save this event details as an encounter
        //////////////////////////////////////////////////////////////////////////////
        Encounter newEncounter = saveEncounterForPreventiveCareEvent(conceptId, jsonData, patient);
        
        
        //////////////////////////////////////////////////////////////////////////////
        // Record this submission in preventiveCareEvents database
        //////////////////////////////////////////////////////////////////////////////
        ///////////////
        // Find Completed Date from Questions
        //      WARNING : Assuming that there is only one datetime datatype question response and that that response is the completed date.
        ///////////////
        JSONArray questions = new JSONArray(jsonData);
        String markCompletedDate = null;
        for (int i = 0; i < questions.length(); i++) {
            //String uuid = questions.getJSONObject(i).getString("uuid");
            String datatype = questions.getJSONObject(i).getString("datatype");
            String response = questions.getJSONObject(i).getString("response");
            if(datatype.equals("DT")){
                markCompletedDate = response;
                break;
            }
        }
        
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date completedDate = new Date();
        try {
            completedDate = format.parse(markCompletedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //////////////
        // Replace previous encounter with new encounter
        //////////////
        PreventativeCareEvent event = Context.getService(PreventativeCareService.class).getEventById(eventId);
        //String oldEncounterUuid = event.getEncounterUuid();
        //Context.getService(EncounterService.class).purgeEncounter(Context.getService(EncounterService.class).getEncounterByUuid(oldEncounterUuid), true); 
        Context.getService(PreventativeCareService.class).updateAssociatedEncounter(event, newEncounter);
        Context.getService(PreventativeCareService.class).updateCompletedDate(event, completedDate);
    }
    
    
    
    public void modifyAppointment(FragmentModel model, 
                              @RequestParam(value = "eventId", required = true) String eventId, 
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
        PreventativeCareEvent event = Context.getService(PreventativeCareService.class).getEventByIdOrGuidelineData(eventId, patient, conceptId, oldTargetDate);
        Context.getService(PreventativeCareService.class).modifyTargetDate(event, newTargetDate);
    }
    
    public void removeAppointment(FragmentModel model, 
                              @RequestParam(value = "eventId", required = true) String eventId, 
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
        PreventativeCareEvent event = Context.getService(PreventativeCareService.class).getEventByIdOrGuidelineData(eventId, patient, conceptId, oldTargetDate);
        Context.getService(PreventativeCareService.class).removeEvent(event);

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
        // Record this new event
        //////////////
        PreventativeCareEvent event = Context.getService(PreventativeCareService.class).generateEventFromGuidelineData(patient, conceptId, targetDate);
        Context.getService(PreventativeCareService.class).addEvent(event);

    }
    
    
    //////////////////////////
    // Save an encounter from questions answered in jsonData  
    //////////////////////////
    protected Encounter saveEncounterForPreventiveCareEvent(String conceptId, String jsonData, Patient patient) throws ParseException {
        //////////////////////////////////////////////////////////////////
        // Save this Encounter
        //////////////////////////////////////////////////////////////////
        
        /////////////////////////////
        // Create Encounter Base 
        //////////////////////////////
        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter newEncounter = new Encounter();
        newEncounter.setPatient(patient);
        Date date = new Date();
        newEncounter.setDateCreated(new Date());
        newEncounter.setEncounterDatetime(date);
        
        EncounterType thisEncounter = new EncounterType();
        /////////////////////////////
        // Append Encounter Type
        //////////////////////////////
        switch (conceptId){
            case "162938" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.INFLUENZA_VACCINE);
                break;
            case "162939" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.PNEUMOCOCCAL_VACCINE);
                break;
            case "162940" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.CHOLESTEROL_SCREENING);
                break;
            case "162941" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.BP_SCREENING); 
                break;
            case "162942" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.HIV_SCREENING);
                break;
            case "162943" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.MAMMOGRAPHY_SCREENING);
                break;
            case "162944" :  
                thisEncounter = encounterService.getEncounterType(PatientPortalToolkitConstants.CERVICAL_CANCER_SCREENING);
                break;
        }
        if(thisEncounter == null) System.out.println("EncounterType does not exist.");   
        newEncounter.setEncounterType(thisEncounter);
        
        ///////////////////////////////
        // Append Observations (Questions)
        ///////////////////////////////
        JSONArray questions = new JSONArray(jsonData);
        for (int i = 0; i < questions.length(); i++) {
            Boolean valid_data = false;
            String uuid = questions.getJSONObject(i).getString("uuid");
            String datatype = questions.getJSONObject(i).getString("datatype");
            String response = questions.getJSONObject(i).getString("response");
            //System.out.println(uuid);
            
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid(uuid));
            
            if(datatype.equals("DT")){
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date parsedDate = formatter.parse(response);
                o.setValueDate(parsedDate);
                valid_data = true;
            }
            if(datatype.equals("NM")){
                o.setValueNumeric(Double.valueOf(response));
                valid_data = true;
            }
            
            //
            // Note : BIT datatype (boolean) not implemented 
            //
            
            if(valid_data) newEncounter.addObs(o);
        }
        Encounter savedEncounter = encounterService.saveEncounter(newEncounter);
        return savedEncounter;
    }
    
}
