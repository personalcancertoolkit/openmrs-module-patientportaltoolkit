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
    public Object getPatientPortalRelation(Patient requestedPatient, Person requestedPerson, User user);

    @Transactional(readOnly = true)
    public Object getAllPatientPortalRelations();

    @Transactional(readOnly = true)
    public Object getPatientPortalRelationByPatient(Patient patient);

    @Transactional(readOnly = true)
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person);

    @Transactional(readOnly = false)
    public PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = true)
    public PatientPortalRelation getPatientPortalRelation(String uuid);

    @Transactional(readOnly = false)
    public void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    @Transactional(readOnly = false)
    public void deletePatientPortalRelation(String uuid);

    @Transactional(readOnly = false)
    public void updatePatientPortalRelation(User user, Person person, String uuid);
}
