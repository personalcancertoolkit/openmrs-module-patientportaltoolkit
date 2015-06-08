package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Maurya.
 */
@Transactional
public interface PatientPortalRelationService extends OpenmrsService {

    @Transactional(readOnly = true)
    Object getPatientPortalRelation(Patient requestedPatient, Person requestedPerson, User user);

    @Transactional(readOnly = true)
    Object getAllPatientPortalRelations();

    @Transactional(readOnly = true)
    Object getPatientPortalRelationByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person);

    @Transactional(readOnly = false)
    PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = true)
    PatientPortalRelation getPatientPortalRelation(String uuid);

    @Transactional(readOnly = false)
    void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = false)
    void deletePatientPortalRelation(String uuid);

    @Transactional(readOnly = false)
    void updatePatientPortalRelation(User user, Person person, String uuid);
}
