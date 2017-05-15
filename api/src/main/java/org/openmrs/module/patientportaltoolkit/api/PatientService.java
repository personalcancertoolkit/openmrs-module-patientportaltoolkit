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

import org.openmrs.Patient;

import java.util.List;

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
     Patient getPatient(String id);

    /**
     * get all patient resources
     *
     *
     * @return list of all patients
     *
     * @should not return null value
     */
    List<Patient> getAllPatients() ;

    /**
     * update patient resource
     *
     * @param patientObject json provided by the client
     * @return patient resource and will return null if patient not found for the given id
     */
     Patient updatePatient(String patientJson);
}
