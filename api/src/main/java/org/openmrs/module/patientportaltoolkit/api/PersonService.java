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

import java.util.Map;

/**
 * Created by maurya.
 */
public interface PersonService {

    /**
     * Get person resource by uuid
     *
     * @param id uuid of the patient
     * @return patient resource and will return null if patient not found for the given id
     *
     * @should not return null value
     * @should return proper formatted date
     */

    Map<String, Object> getPerson(String id);

    /**
     * update person object
     *
     * @param personJson json provided by the client
     * @return person resource and will return null if patient not found for the given id
     */
    Object updatePerson(String personJson);
}
