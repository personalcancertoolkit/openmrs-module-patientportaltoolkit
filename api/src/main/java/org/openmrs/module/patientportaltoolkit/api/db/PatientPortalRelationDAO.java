/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
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

    List<PatientPortalRelation> getAcceptedPatientPortalRelationByRelatedPerson(Person person);

    /**
     *
     */
    PatientPortalRelation getPatientPortalRelation(Person person, Person requestedPerson, User requestingUser);


    /**
     */
    void updatePatientPortalRelation(User user, Person person, String uuid);
}
