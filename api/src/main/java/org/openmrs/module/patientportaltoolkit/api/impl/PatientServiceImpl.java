/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import java.util.List;

/**
 * Created by maurya.
 */
public class PatientServiceImpl extends BaseOpenmrsService implements PatientService {

    @Override
    public Patient getPatient(String patientId) {
        Patient patient = Context.getPatientService().getPatientByUuid(patientId);
        return patient;
    }

    @Override
    public List<Patient> getAllPatients() {
        return Context.getPatientService().getAllPatients();
    }

    @Override
    public Patient updatePatient(String patientJson) {
        return ToolkitResourceUtil.updatePatient(patientJson);
    }
}
