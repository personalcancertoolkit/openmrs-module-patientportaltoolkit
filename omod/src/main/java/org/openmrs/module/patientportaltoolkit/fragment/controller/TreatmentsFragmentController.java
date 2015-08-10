package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.ui.framework.page.PageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maurya on 19/06/2015.
 */
public class TreatmentsFragmentController {

    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
        if (patient !=null) {
            model.addAttribute("surgeryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.SURGERY_ENCOUNTER));
            model.addAttribute("chemotherapyConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER));
            model.addAttribute("latestTreatmentSummary", GenerateTreatmentClassesUtil.generateLatestGeneralHistory(patient));
            model.addAttribute("treatmentsummary", GenerateTreatmentClassesUtil.generateGeneralHistory(patient));
            model.addAttribute("radiationencounters", GenerateTreatmentClassesUtil.generateRadiations(patient));
            model.addAttribute("surgeryencounters",GenerateTreatmentClassesUtil.generateSurgeries(patient));
            model.addAttribute("chemotherapyencounters",GenerateTreatmentClassesUtil.generateChemotherapies(patient));
        }
        else {
            model.addAttribute("treatmentsummary",null);
            model.addAttribute("radiationencounters",null);
            model.addAttribute("surgeryencounters",null);
            model.addAttribute("chemotherapyencounters",null);
        }
    }

}
