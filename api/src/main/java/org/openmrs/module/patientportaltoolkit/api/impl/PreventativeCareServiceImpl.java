/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.db.PreventativeCareDAO;
import org.openmrs.Encounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

/**
 * Created by maurya on 11/30/16.
 */
public class PreventativeCareServiceImpl extends BaseOpenmrsService implements PreventativeCareService {
    protected PreventativeCareDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public PreventativeCareDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PreventativeCareDAO dao) {
        this.dao = dao;
    }

    @Override
    public PreventativeCareEvent getEventById(String Id) {
        // If id is not a valid integer, return null - like would be returned if no
        // reminders with a valid integer id were found.
        try {
            return dao.getPreventativeCareEvent(Integer.parseInt(Id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public PreventativeCareEvent getEventByIdOrGuidelineData(String databaseId, Patient patient, String conceptId,
            Date targetDate) {
        PreventativeCareEvent event = null;
        // attempt to get reminder by id
        event = getEventById(databaseId);
        if (event == null) {
            // System.out.println("Event with that id is not found in database. Time to
            // generate it by guideline data!"
            Concept followUpConcept = Context.getConceptService().getConcept(conceptId);
            event = generateEventFromGuidelineData(patient, followUpConcept, targetDate);
        }
        return event;
    }

    @Override
    public List<PreventativeCareEvent> getAllPreventativeCareEventByPatient(Patient patient) {

        List<PreventativeCareEvent> preventiveEvents = new ArrayList<>();
        List<PreventativeCareEvent> databasePreventiveEvents = new ArrayList<>();

        // Get all events from database
        databasePreventiveEvents = dao.getAllPreventativeCareEventsByPatient(patient);
        preventiveEvents.addAll(databasePreventiveEvents);

        // Get all events from guidelines
        Date dateOfJoin = patient.getDateCreated();
        LocalDate modifiableDate = null;
        Date targetDate = null;
        for (PreventiveCareGuideline g : getPreventativeCareGuideline(patient)) { // For each guideline

            PreventativeCareEvent mostRecentCompleted = dao
                    .getMostRecentPreventativeCareEventInStatusPriorToDateForProcedureAndPatient(
                            patient,
                            LocalDate.now().toDate(),
                            g.getFollowupProcedure(),
                            PreventativeCareEvent.COMPLETED_STATUS);

            PreventativeCareEvent farthestUpcomingWithinAYearThatWasEitherModifiedOrCreated = dao
                    .getPreventativeCareEventInStatusFarthestAwayFromDateForProcedureAndPatient(
                            patient,
                            LocalDate.now().plusDays(1).toDate(),
                            12,
                            g.getFollowupProcedure(),
                            PreventativeCareEvent.CREATED_STATUS);

            Date anchorDate = dateOfJoin;
            if (farthestUpcomingWithinAYearThatWasEitherModifiedOrCreated != null) {
                anchorDate = farthestUpcomingWithinAYearThatWasEitherModifiedOrCreated.getTargetDate();
            } else if (mostRecentCompleted != null) {
                anchorDate = mostRecentCompleted.getCompleteDate();
            }

            for (PreventiveCareGuidelineInterval gi : g.getPcgguidelineIntervalSet()) { // and for each guideline's set
                                                                                        // of intervals (e.g., check
                // up in 6mo, 12mo, and 24mo)
                modifiableDate = new LocalDate(anchorDate);
                targetDate = modifiableDate.plusMonths(gi.getIntervalLength()).toDate();

                // enforces, for example, that influenza target dates fall between oct and march
                targetDate = enforceConceptSpecificTargetDateRequirements(targetDate, g.getFollowupProcedure());

                // If this reminder, with same target date, was already found in database
                // recorded reminders : don't duplicate it. Just continue.
                if (findEventByConceptAndDate(databasePreventiveEvents, g.getFollowupProcedure(), targetDate) != null)
                    continue;

                // Create, on the fly, a new event with the followup procedure and target date
                // required by guidelines.
                PreventativeCareEvent event = generateEventFromGuidelineData(patient, g.getFollowupProcedure(),
                        targetDate);
                preventiveEvents.add(event);
            }
        }

        // Now that guidelines are generated, remove all events from list which have a
        // status of -1
        List<PreventativeCareEvent> valid_events = new ArrayList<>();
        for (PreventativeCareEvent event : preventiveEvents) {
            if (event.getStatus() != PreventativeCareEvent.REMOVED_STATUS)
                valid_events.add(event);
        }

        return valid_events;
    }

    /*
     * Used to enforce date requirements on preventive care events
     */
    Date enforceConceptSpecificTargetDateRequirements(Date targetDate, Concept targetConcept) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        int month = cal.get(Calendar.MONTH);

        // Enforce that, if event is influenza vacination, the date falls between oct
        // and march
        if (targetConcept.getConceptId() == 162938) {
            if (!(month >= (10 - 1) || month <= (3 - 1))) { // Ensure that date is between oct and march. Note, minus 1
                                                            // due to jan = 0
                // System.out.println("chaning date!");
                cal.set(Calendar.MONTH, Calendar.OCTOBER);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                // System.out.println("Changing date to...");
                targetDate = cal.getTime();
                // System.out.println(targetDate);
            }
        }

        return targetDate;
    }

    /*
     * Used to determine if an event with the same targetDate and targetConcept
     * already exists from a list of events
     */
    PreventativeCareEvent findEventByConceptAndDate(List<PreventativeCareEvent> searchList, Concept targetConcept,
            Date targetDate) {
        PreventativeCareEvent event = null;
        for (PreventativeCareEvent e : searchList) {
            if (e.getTargetDate() == null)
                continue;
            if (e.getFollowProcedure().equals(targetConcept)) {
                if (e.getStatus() == PreventativeCareEvent.COMPLETED_STATUS && e.getCompleteDate().equals(targetDate)) {
                    event = e;
                } else if (e.getStatus() == PreventativeCareEvent.CREATED_STATUS
                        && e.getTargetDate().equals(targetDate)) {
                    event = e;
                }
            }
        }
        return event;
    }

    @Override
    public PreventativeCareEvent generateEventFromGuidelineData(Patient patient, String conceptId, Date targetDate) {
        Concept followUpConcept = Context.getConceptService().getConcept(conceptId);
        return generateEventFromGuidelineData(patient, followUpConcept, targetDate);
    }

    @Override
    public PreventativeCareEvent generateEventFromGuidelineData(Patient patient, Concept followupConcept,
            Date targetDate) {
        PreventativeCareEvent event = new PreventativeCareEvent();
        event.setPatient(patient);
        event.setFollowProcedure(followupConcept);
        event.setTargetDate(targetDate);
        event.setOrigTargetDate(targetDate);
        event.setStatus(PreventativeCareEvent.CREATED_STATUS);
        return event;
    }

    @Override
    public List<PreventativeCareEvent> getPreventativeCareEventsCompleted(Patient patient) {
        return null;
    }

    @Override
    public PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        return null;
    }

    @Override
    public PreventativeCareEvent markCompletedEvent(PreventativeCareEvent event, Date markCompleteDate,
            Encounter relevantEncounter) {
        // Takes an event object, marks it completed, and saves it.
        // System.out.println("Marking event completed ");
        Date today = new Date();
        event.setCompleteDate(markCompleteDate);
        event.setResponseDate(today);
        event.setStatus(PreventativeCareEvent.COMPLETED_STATUS);
        event.setResponseUser(Context.getAuthenticatedUser());
        event.setEncounterUuid(relevantEncounter.getUuid());
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent updateAssociatedEncounter(PreventativeCareEvent event, Encounter newEncounter) {
        // associate new encounter
        event.setEncounterUuid(newEncounter.getUuid());
        // event.setModifiedDate(today);
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent updateCompletedDate(PreventativeCareEvent event, Date completedDate) {
        // Takes a reminder object, marks it completed, and saves it.
        event.setCompleteDate(completedDate);
        // event.setModifiedDate(today);
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent modifyTargetDate(PreventativeCareEvent event, Date newTargetDate) {
        // Takes a reminder object, marks it completed, and saves it.
        event.setTargetDate(newTargetDate);
        // event.setModifiedDate(today);
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent removeEvent(PreventativeCareEvent event) {
        event.setStatus(PreventativeCareEvent.REMOVED_STATUS);
        // event.setModifiedDate(today);
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent addEvent(PreventativeCareEvent event) {
        // Takes an event object and saves it into database
        event.setStatus(PreventativeCareEvent.CREATED_STATUS);
        // event.setModifiedDate(today);
        return dao.savePreventativeCareEvent(event);
    }

    @Override
    public PreventativeCareEvent markScheduledPreventativeCareEvent(String PreventativeCareEventId, Date date) {
        return null;
    }

    @Override
    public List<PreventiveCareGuideline> getPreventativeCareGuideline(Patient patient) {
        List<PreventiveCareGuideline> totalPreventiveCareGuidelines = dao.getAllPreventativeCareGuidelines();
        // Screening Mammography
        if (patient.getGender().equals("M") || (patient.getGender().equals("F") && patient.getAge() < 40)) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(5));
        }
        // Cervical Cancer Screening
        if (patient.getGender().equals("M")
                || (patient.getGender().equals("F") && (patient.getAge() < 21 || patient.getAge() > 65))) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(6));
        }
        // Cholesterol Screening
        if ((patient.getGender().equals("F") && patient.getAge() < 45)
                || (patient.getGender().equals("M") && patient.getAge() < 35)) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(7));
        }

        return totalPreventiveCareGuidelines;
    }
}
