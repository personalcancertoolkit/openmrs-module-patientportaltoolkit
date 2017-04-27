package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
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
public class PreventiveCareManageModalFragmentController {
    public void controller(PageModel model, PageRequest pageRequest) {
        
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
            encounterService.saveEncounter(newInfluenzaEncounter);
        }

        //log.info("Save New Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

    public void savePneumococcalForm(FragmentModel model, @RequestParam(value = "pneumococcalDate") String pneumococcalDate, HttpServletRequest servletRequestest) throws ParseException {

        EncounterService encounterService= Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter newPneumococcalEncounter = new Encounter();
        newPneumococcalEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        Date date = new Date();
        newPneumococcalEncounter.setDateCreated(new Date());
        newPneumococcalEncounter.setEncounterDatetime(date);
        newPneumococcalEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.PNEUMOCOCCAL_VACCINE));
        if (pneumococcalDate != null && pneumococcalDate != "") {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("c93df44f-d5b7-49a6-8539-e8265c03dbb3"));
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date parsedDate = formatter.parse(pneumococcalDate);
            o.setValueDate(parsedDate);
            newPneumococcalEncounter.addObs(o);
            encounterService.saveEncounter(newPneumococcalEncounter);
        }
        //log.info("Save New Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

    public void saveCholesterolForm(FragmentModel model, @RequestParam(value = "cholesterolDate") String cholesterolDate,  @RequestParam(value = "cholesterolLDLNumber") Integer cholesterolLDLNumber, @RequestParam(value = "cholesterolTotalNumber") Integer cholesterolTotalNumber, HttpServletRequest servletRequestest) throws ParseException {

        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter newCholesterolEncounter = new Encounter();
        newCholesterolEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        Date date = new Date();
        newCholesterolEncounter.setDateCreated(new Date());
        newCholesterolEncounter.setEncounterDatetime(date);
        newCholesterolEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.CHOLESTEROL_SCREENING));
        if (cholesterolDate != null && cholesterolDate != "") {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("01f5d7c7-f0c5-4329-8b2d-2053155a962f"));
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date parsedDate = formatter.parse(cholesterolDate);
            o.setValueDate(parsedDate);
            newCholesterolEncounter.addObs(o);
        }
        if (cholesterolLDLNumber != null) {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("b0a44f7a-4188-44b3-b86f-955a32d8f4cd"));
            o.setValueNumeric(Double.valueOf(cholesterolLDLNumber));
            newCholesterolEncounter.addObs(o);
        }
        if (cholesterolTotalNumber != null) {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("4788cb2c-6324-412f-b617-31ef341e7455"));
            o.setValueNumeric(Double.valueOf(cholesterolTotalNumber));
            newCholesterolEncounter.addObs(o);
        }
        encounterService.saveEncounter(newCholesterolEncounter);
        //log.info("Save New Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
