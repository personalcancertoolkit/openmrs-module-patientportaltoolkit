package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maurya on 9/19/16.
 */
@Transactional
public interface PersonPreferencesService {

    @Transactional(readOnly = true)
    PersonPreferences getPersonPreferencesByUuid(String uuid);

    @Transactional(readOnly = true)
    PersonPreferences getPersonPreferencesByPerson(Person person);

    @Transactional(readOnly = true)
    List<PersonPreferences> getAllPersonPreferences();

    @Transactional(readOnly = true)
    List<PersonPreferences> getAllEnrolledPersonPreferences();

    @Transactional(readOnly = false)
    PersonPreferences savePersonPreferences(PersonPreferences personPreferences);

    @Transactional(readOnly = false)
    void deletePersonPreferences(PersonPreferences personPreferences);
}
