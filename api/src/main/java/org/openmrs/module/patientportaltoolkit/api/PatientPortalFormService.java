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
