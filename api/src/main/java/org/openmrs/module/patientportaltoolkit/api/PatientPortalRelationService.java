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
    PatientPortalRelation getPatientPortalRelation(Person person, Person requestedPerson, User user);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getAllPatientPortalRelations();

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient, Boolean includeRetired);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByRelatedPerson(Person person);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getAcceptedPatientPortalRelationByRelatedPerson(Person person);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person);

    @Transactional(readOnly = true)
    List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person,Boolean includeRetired);

    @Transactional(readOnly = false)
    PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = true)
    PatientPortalRelation getPatientPortalRelation(String uuid);

    @Transactional(readOnly = false)
    void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = false)
    void deletePatientPortalRelation(String uuid, User user);

    @Transactional(readOnly = false)
    void updatePatientPortalRelation(User user, Person person, String uuid);
}
