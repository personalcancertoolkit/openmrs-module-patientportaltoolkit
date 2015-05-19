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

import java.util.List;
import java.util.Map;

/**
 * Created by maurya.
 */
public interface PatientService {

    /**
     * Get patient resource by uuid
     *
     * @param id uuid of the patient
     * @return patient resource and will return null if patient not found for the given id
     *
     * @should not return null value
     */
    Map<String, Object> getPatient(String id);

    /**
     * get all patient resources
     *
     *
     * @return list of all patients
     *
     * @should not return null value
     */
    List<Object> getAllPatients() ;

}
