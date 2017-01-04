package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;

/**
 * Created by maurya on 1/4/17.
 */
public interface PatientPortalPersonAttributesDAO {

    PatientPortalPersonAttributes getPatientPortalPersonAttributesByPatient(Patient patient);
}
