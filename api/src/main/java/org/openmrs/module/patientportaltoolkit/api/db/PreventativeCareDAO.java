package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;

import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public interface PreventativeCareDAO {

    void deletePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    List<PreventativeCareEvent> getAllPreventativeCareEventsByPatient(Patient patient);

    PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    public PreventativeCareEvent getPreventativeCareEvent(Integer id);
}
