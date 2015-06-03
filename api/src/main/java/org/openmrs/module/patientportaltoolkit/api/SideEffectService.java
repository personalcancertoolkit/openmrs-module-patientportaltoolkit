package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.SideEffect;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Maurya on 02/06/2015.
 */
public interface SideEffectService extends OpenmrsService {

    @Transactional(readOnly = true)
    public List<SideEffect> getAllSideEffects();

    @Transactional(readOnly = true)
    public List<Concept> getAllSideEffectsForPatient(Patient patient);
}
