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

package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.GuidelineInterval;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.GuidelineService;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.db.ReminderDAO;
import org.openmrs.module.patientportaltoolkit.api.db.hibernate.HibernateReminderDAO;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

import java.text.ParseException;
import java.util.*;

/**
 * Created by Maurya on 08/06/2015.
 */
public class ReminderServiceImpl extends BaseOpenmrsService implements ReminderService {

    protected ReminderDAO dao;
    private final static Integer CANCER_ABNORMALITY_TOLD = 162826;
    private final static Integer CANCER_ABNORMALITY_TOLD_YES = 1065;
    private final static Integer CANCER_ABNORMALITY = 162870;
    private final static Integer CANCER_ABNORMALITY_FAP = 162828;
    private final static Integer CANCER_ABNORMALITY_HNPCC = 162827;
    private final static Integer CANCER_ABNORMALITY_INFLBD = 162829;

    private final static Integer CANCER_TYPE = 162869;
    private final static Integer CANCER_STAGE = 162875;
    private final static Integer SURGERY_TYPE = 162876;
    private final static Integer SURGERY_DATE = 162842;
    private final static Integer DIAGNOSIS_DATE = 162825;
    private final static Integer RADIATION_TYPE = 162878;
    private final static Integer RADIATION_START_DATE = 6132;
    private final static Integer CHEMOTHERAPY_MEDS = 6156;
    private final static int ALERT_DAYS = 60;
    private final static int MATCH_DAYS = 7;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public ReminderDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(ReminderDAO dao) {
        this.dao = dao;
    }
    @Override
    public Reminder saveReminder(Reminder Reminder){
        return dao.saveReminder(Reminder);
    }

    @Override
    public List<Reminder> getReminders(Patient pat) {
        return generateRemindersbyGuidelineConditions(pat);
    }
    

    @Override
    public Reminder markCompletedReminder(Reminder reminder) {
        Date today = new Date();
        reminder.setCompleteDate(today);
        reminder.setStatus(1);
        return dao.saveReminder(reminder);
    }

    
    @Override
    public Reminder markCompletedReminder(Reminder reminder, Date markCompleteDate, String doctorsName, String comments) {
        // Takes a reminder object, marks it completed, and saves it.
        Date today = new Date();
        reminder.setCompleteDate(markCompleteDate);
        reminder.setDoctorName(doctorsName);
        reminder.setResponseComments(comments);
        reminder.setResponseDate(today);
        reminder.setStatus(1);
        reminder.setResponseUser(Context.getAuthenticatedUser());
        return dao.saveReminder(reminder);
    }

/*    @Override
    public Reminder markScheduledReminder(String reminderId, Date date) {
        Date today = new Date();
        Reminder reminder=getRemindersById(reminderId);
        reminder.setResponseDate(date);
        reminder.setStatus(2);
        reminder.setResponseUser(Context.getAuthenticatedUser());
        return dao.saveReminder(reminder);
    }*/

    @Override
    public Reminder getRemindersById(String Id) {
        // If id is not a valid integer, return null - like would be returned if no reminders with a valid integer id were found.
        try {
            return dao.getReminder(Integer.parseInt(Id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    
    @Override
    public Reminder getReminderByIdOrGuidelineData(String reminderId, Patient patient, String conceptId, Date targetDate) {
        Reminder reminder = null;
        // attempt to get reminder by id
        reminder = getRemindersById(reminderId);
        if(reminder == null){
            //System.out.println("Reminder with that id is not found in database. Time to generate it by guideline data!"); 
            //reminder = getReminderByConceptId(conceptId);   
            Concept followUpConcept = Context.getConceptService().getConcept(conceptId);
            reminder = generateReminderFromGuidelineData(patient, followUpConcept, targetDate); 
        }
        return reminder;
    }
    
    
    @Override
    public List<Reminder> getAllRemindersByPatient(Patient patient) {
        return dao.getAllRemindersByPatient(patient);
    }


    /*
    private List<Reminder>  findReminders(Patient pat) {
    */


    /*
    private List<Reminder>  findAllReminders(Patient pat) {
    */

    private List<Reminder> getRemindersByProvider(Patient pat) {
        return dao.getRemindersByProvider(pat);
    }

    /*
    private Date findMidDate(Date refDate1, Date refDate2) {
    */

    @Override
    public List<Reminder> getRemindersCompleted(Patient pat) {
        List<Reminder> reminderList = dao.getRemindersCompleted(pat);
        return reminderList;
    }

    /*
    private Date[] findTargetDates(Date surgDate, Concept radiationType, String followYears) {
    */  

    /*
    private Date findDate(Date startDate, String yearsAfter) {
    }
    */  

    /*
    private Date findScheduleDate(Patient patient, Concept careType, Date targetDate) {
    */  

    /**
     *
     *
     * @param
     * @return
     */
    private Obs findLatest(List<Obs> observations) {
        Obs latest = null;

        if(observations != null) {
            for (Obs obs : observations) {
                if(obs != null && !obs.isVoided()) {
                    if(latest == null || latest.getDateCreated().before(obs.getDateCreated())) {
                        latest = obs;
                    }

                }
            }
        }

        return latest;
    }

    @Override
    public  GuidelineConditionSet generateGuidelineConditionSet(Patient patient) {
        //find cancer type
        Concept cancerTypeConcept = Context.getConceptService().getConcept(CANCER_TYPE);
        Obs cancerType = findLatest(Context.getObsService().getObservationsByPersonAndConcept(patient, cancerTypeConcept));
        Concept type = cancerType==null? null : cancerType.getValueCoded();
        //find cancer stage
        Concept cancerStageConcept = Context.getConceptService().getConcept(CANCER_STAGE);
        Obs cancerStage = findLatest(Context.getObsService().getObservationsByPersonAndConcept(patient, cancerStageConcept));
        Concept stage = cancerStage==null? null : cancerStage.getValueCoded();

        Set<Concept> conditionConcepts = new HashSet<>();
        conditionConcepts.add(type);
        conditionConcepts.add(stage);
        GuidelineConditionSet guidelineConditionSet = Context.getService(GuidelineService.class).getGuidlineConditionSetbyConditions(conditionConcepts);

        return guidelineConditionSet;
    }
    public  List<Reminder> generateRemindersbyGuidelineConditions(Patient patient) {
        ///////////////////////////////////////////////////////////////////////////////////////////
        // Ensure that this user satisfies conditions for having reminders
        //      return null if they do not
        ///////////////////////////////////////////////////////////////////////////////////////////
        //find genetic_abnormality flag and answer
        //block any follow-up tests from appearing:
        //	1)       IF the patient answers YES to genetic abnormality, and
        //	2)       THEN answers FAP/HNPCC/or INFLAMMATORY BOWEL DISORDER
        //find genetic abnormality flag
        Concept cancerAbnormalityToldConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY_TOLD);
        Concept cancerAbnormalityToldYesConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY_TOLD_YES);
        Concept cancerAbnormalityConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY);
        Concept cancerAbnormalityFapConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY_FAP);
        Concept cancerAbnormalityHnpccConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY_HNPCC);
        Concept cancerAbnormalityInflbdConcept = Context.getConceptService().getConcept(CANCER_ABNORMALITY_INFLBD);

