package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maurya on 19/06/2015.
 */
public class TreatmentsFragmentController {


    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_TREATMENTS_FRAGMENT", pageRequest.getRequest()));
        Patient patient = null;
        Person person = (Person) model.get("person");
       // if(Context.getAuthenticatedUser().getPerson().isPatient()){
        if(person.isPatient()){
        patient= Context.getPatientService().getPatientByUuid(person.getUuid());
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
            model.addAttribute("surgeryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.SURGERY_ENCOUNTER));
            model.addAttribute("chemotherapyConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER));
            model.addAttribute("radiationConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.RADIATION_ENCOUNTER));
            model.addAttribute("latestTreatmentSummary", GenerateTreatmentClassesUtil.generateLatestGeneralHistory(patient));
            model.addAttribute("treatmentsummary", GenerateTreatmentClassesUtil.generateGeneralHistory(patient));
            model.addAttribute("radiationencounters", GenerateTreatmentClassesUtil.generateRadiations(patient));
            model.addAttribute("surgeryencounters",GenerateTreatmentClassesUtil.generateSurgeries(patient));
            model.addAttribute("chemotherapyencounters",GenerateTreatmentClassesUtil.generateChemotherapies(patient));
        }
        else {
            model.addAttribute("surgeryConcepts",null);
            model.addAttribute("chemotherapyConcepts", null);
            model.addAttribute("radiationConcepts", null);
            model.addAttribute("latestTreatmentSummary", null);
            model.addAttribute("treatmentsummary", null);
            model.addAttribute("radiationencounters", null);
            model.addAttribute("surgeryencounters",null);
            model.addAttribute("chemotherapyencounters",null);
        }
        //log.info("Treatments requested for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

}
