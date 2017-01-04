package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public interface PreventativeCareService {
    @Transactional(readOnly = true)
    PreventativeCareEvent getPreventativeCareEventById(String Id);

    @Transactional(readOnly = true)
    List<PreventativeCareEvent> getAllPreventativeCareEventByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<PreventativeCareEvent> getPreventativeCareEventsCompleted(Patient patient);


    @Transactional
    PreventativeCareEvent markCompletedPreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    @Transactional
    PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    @Transactional
    PreventativeCareEvent markCompletedPreventativeCareEvent(String preventativeCareEventId, Date markCompleteDate, String doctorsName, String comments);

    @Transactional
    PreventativeCareEvent markScheduledPreventativeCareEvent(String PreventativeCareEventId, Date date);
}
