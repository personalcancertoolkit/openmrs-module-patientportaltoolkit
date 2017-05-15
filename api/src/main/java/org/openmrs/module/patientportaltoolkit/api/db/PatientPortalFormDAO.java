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

import org.openmrs.module.patientportaltoolkit.PatientPortalForm;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */
public interface PatientPortalFormDAO {

    void deletePatientPortalForm(PatientPortalForm patientPortalForm);

    List<PatientPortalForm> getAllPatientPortalForms();

    PatientPortalForm getPatientPortalForm(String uuid);

    PatientPortalForm getPatientPortalFormByFormType(String formType);

    void savePatientPortalForm(PatientPortalForm patientPortalForm);

    void softDeletePatientPortalForm (PatientPortalForm patientPortalForm);
}
