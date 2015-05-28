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
    public void setSessionFactory(SessionFactory sessionFactory);

    /**
     * Get relation of a uuid
     *
     * @param uuid of the relation
     * @return Relation
     */
    public PatientPortalRelation getPatientPortalRelation(String uuid);


    /**
     */
    public PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    /**
     */
    public void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation);

    /**
     */
    public List<PatientPortalRelation> getAllPatientPortalRelation();

    /**
     */
    public List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient);

    /**
     */
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person);

    /**
     *
     */
    public PatientPortalRelation getPatientPortalRelation(Patient requestedPatient, Person requestedPerson, User requestingUser);


    /**
     */
    public void updatePatientPortalRelation(User user, Person person, String uuid);
}
