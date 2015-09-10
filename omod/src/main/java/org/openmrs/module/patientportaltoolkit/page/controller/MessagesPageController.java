package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maurya on 9/7/15.
 */
public class MessagesPageController {

    public void controller(PageModel model) {
        Patient patient = null;
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null)
            model.addAttribute("messages", Context.getService(MessageService.class).getMessageForPerson(patient,true));
        else
            model.addAttribute("messages",null);
    }

    public Object returnRelationships( @RequestParam(value = "searchQuery", required = true) String searchQuery){
        Patient patient = null;
        List<String> returnnames=new ArrayList<>();
        patient= Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        if (patient !=null) {
            List<PatientPortalRelation> pptrelations = Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPatient(patient);
        for( PatientPortalRelation pptr: pptrelations){
            returnnames.add(pptr.getRelatedPersonName());
        }
        }
        return returnnames;
    }
}
