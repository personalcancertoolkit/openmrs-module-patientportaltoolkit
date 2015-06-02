package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.PatientPortalForm;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */
public interface PatientPortalFormDAO {

    public void deletePatientPortalForm(PatientPortalForm patientPortalForm);

    public List<PatientPortalForm> getAllPatientPortalForms();

    public PatientPortalForm getPatientPortalForm(String uuid);

    public void savePatientPortalForm(PatientPortalForm patientPortalForm);

    public void softDeletePatientPortalForm (PatientPortalForm patientPortalForm);
}
