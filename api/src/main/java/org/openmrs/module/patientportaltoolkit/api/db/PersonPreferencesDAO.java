package org.openmrs.module.patientportaltoolkit.api.db;

import org.hibernate.SessionFactory;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;

import java.util.List;

/**
 * Created by maurya on 9/19/16.
 */
public interface PersonPreferencesDAO {

    /**
     * Set Hibernate session factory
     *
     * @param sessionFactory Hibernate session factory object
     */
    void setSessionFactory(SessionFactory sessionFactory);

    PersonPreferences getPersonPreferenceByUuid(String uuid);

    PersonPreferences savePersonPreferences(PersonPreferences personPreferences);

    /**
     */
    void deletePersonPreferences(PersonPreferences personPreferences);

    /**
     */
    List<PersonPreferences> getAllPersonPreferences();

    /**
     */
    PersonPreferences getPersonPreferencesByPerson(Person person);

}
