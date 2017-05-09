package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.Concept;
import org.openmrs.Encounter;

import java.util.Date;
import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public interface PreventativeCareService {
    @Transactional(readOnly = true)
    PreventativeCareEvent getEventById(String Id);

    @Transactional(readOnly = true)
    List<PreventativeCareEvent> getAllPreventativeCareEventByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<PreventativeCareEvent> getPreventativeCareEventsCompleted(Patient patient);

    @Transactional
    PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    @Transactional
    PreventativeCareEvent markCompletedEvent(PreventativeCareEvent preventativeCareEvent, Date markCompleteDate, Encounter relevantEncounter);

    @Transactional
    PreventativeCareEvent markScheduledPreventativeCareEvent(String PreventativeCareEventId, Date date);

    @Transactional
    List<PreventiveCareGuideline> getPreventativeCareGuideline(Patient patient);
    
    
    
    @Transactional
    PreventativeCareEvent getEventByIdOrGuidelineData(String databaseId, Patient patient, String conceptId, Date targetDate) ;
        
    @Transactional
    PreventativeCareEvent generateEventFromGuidelineData(Patient patient, String conceptId, Date targetDate);
        
    @Transactional
    PreventativeCareEvent generateEventFromGuidelineData(Patient patient, Concept followupConcept, Date targetDate);
    
    
}
