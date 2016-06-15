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

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */
public interface PatientPortalFormService  extends OpenmrsService {

    @Transactional(readOnly = true)
    List<PatientPortalForm> getAllPatientPortalForms();

    @Transactional(readOnly = true)
    PatientPortalForm getPatientPortalForm(String uuid);

    @Transactional(readOnly = true)
    PatientPortalForm getPatientPortalFormByFormType(String formType);
}
