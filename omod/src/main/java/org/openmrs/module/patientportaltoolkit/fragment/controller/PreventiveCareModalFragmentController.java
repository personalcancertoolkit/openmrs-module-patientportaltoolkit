package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maurya on 2/27/17.
 */
public class PreventiveCareModalFragmentController {
    public void controller(PageModel model, PageRequest pageRequest) {
        PatientPortalFormService patientPortalFormService= Context.getService(PatientPortalFormService.class);
        model.addAttribute("influenzaConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.INFLUENZA_VACCINE));
        model.addAttribute("pneumococcalConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.PNEUMOCOCCAL_VACCINE));
        model.addAttribute("cholesterolConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CHOLESTEROL_SCREENING));
        model.addAttribute("bpConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.BP_SCREENING));
        model.addAttribute("hivConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.HIV_SCREENING));
        model.addAttribute("mammographyConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.MAMMOGRAPHY_SCREENING));
        model.addAttribute("cervicalConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CERVICAL_CANCER_SCREENING));
    }
    public void saveInfluenzaForm(FragmentModel model, @RequestParam(value = "influenzaDate") String influenzaDate, HttpServletRequest servletRequestest) throws ParseException {

        //log.info(PPTLogAppender.appendLog("NEW_SURGERY", servletRequestest, "surgeryTypes:", surgeryTypes, "surgeryComplications:", surgeryComplications, "majorComplicationsTypeAnswer:", majorComplicationsTypeAnswer, "surgeryDate:", surgeryDate, "surgeonPcpName:", surgeonPcpName, "surgeonPcpEmail:", surgeonPcpEmail, "surgeonPcpPhone:", surgeonPcpPhone, "surgeryInstitutionName:", surgeryInstitutionName, "surgeryInstitutionCity:", surgeryInstitutionCity, "surgeryInstitutionState:", surgeryInstitutionState));

        EncounterService encounterService= Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter newInfluenzaEncounter = new Encounter();
        newInfluenzaEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        Date date = new Date();
        newInfluenzaEncounter.setDateCreated(new Date());
        newInfluenzaEncounter.setEncounterDatetime(date);
        newInfluenzaEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.INFLUENZA_VACCINE));
        if (influenzaDate != null && influenzaDate != "") {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("f1cba252-751f-470b-871b-2399565af396"));
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date parsedDate = formatter.parse(influenzaDate);
            o.setValueDate(parsedDate);
            newInfluenzaEncounter.addObs(o);
        }
        encounterService.saveEncounter(newInfluenzaEncounter);
        //log.info("Save New Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
