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

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;

import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
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
    PreventativeCareEvent updateAssociatedEncounter(PreventativeCareEvent event, Encounter newEncounter);
    
    @Transactional
    PreventativeCareEvent updateCompletedDate(PreventativeCareEvent event, Date completedDate);
    
    @Transactional
    PreventativeCareEvent modifyTargetDate(PreventativeCareEvent event, Date newTargetDate);
    
    @Transactional
    PreventativeCareEvent removeEvent(PreventativeCareEvent event);
    
    @Transactional
    PreventativeCareEvent addEvent(PreventativeCareEvent preventativeCareEvent);
    
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


    @Transactional
    void savePreventiveCareGuideLine(PreventiveCareGuideline pcg);

    @Transactional
    List<PreventiveCareGuideline> getPreventiveCareGuideLine();

    @Transactional
    PreventiveCareGuideline getPreventiveCareGuideLine(int id);


    @Transactional
    void deletePreventiveCareGuideLine(PreventiveCareGuideline pcg);

    @Transactional
    void savePreventiveCareGuidelineInterval(PreventiveCareGuidelineInterval pcg_interval);

    @Transactional
    List<PreventiveCareGuidelineInterval> getPreventiveCareGuidelineInterval();

    List<PreventiveCareGuidelineInterval> getPreventiveCareGuidelineInterval(PreventiveCareGuideline pcg);

    @Transactional
    void deletePreventiveCareGuidelineInterval(PreventiveCareGuidelineInterval pcg_interval);
}
