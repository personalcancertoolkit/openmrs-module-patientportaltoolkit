package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

public class AddNewTreatmentPageController {

    public void controller(PageModel model, PageRequest pageRequest) {
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
        model.addAttribute("treatmentSummaryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER));
    }

}
