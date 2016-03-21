package org.openmrs.module.patientportaltoolkit.api.db;

import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;

import java.util.List;

/**
 * Created by Maurya.
 */
public interface PatientPortalRelationDAO {

    /**
     * Set Hibernate session factory
     *
     * @param sessionFactory Hibernate session factory object
     */
    void setSessionFactory(SessionFactory sessionFactory);

    /**
     * Get relation of a uuid
     *
     * @param uuid of the relation
     * @return Relation
     */
    PatientPortalRelation getPatientPortalRelation(String uuid);


    /**
     */
    PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    /**
     */
    void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    /**
     */
    List<PatientPortalRelation> getAllPatientPortalRelation();

    /**
     */
    List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient);

    /**
     */
    List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person);

    /**
     */
    List<PatientPortalRelation> getPatientPortalRelationByRelatedPerson(Person person);

    /**
     *
     */
    PatientPortalRelation getPatientPortalRelation(Patient requestedPatient, Person requestedPerson, User requestingUser);


    /**
     */
    void updatePatientPortalRelation(User user, Person person, String uuid);
}
