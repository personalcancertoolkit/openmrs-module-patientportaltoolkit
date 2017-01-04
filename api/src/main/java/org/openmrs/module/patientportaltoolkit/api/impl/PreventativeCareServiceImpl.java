package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;

import java.util.Date;
import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public class PreventativeCareServiceImpl extends BaseOpenmrsService implements PreventativeCareService {

    @Override
    public PreventativeCareEvent getPreventativeCareEventById(String Id) {
        return null;
    }

    @Override
    public List<PreventativeCareEvent> getAllPreventativeCareEventByPatient(Patient patient) {
        return null;
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
}
