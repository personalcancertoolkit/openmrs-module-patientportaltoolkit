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
