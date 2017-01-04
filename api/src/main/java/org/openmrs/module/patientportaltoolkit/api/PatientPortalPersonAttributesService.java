package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by maurya on 1/4/17.
 */
@Transactional
public interface PatientPortalPersonAttributesService {
    @Transactional(readOnly = true)
    PatientPortalPersonAttributes getPatientPortalPersonAttributesByPatient(Patient patient);

}
