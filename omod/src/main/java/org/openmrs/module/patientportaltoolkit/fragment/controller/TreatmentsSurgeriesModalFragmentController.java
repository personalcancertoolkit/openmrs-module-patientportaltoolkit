/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 29/07/2015.
 */
public class TreatmentsSurgeriesModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SURGERIES_FRAGMENT", pageRequest.getRequest()));
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
        model.addAttribute("surgeryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.SURGERY_ENCOUNTER));

    }

    public void saveNewSurgeryForm(FragmentModel model, @RequestParam(value = "surgeryTypes", required = false) String surgeryTypes,
                                   @RequestParam(value = "surgeryComplications", required = false) String surgeryComplications,
                                   @RequestParam(value = "majorComplicationsTypeAnswer", required = false) String majorComplicationsTypeAnswer,
                                   @RequestParam(value = "surgeryDate", required = false) String surgeryDate,
                                   @RequestParam(value = "surgeonPcpName", required = false) String surgeonPcpName,
                                   @RequestParam(value = "surgeonPcpEmail", required = false) String surgeonPcpEmail,
                                   @RequestParam(value = "surgeonPcpPhone", required = false) String surgeonPcpPhone,
                                   @RequestParam(value = "surgeryInstitutionName", required = false) String surgeryInstitutionName,
                                   @RequestParam(value = "surgeryInstitutionCity", required = false) String surgeryInstitutionCity,
                                   @RequestParam(value = "surgeryInstitutionState", required = false) String surgeryInstitutionState,
                                   @RequestParam(value = "patientUuid", required = false) String patientUuid,
                                   HttpServletRequest servletRequestest) throws ParseException {

        log.info(PPTLogAppender.appendLog("NEW_SURGERY", servletRequestest, "surgeryTypes:", surgeryTypes, "surgeryComplications:", surgeryComplications, "majorComplicationsTypeAnswer:", majorComplicationsTypeAnswer, "surgeryDate:", surgeryDate, "surgeonPcpName:", surgeonPcpName, "surgeonPcpEmail:", surgeonPcpEmail, "surgeonPcpPhone:", surgeonPcpPhone, "surgeryInstitutionName:", surgeryInstitutionName, "surgeryInstitutionCity:", surgeryInstitutionCity, "surgeryInstitutionState:", surgeryInstitutionState));
        EncounterService encounterService= Context.getEncounterService();
        Encounter newSurgeryEncounter = new Encounter();
        if (patientUuid == null || patientUuid.isEmpty()){
            newSurgeryEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        }
        else{
            newSurgeryEncounter.setPatient(Context.getPatientService().getPatientByUuid(patientUuid));
        }
        Date date = new Date();
        newSurgeryEncounter.setDateCreated(new Date());
        newSurgeryEncounter.setEncounterDatetime(date);
        newSurgeryEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.SURGERY_ENCOUNTER));
        ConceptService conceptService=Context.getConceptService();
        String[] str_array = surgeryTypes.split("split");
        List<String> surgeryTypesConcepts = new ArrayList<>();
        for(String s: str_array){
            surgeryTypesConcepts.add(s);
        }
        for (String s : surgeryTypesConcepts){
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("d409122c-8a0b-4282-a17f-07abad81f278"));
            o.setValueCoded(conceptService.getConceptByUuid(s));
            newSurgeryEncounter.addObs(o);
        }
        Obs suregeryInstitution = new Obs();
        suregeryInstitution.setConcept(conceptService.getConceptByUuid("329328ab-8e1c-461e-9261-fd4471b1f131"));

        Obs surgeon = new Obs();
        surgeon.setConcept(conceptService.getConceptByUuid("292e2107-b909-4e4a-947f-ce2be8738137"));

        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("surgeryComplications");//99ef1d68-05ed-4f37-b98b-c982e3574138
        allTheEnteredValues.add("majorComplicationsTypeAnswer");
        allTheEnteredValues.add("surgeryDate");
        allTheEnteredValues.add("surgeonPcpName");
        allTheEnteredValues.add("surgeonPcpEmail");
        allTheEnteredValues.add("surgeonPcpPhone");
        allTheEnteredValues.add("surgeryInstitutionName");
        allTheEnteredValues.add("surgeryInstitutionCity");
        allTheEnteredValues.add("surgeryInstitutionState");
        for (String entry : allTheEnteredValues)
        {
            if(entry !=null) {
                switch (entry) {
                    case "surgeryComplications":
                        if (surgeryComplications != null && surgeryComplications != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("99ef1d68-05ed-4f37-b98b-c982e3574138"));
                            o.setValueCoded(conceptService.getConceptByUuid(surgeryComplications));
                            newSurgeryEncounter.addObs(o);
                        }
                        break;
                    case "majorComplicationsTypeAnswer":
                        if (majorComplicationsTypeAnswer != null && majorComplicationsTypeAnswer != "") {
                                Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("c2d9fca3-1e0b-4007-8c3c-b3ebb4e67963"));
                            o.setValueText(majorComplicationsTypeAnswer);
                            newSurgeryEncounter.addObs(o);
                            }
                        break;
                    case "surgeryDate":
                        if (surgeryDate != null && surgeryDate != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("87a69397-65ef-4576-a709-ae0a526afd85"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(surgeryDate);
                            o.setValueDate(parsedDate);
                            newSurgeryEncounter.addObs(o);
                            }
                        break;
                    case "surgeonPcpName":
                        Obs surgeonPcpNameObs = new Obs();
                        surgeonPcpNameObs.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                        surgeonPcpNameObs.setValueText(surgeonPcpName);
                        surgeon.addGroupMember(surgeonPcpNameObs);
                        break;
                    case "surgeonPcpEmail":
                        Obs surgeonPcpEmailObs = new Obs();
                        surgeonPcpEmailObs.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                        surgeonPcpEmailObs.setValueText(surgeonPcpEmail);
                        surgeon.addGroupMember(surgeonPcpEmailObs);
                        break;
                    case "surgeonPcpPhone":
                        Obs surgeonPcpPhoneObs = new Obs();
                        surgeonPcpPhoneObs.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                        surgeonPcpPhoneObs.setValueText(surgeonPcpPhone);
                        surgeon.addGroupMember(surgeonPcpPhoneObs);
                        break;
                    case "surgeryInstitutionName":
                        Obs surgeryInstitutionNameObs = new Obs();
                        surgeryInstitutionNameObs.setConcept(conceptService.getConceptByUuid("47d58999-d3b5-4869-a52e-841e2e6bdbb3"));
                        surgeryInstitutionNameObs.setValueText(surgeryInstitutionName);
                        suregeryInstitution.addGroupMember(surgeryInstitutionNameObs);
                        break;
                    case "surgeryInstitutionCity":
                        Obs surgeryInstitutionCityObs = new Obs();
                        surgeryInstitutionCityObs.setConcept(conceptService.getConceptByUuid("bfa752d6-2037-465e-b0a2-c4c2d485ec32"));
                        surgeryInstitutionCityObs.setValueText(surgeryInstitutionCity);
                        suregeryInstitution.addGroupMember(surgeryInstitutionCityObs);
                        break;
                    case "surgeryInstitutionState":
                        Obs surgeryInstitutionStateobs = new Obs();
                        surgeryInstitutionStateobs.setConcept(conceptService.getConceptByUuid("34489100-487e-443a-bf27-1b6869fb9332"));
                        surgeryInstitutionStateobs.setValueText(surgeryInstitutionState);
                        suregeryInstitution.addGroupMember(surgeryInstitutionStateobs);
                        break;
                }
            }
        }
        newSurgeryEncounter.addObs(suregeryInstitution);
        newSurgeryEncounter.addObs(surgeon);
        encounterService.saveEncounter(newSurgeryEncounter);
        //log.info("Save New Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
    public void saveSurgeryForm(FragmentModel model,  @RequestParam(value = "encounterId", required = false) String encounterId,
                         @RequestParam(value = "surgeryTypes", required = false) String surgeryTypes,
                         @RequestParam(value = "surgeryComplications", required = false) String surgeryComplications,
                         @RequestParam(value = "majorComplicationsTypeAnswer", required = false) String majorComplicationsTypeAnswer,
                         @RequestParam(value = "surgeryDate", required = false) String surgeryDate,
                         @RequestParam(value = "surgeonPcpName", required = false) String surgeonPcpName,
                         @RequestParam(value = "surgeonPcpEmail", required = false) String surgeonPcpEmail,
                         @RequestParam(value = "surgeonPcpPhone", required = false) String surgeonPcpPhone,
                         @RequestParam(value = "surgeryInstitutionName", required = false) String surgeryInstitutionName,
                         @RequestParam(value = "surgeryInstitutionCity", required = false) String surgeryInstitutionCity,
                         @RequestParam(value = "surgeryInstitutionState", required = false) String surgeryInstitutionState, HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("UPDATE_SURGERY",servletRequest,"encounterId:", encounterId ,"surgeryTypes:", surgeryTypes, "surgeryComplications:", surgeryComplications, "majorComplicationsTypeAnswer:", majorComplicationsTypeAnswer, "surgeryDate:", surgeryDate, "surgeonPcpName:", surgeonPcpName, "surgeonPcpEmail:", surgeonPcpEmail, "surgeonPcpPhone:", surgeonPcpPhone, "surgeryInstitutionName:", surgeryInstitutionName, "surgeryInstitutionCity:", surgeryInstitutionCity, "surgeryInstitutionState:", surgeryInstitutionState));
        EncounterService encounterService= Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        String[] str_array = surgeryTypes.split("split");
        List<String> surgeryTypeConcepts = new ArrayList<>();
        for(String s: str_array){
            surgeryTypeConcepts.add(s);
        }
        List<String> existingSurgeryTypeConcepts = new ArrayList<>();
        if(encounterId !=null) {
            Encounter surgeryEncounter = encounterService.getEncounterByUuid(encounterId);
            for (Obs o:surgeryEncounter.getObs()){
                switch (o.getConcept().getUuid()){
                    case "d409122c-8a0b-4282-a17f-07abad81f278":
                        existingSurgeryTypeConcepts.add(o.getValueCoded().getUuid());
                        break;
                    case "99ef1d68-05ed-4f37-b98b-c982e3574138":
                        if(o.getValueCoded().getUuid()!=surgeryComplications)
                            o.setValueCoded(conceptService.getConceptByUuid(surgeryComplications));
                        break;
                    case "c2d9fca3-1e0b-4007-8c3c-b3ebb4e67963":
                        if(!o.getValueText().equals(majorComplicationsTypeAnswer))
                            o.setValueText(majorComplicationsTypeAnswer);
                        break;
                    case "87a69397-65ef-4576-a709-ae0a526afd85":
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        Date parsedDate = formatter.parse(surgeryDate);
                        if(o.getValueDate()!=parsedDate)
                            o.setValueDate(parsedDate);
                        break;
                    case "c2cb2220-c07d-47c6-a4df-e5918aac3fc2":
                        if(o.getValueText()!=surgeonPcpName)
                            o.setValueText(surgeonPcpName);
                        break;
                    case "898a0028-8c65-4db9-a802-1577fce59864":
                        if(o.getValueText()!=surgeonPcpEmail)
                            o.setValueText(surgeonPcpEmail);
                        break;
                    case "9285b227-4054-4830-ac32-5ea78462e8c4":
                        if(o.getValueText()!=surgeonPcpPhone)
                            o.setValueText(surgeonPcpPhone);
                        break;
                    case "47d58999-d3b5-4869-a52e-841e2e6bdbb3":
                        if(o.getValueText()!=surgeryInstitutionName)
                            o.setValueText(surgeryInstitutionName);
                        break;
                    case "bfa752d6-2037-465e-b0a2-c4c2d485ec32":
                        if(o.getValueText()!=surgeryInstitutionCity)
                            o.setValueText(surgeryInstitutionCity);
                        break;
                    case "34489100-487e-443a-bf27-1b6869fb9332":
                        if(o.getValueText()!=surgeryInstitutionState)
                            o.setValueText(surgeryInstitutionState);
                        break;
                }
            }
            for(String s: existingSurgeryTypeConcepts){
                if (surgeryTypeConcepts.contains(s))
                    surgeryTypeConcepts.remove(s);
                else {
                    for (Obs o : surgeryEncounter.getObs()) {
                        if (o.getConcept().getUuid().equals("d409122c-8a0b-4282-a17f-07abad81f278")) {
                            if (o.getValueCoded().getUuid().equals(s))
                                o.setVoided(true);
                        }
                    }
                }

            }
            for (String s : surgeryTypeConcepts){
                Obs o = new Obs();
                o.setConcept(conceptService.getConceptByUuid("d409122c-8a0b-4282-a17f-07abad81f278"));
                o.setValueCoded(conceptService.getConceptByUuid(s));
                surgeryEncounter.addObs(o);
            }
            encounterService.saveEncounter(surgeryEncounter);
          //  log.info("Update Surgery for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        }

        }
}
