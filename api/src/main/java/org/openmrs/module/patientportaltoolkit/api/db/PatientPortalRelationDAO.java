/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
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
