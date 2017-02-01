package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;

import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public interface PreventativeCareDAO {

    void deletePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    List<PreventativeCareEvent> getAllPreventativeCareEventsByPatient(Patient patient);

    PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    PreventativeCareEvent getPreventativeCareEvent(Integer id);

    List<PreventiveCareGuideline> getAllPreventativeCareGuidelines ();

    PreventiveCareGuideline getPreventativeCareGuidelinebyID (Integer id);
}
