package org.openmrs.module.patientportaltoolkit.page.controller;


import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.ArrayList;
import java.util.List;


public class AddNewTreatmentsPageController {

    public void controller(PageModel model, PageRequest pageRequest) {
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
        model.addAttribute("newtreatmentSummaryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER));
       /* List<Patient> omrspatients = Context.getPatientService().getAllPatients();
        List<Object> patients = new ArrayList<Object>();
        for(Patient p: omrspatients) {
            patients.add(ToolkitResourceUtil.generatePerson(Context.getPatientService().getPatientByUuid(p.getUuid())));
        }
        System.out.println("----Patients------->\n"+patients);
        model.addAttribute("listOfAllPatients",patients);*/
    }


}