        Obs cancerAbnormalityToldObs = findLatest(Context.getObsService().getObservationsByPersonAndConcept(patient, cancerAbnormalityToldConcept));
        Concept cancerAbnormalityToldAns = (cancerAbnormalityToldObs==null? null : cancerAbnormalityToldObs.getValueCoded());
        if(cancerAbnormalityToldAns != null && cancerAbnormalityToldAns.equals(cancerAbnormalityToldYesConcept)) {
            Obs cancerAbnormalityObs = findLatest(Context.getObsService().getObservationsByPersonAndConcept(patient, cancerAbnormalityConcept));
            Concept cancerAbnormalityAns = (cancerAbnormalityObs==null? null : cancerAbnormalityObs.getValueCoded());
            if(cancerAbnormalityAns != null &&
                    (cancerAbnormalityAns.equals(cancerAbnormalityFapConcept) ||
                            cancerAbnormalityAns.equals(cancerAbnormalityHnpccConcept) ||
                            cancerAbnormalityAns.equals(cancerAbnormalityInflbdConcept))) {
                return null;
            }
        }
        //find surgery date
        Concept surgeryDateConcept = Context.getConceptService().getConcept(SURGERY_DATE);
        Obs surgeryDate = findLatest(Context.getObsService().getObservationsByPersonAndConcept(patient, surgeryDateConcept));
        Date surgDate = surgeryDate==null? null : surgeryDate.getValueDatetime();
        if(surgDate == null) {
            log.warn("No surgery is found for this patient: " + patient);
            return null;
        }


        
        ///////////////////////////////////////////////////////////////////////////////////////////
        // Build reminders list
        ///////////////////////////////////////////////////////////////////////////////////////////
        List<Reminder> reminders = new ArrayList<>();
        
        // Get all reminders from database
        List<Reminder> databaseReminders = new ArrayList<>();
        databaseReminders = getAllRemindersByPatient(patient);
        reminders.addAll(databaseReminders);
        
        // Get all guideline reminders
        GuidelineConditionSet guidelineConditionSet = generateGuidelineConditionSet(patient);
        Date firstSurgeryDate= ToolkitResourceUtil.getFirstSurgeryDate(patient);
        LocalDate modifiableDate=null;
        Date targetDate=null;
        // For each guideline
        for (Guideline g:  guidelineConditionSet.getGuidelines()) {
            // and for each guideline's set of intervals (e.g., check up in 6mo, 12mo, and 24mo)
            for (GuidelineInterval gi: g.getGuidelineIntervalSet()) {
                modifiableDate = new LocalDate(firstSurgeryDate);
                targetDate = modifiableDate.plusMonths(gi.getIntervalLength()).toDate();
                
                // If this reminder, with same target date, was already found in database recorded reminders : don't duplicate it. Just continue. 
                if(findReminderByFollowupCareAndDate(databaseReminders,g.getFollowupProcedure(),targetDate) != null)
                    continue;
                
                // Create, on the fly, a new reminder with the followup procedure and target date required by guidelines.
                Reminder reminder = generateReminderFromGuidelineData(patient, g.getFollowupProcedure(), targetDate);
                reminders.add(reminder);
            }
        }

        return reminders;
    } // end generateRemindersbyGuidelineConditions
    
    
    @Override
    public Reminder generateReminderFromGuidelineData(Patient patient, Concept followupConcept, Date targetDate){
        Reminder reminder = new Reminder();
        reminder.setPatient(patient);
        reminder.setFollowProcedure(followupConcept);
        reminder.setTargetDate(targetDate);
        reminder.setStatus(0);
        return reminder;
    }
    
    
    Reminder findReminderByFollowupCareAndDate (List<Reminder> findInReminderList,Concept reminderProcedure,Date reminderDate){
        Reminder exactReminder = null;
        for(Reminder r:findInReminderList) {
            if(r.getTargetDate() == null)
                continue;
            if(r.getFollowProcedure().equals(reminderProcedure) && r.getTargetDate().equals(reminderDate)){
                exactReminder=r;
            }
        }
        return exactReminder;
    }

}