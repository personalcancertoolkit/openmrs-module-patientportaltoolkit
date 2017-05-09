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
        System.out.println("Event was returned");
        Context.getService(PreventativeCareService.class).markCompletedEvent(event, completedDate, savedEncounter);
        System.out.println("All was completed");
    }

    
    
    
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
