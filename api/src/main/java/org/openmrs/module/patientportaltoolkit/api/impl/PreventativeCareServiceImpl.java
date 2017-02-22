package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.db.PreventativeCareDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public PreventativeCareEvent getPreventativeCareEventById(String Id) {
        return null;
    }

    @Override
    public List<PreventativeCareEvent> getAllPreventativeCareEventByPatient(Patient patient) {
       Date dateOfJoin = patient.getDateCreated();
        LocalDate modifiableDate=null;
        List<PreventativeCareEvent> preventiveEvents = new ArrayList<>();
        List<PreventativeCareEvent> databasePreventiveEvents = new ArrayList<>();
        databasePreventiveEvents = dao.getAllPreventativeCareEventsByPatient(patient);
        preventiveEvents.addAll(databasePreventiveEvents);

        for (PreventiveCareGuideline pcg:  getPreventativeCareGuideline(patient)) {
            for (PreventiveCareGuidelineInterval pcgi: pcg.getPcgguidelineIntervalSet()){
                modifiableDate = new LocalDate(dateOfJoin);
                PreventativeCareEvent pcge = new PreventativeCareEvent();
                pcge.setTargetDate(modifiableDate.plusMonths(pcgi.getIntervalLength()).toDate());
                pcge.setFollowProcedure(pcg.getFollowupProcedure());
                pcge.setStatus(0);
                preventiveEvents.add(pcge);
            }
        }
       return preventiveEvents;
    }

    @Override
    public List<PreventativeCareEvent> getPreventativeCareEventsCompleted(Patient patient) {
        return null;
    }

    @Override
    public PreventativeCareEvent markCompletedPreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        return null;
    }

    @Override
    public PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        return null;
    }

    @Override
    public PreventativeCareEvent markCompletedPreventativeCareEvent(String preventativeCareEventId, Date markCompleteDate, String doctorsName, String comments) {
        return null;
    }

    @Override
    public PreventativeCareEvent markScheduledPreventativeCareEvent(String PreventativeCareEventId, Date date) {
        return null;
    }

    @Override
    public List<PreventiveCareGuideline> getPreventativeCareGuideline(Patient patient) {
        List<PreventiveCareGuideline> totalPreventiveCareGuidelines = dao.getAllPreventativeCareGuidelines();
        //Screening Mammography
        if (patient.getGender().equals("M") || (patient.getGender().equals("F") && patient.getAge()<40)) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(5));
        }
        //Cervical Cancer Screening
        if (patient.getGender().equals("M") || (patient.getGender().equals("F") && (patient.getAge()<21 || patient.getAge()>65))) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(6));
        }
        //Cholesterol Screening
        if ((patient.getGender().equals("F") && patient.getAge()<45) || (patient.getGender().equals("M") && patient.getAge()<35)) {
            totalPreventiveCareGuidelines.remove(dao.getPreventativeCareGuidelinebyID(7));
        }

        return totalPreventiveCareGuidelines;
    }
}
