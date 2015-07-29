package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
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
