package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.ui.framework.page.PageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maurya on 19/06/2015.
 */
public class TreatmentsFragmentController {

    private final static String  RADIATION_ENCOUNTER = "CANCER TREATMENT - RADIATION";
    private final static String  CHEMOTHERAPY_ENCOUNTER = "CANCER TREATMENT - CHEMOTHERAPY";
    private final static String  SURGERY_ENCOUNTER = "CANCER TREATMENT - SURGERY";
    private final static String  TREATMENTSUMMARY_ENCOUNTER = "CANCER TREATMENT SUMMARY";
    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null) {
            model.addAttribute("encounterss", getEncountersByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER));
            model.addAttribute("treatmentsummary", GenerateTreatmentClassesUtil.generateGeneralHistory(patient));
            model.addAttribute("radiationencounters",getEncountersByTreatment(patient, RADIATION_ENCOUNTER));
            model.addAttribute("surgeryencounters",getEncountersByTreatment(patient, SURGERY_ENCOUNTER));
            model.addAttribute("chemotherapyencounters",getEncountersByTreatment(patient, CHEMOTHERAPY_ENCOUNTER));
        }
        else {
            model.addAttribute("treatmentsummary",null);
            model.addAttribute("radiationencounters",null);
            model.addAttribute("surgeryencounters",null);
            model.addAttribute("chemotherapyencounters",null);
        }
    }

    public List<Encounter> getEncountersByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        List<Encounter> treatmentEncounters = new ArrayList<Encounter>();
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                treatmentEncounters.add(encounter);
            }
        }
        return treatmentEncounters;
    }
}
