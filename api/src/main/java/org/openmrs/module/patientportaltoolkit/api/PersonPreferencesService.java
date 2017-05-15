/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Person;
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
