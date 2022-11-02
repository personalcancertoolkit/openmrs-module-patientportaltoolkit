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
import java.util.*;

/**
 * Created by Maurya on 10/08/2015.
 */
public class TreatmentsChemotherapyModalFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_CHEMOTHERAPY_FRAGMENT", pageRequest.getRequest()));
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
        model.addAttribute("chemotherapyConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER));

    }

    public void saveNewChemotherapyForm(FragmentModel model, @RequestParam(value = "chemotherapyMeds", required = false) String chemotherapyMeds,
                                        @RequestParam(value = "otherChemotherapyMedicationName", required = false) String otherChemotherapyMedicationName,
                                        @RequestParam(value = "centralLine", required = false) String centralLine,
                                        @RequestParam(value = "chemoStartDate", required = false) String chemoStartDate,
                                        @RequestParam(value = "chemoEndDate", required = false) String chemoEndDate,
                                        @RequestParam(value = "chemotherapyPcpName", required = false) String chemotherapyPcpName,
                                        @RequestParam(value = "chemotherapyPcpEmail", required = false) String chemotherapyPcpEmail,
                                        @RequestParam(value = "chemotherapyPcpPhone", required = false) String chemotherapyPcpPhone,
                                        @RequestParam(value = "chemotherapyInstitutionName", required = false) String chemotherapyInstitutionName,
                                        @RequestParam(value = "chemotherapyInstitutionCity", required = false) String chemotherapyInstitutionCity,
                                        @RequestParam(value = "chemotherapyInstitutionState", required = false) String chemotherapyInstitutionState,
                                        @RequestParam(value = "patientUuid", required = false) String patientUuid, HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("NEW_CHEMOTHERAPY", servletRequest, "chemotherapyMeds:", chemotherapyMeds, "centralLine:", centralLine, "chemoStartDate:", chemoStartDate, "chemoEndDate:", chemoEndDate, "chemotherapyPcpName:", chemotherapyPcpName, "chemotherapyPcpEmail:", chemotherapyPcpEmail, "chemotherapyPcpPhone:", chemotherapyPcpPhone, "chemotherapyInstitutionName:", chemotherapyInstitutionName, "chemotherapyInstitutionCity:", chemotherapyInstitutionCity, "chemotherapyInstitutionState:", chemotherapyInstitutionState));
        EncounterService encounterService = Context.getEncounterService();
        Encounter newChemotherapyEncounter = new Encounter();
        if (patientUuid == null || patientUuid.isEmpty()){
            newChemotherapyEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        }
        else{
            newChemotherapyEncounter.setPatient(Context.getPatientService().getPatientByUuid(patientUuid));
        }
        Date date = new Date();
        newChemotherapyEncounter.setDateCreated(new Date());
        newChemotherapyEncounter.setEncounterDatetime(date);
        newChemotherapyEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER));
        ConceptService conceptService = Context.getConceptService();
        String[] str_array = chemotherapyMeds.split("split");
        List<String> chemotherapyMedictionConcepts = new ArrayList<>();
        for (String s : str_array) {
            chemotherapyMedictionConcepts.add(s);
        }


        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("otherChemotherapyMedicationName");
        allTheEnteredValues.add("centralLine");
        allTheEnteredValues.add("chemoStartDate");
        allTheEnteredValues.add("chemoEndDate");
        allTheEnteredValues.add("chemotherapyPcpName");
        allTheEnteredValues.add("chemotherapyPcpEmail");
        allTheEnteredValues.add("chemotherapyPcpPhone");
        allTheEnteredValues.add("chemotherapyInstitutionName");
        allTheEnteredValues.add("chemotherapyInstitutionCity");
        allTheEnteredValues.add("chemotherapyInstitutionState");
        for (String s : chemotherapyMedictionConcepts) {
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("8481b9da-74e3-45a9-9124-d69ab572d636"));
            o.setValueCoded(conceptService.getConceptByUuid(s));
            newChemotherapyEncounter.addObs(o);
        }
        Obs chemotherapyInstitution = new Obs();
        chemotherapyInstitution.setConcept(conceptService.getConceptByUuid("329328ab-8e1c-461e-9261-fd4471b1f131"));

        Obs oncologist = new Obs();
        oncologist.setConcept(conceptService.getConceptByUuid("5cbdb3c4-a531-4da5-b1e3-d5fd6420693a"));

        for (String entry : allTheEnteredValues) {
            if (entry != null) {
                switch (entry) {
                    case "centralLine":
                        if (centralLine != null && centralLine != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("361b7f9b-a985-4b18-9055-03af3b41b8b3"));
                            o.setValueCoded(conceptService.getConceptByUuid(centralLine));
                            newChemotherapyEncounter.addObs(o);
                        }
                        break;
                    case "chemoStartDate":
                        if (chemoStartDate != null && chemoStartDate != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("85c3a99e-0598-4c63-912b-796dee9c75b2"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(chemoStartDate);
                            o.setValueDate(parsedDate);
                            newChemotherapyEncounter.addObs(o);
                        }
                        break;
                    case "chemoEndDate":
                        if (chemoEndDate != null && chemoEndDate != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(chemoEndDate);
                            o.setValueDate(parsedDate);
                            newChemotherapyEncounter.addObs(o);
                        }
                        break;
                    case "chemotherapyPcpName":
                        Obs chemotherapyPcpNameObs = new Obs();
                        chemotherapyPcpNameObs.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                        chemotherapyPcpNameObs.setValueText(chemotherapyPcpName);
                        oncologist.addGroupMember(chemotherapyPcpNameObs);
                        break;
                    case "chemotherapyPcpEmail":
                        Obs chemotherapyPcpEmailObs = new Obs();
                        chemotherapyPcpEmailObs.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                        chemotherapyPcpEmailObs.setValueText(chemotherapyPcpEmail);
                        oncologist.addGroupMember(chemotherapyPcpEmailObs);
                        break;
                    case "chemotherapyPcpPhone":
                        Obs chemotherapyPcpPhoneObs = new Obs();
                        chemotherapyPcpPhoneObs.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                        chemotherapyPcpPhoneObs.setValueText(chemotherapyPcpPhone);
                        oncologist.addGroupMember(chemotherapyPcpPhoneObs);
                        break;
                    case "chemotherapyInstitutionName":
                        Obs chemotherapyInstitutionNameObs = new Obs();
                        chemotherapyInstitutionNameObs.setConcept(conceptService.getConceptByUuid("47d58999-d3b5-4869-a52e-841e2e6bdbb3"));
                        chemotherapyInstitutionNameObs.setValueText(chemotherapyInstitutionName);
                        chemotherapyInstitution.addGroupMember(chemotherapyInstitutionNameObs);
                        break;
                    case "chemotherapyInstitutionCity":
                        Obs chemotherapyInstitutionCityObs = new Obs();
                        chemotherapyInstitutionCityObs.setConcept(conceptService.getConceptByUuid("bfa752d6-2037-465e-b0a2-c4c2d485ec32"));
                        chemotherapyInstitutionCityObs.setValueText(chemotherapyInstitutionCity);
                        chemotherapyInstitution.addGroupMember(chemotherapyInstitutionCityObs);
                        break;
                    case "chemotherapyInstitutionState":
                        Obs chemotherapyInstitutionobs = new Obs();
                        chemotherapyInstitutionobs.setConcept(conceptService.getConceptByUuid("34489100-487e-443a-bf27-1b6869fb9332"));
                        chemotherapyInstitutionobs.setValueText(chemotherapyInstitutionState);
                        chemotherapyInstitution.addGroupMember(chemotherapyInstitutionobs);
                        break;
                    case "otherChemotherapyMedicationName":
                        Obs otherChemotherapyMedicationNameObs = new Obs();
                        otherChemotherapyMedicationNameObs.setConcept(conceptService.getConceptByUuid("3a49bfbb-df57-4c51-9f14-f05f848093e0"));
                        otherChemotherapyMedicationNameObs.setValueText(otherChemotherapyMedicationName);
                        newChemotherapyEncounter.addObs(otherChemotherapyMedicationNameObs);
                        break;

                }
            }
        }
        newChemotherapyEncounter.addObs(chemotherapyInstitution);
        newChemotherapyEncounter.addObs(oncologist);
        encounterService.saveEncounter(newChemotherapyEncounter);
        //log.info("Save New Chemotherapy for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

    public void saveChemotherapyForm(FragmentModel model, @RequestParam(value = "encounterId", required = false) String encounterId,
                                     @RequestParam(value = "otherChemotherapyMedicationName", required = false) String otherChemotherapyMedicationName,
                                     @RequestParam(value = "chemotherapyMeds", required = false) String chemotherapyMeds,
                                     @RequestParam(value = "centralLine", required = false) String centralLine,
                                     @RequestParam(value = "chemoStartDate", required = false) String chemoStartDate,
                                     @RequestParam(value = "chemoEndDate", required = false) String chemoEndDate,
                                     @RequestParam(value = "chemotherapyPcpName", required = false) String chemotherapyPcpName,
                                     @RequestParam(value = "chemotherapyPcpEmail", required = false) String chemotherapyPcpEmail,
                                     @RequestParam(value = "chemotherapyPcpPhone", required = false) String chemotherapyPcpPhone,
                                     @RequestParam(value = "chemotherapyInstitutionName", required = false) String chemotherapyInstitutionName,
                                     @RequestParam(value = "chemotherapyInstitutionCity", required = false) String chemotherapyInstitutionCity,
                                     @RequestParam(value = "chemotherapyInstitutionState", required = false) String chemotherapyInstitutionState, HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("UPDATE_CHEMOTHERAPY", servletRequest, "chemotherapyMeds:", chemotherapyMeds, "centralLine:", centralLine, "chemoStartDate:", chemoStartDate, "chemoEndDate:", chemoEndDate, "chemotherapyPcpName:", chemotherapyPcpName, "chemotherapyPcpEmail:", chemotherapyPcpEmail, "chemotherapyPcpPhone:", chemotherapyPcpPhone, "chemotherapyInstitutionName:", chemotherapyInstitutionName, "chemotherapyInstitutionCity:", chemotherapyInstitutionCity, "chemotherapyInstitutionState:", chemotherapyInstitutionState));
        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService = Context.getConceptService();
        String[] str_array = chemotherapyMeds.split("split");
        List<String> chemotherapyMedictionConcepts = new ArrayList<>();
        for (String s : str_array) {
            chemotherapyMedictionConcepts.add(s);
        }
        List<String> existingChemotherapyMedictionConcepts = new ArrayList<>();
        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("otherChemotherapyMedicationName");
        allTheEnteredValues.add("chemotherapyMeds");
        allTheEnteredValues.add("centralLine");
        allTheEnteredValues.add("chemoStartDate");
        allTheEnteredValues.add("chemoEndDate");
        allTheEnteredValues.add("chemotherapyPcpName");
        allTheEnteredValues.add("chemotherapyPcpEmail");
        allTheEnteredValues.add("chemotherapyPcpPhone");
        allTheEnteredValues.add("chemotherapyInstitutionName");
        allTheEnteredValues.add("chemotherapyInstitutionCity");
        allTheEnteredValues.add("chemotherapyInstitutionState");
        if (encounterId != null) {
            Encounter chemotherapyEncounter = encounterService.getEncounterByUuid(encounterId);
            Map<String, List<Obs>> observationConceptUUIDToObsMap = new HashMap<>();
            for (Obs o : chemotherapyEncounter.getObs()) {
                if (observationConceptUUIDToObsMap.get(o.getConcept().getUuid()) == null) {
                    List<Obs> newObsList = new ArrayList<>();
                    newObsList.add(o);
                    observationConceptUUIDToObsMap.put(o.getConcept().getUuid(), newObsList);
                } else {
                    List<Obs> existingObsList = observationConceptUUIDToObsMap.get(o.getConcept().getUuid());
                    existingObsList.add(o);
                    observationConceptUUIDToObsMap.put(o.getConcept().getUuid(), existingObsList);
                }
            }
            for (String entry : allTheEnteredValues) {
                if (entry != null) {
                    switch (entry) {
                        case "chemotherapyMeds":
                            for (Obs o : observationConceptUUIDToObsMap.get("8481b9da-74e3-45a9-9124-d69ab572d636"))
                                existingChemotherapyMedictionConcepts.add(o.getValueCoded().getUuid());
                            break;
                        case "centralLine":
                            if (observationConceptUUIDToObsMap.get("361b7f9b-a985-4b18-9055-03af3b41b8b3") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("361b7f9b-a985-4b18-9055-03af3b41b8b3").get(0);
                                o.setValueCoded(conceptService.getConceptByUuid(centralLine));
                            } else {
                                Obs o = new Obs();
                                o.setConcept(conceptService.getConceptByUuid("361b7f9b-a985-4b18-9055-03af3b41b8b3"));
                                o.setValueCoded(conceptService.getConceptByUuid(centralLine));
                                chemotherapyEncounter.addObs(o);
                            }
                            break;
                        case "chemoStartDate":
                            if (observationConceptUUIDToObsMap.get("85c3a99e-0598-4c63-912b-796dee9c75b2") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("85c3a99e-0598-4c63-912b-796dee9c75b2").get(0);
                                if (chemoStartDate != null && chemoStartDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(chemoStartDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (chemoStartDate != null && chemoStartDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("85c3a99e-0598-4c63-912b-796dee9c75b2"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(chemoStartDate);
                                    o.setValueDate(parsedDate);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemoEndDate":
                            if (observationConceptUUIDToObsMap.get("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315").get(0);
                                if (chemoEndDate != null && chemoEndDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(chemoEndDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (chemoEndDate != null && chemoEndDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(chemoEndDate);
                                    o.setValueDate(parsedDate);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyPcpName":
                            if (observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2").get(0);
                                if (o.getValueText() != chemotherapyPcpName)
                                    o.setValueText(chemotherapyPcpName);
                            } else {
                                if (chemotherapyPcpName != null && chemotherapyPcpName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                                    o.setValueText(chemotherapyPcpName);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyPcpEmail":
                            if (observationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864").get(0);
                                if (o.getValueText() != chemotherapyPcpEmail)
                                    o.setValueText(chemotherapyPcpEmail);
                            } else {
                                if (chemotherapyPcpEmail != null && chemotherapyPcpEmail != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                                    o.setValueText(chemotherapyPcpEmail);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyPcpPhone":
                            if (observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4").get(0);
                                if (o.getValueText() != chemotherapyPcpPhone)
                                    o.setValueText(chemotherapyPcpPhone);
                            } else {
                                if (chemotherapyPcpPhone != null && chemotherapyPcpPhone != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                                    o.setValueText(chemotherapyPcpPhone);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyInstitutionName":
                            if (observationConceptUUIDToObsMap.get("47d58999-d3b5-4869-a52e-841e2e6bdbb3") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("47d58999-d3b5-4869-a52e-841e2e6bdbb3").get(0);
                                if (o.getValueText() != chemotherapyInstitutionName)
                                    o.setValueText(chemotherapyInstitutionName);
                            } else {
                                if (chemotherapyInstitutionName != null && chemotherapyInstitutionName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("47d58999-d3b5-4869-a52e-841e2e6bdbb3"));
                                    o.setValueText(chemotherapyInstitutionName);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyInstitutionCity":
                            if (observationConceptUUIDToObsMap.get("bfa752d6-2037-465e-b0a2-c4c2d485ec32") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("bfa752d6-2037-465e-b0a2-c4c2d485ec32").get(0);
                                if (o.getValueText() != chemotherapyInstitutionCity)
                                    o.setValueText(chemotherapyInstitutionCity);
                            } else {
                                if (chemotherapyInstitutionCity != null && chemotherapyInstitutionCity != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("bfa752d6-2037-465e-b0a2-c4c2d485ec32"));
                                    o.setValueText(chemotherapyInstitutionCity);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "chemotherapyInstitutionState":
                            if (observationConceptUUIDToObsMap.get("34489100-487e-443a-bf27-1b6869fb9332") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("34489100-487e-443a-bf27-1b6869fb9332").get(0);
                                if (o.getValueText() != chemotherapyInstitutionState)
                                    o.setValueText(chemotherapyInstitutionState);
                            } else {
                                if (chemotherapyInstitutionState != null && chemotherapyInstitutionState != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("34489100-487e-443a-bf27-1b6869fb9332"));
                                    o.setValueText(chemotherapyInstitutionState);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "otherChemotherapyMedicationName":
                            if (observationConceptUUIDToObsMap.get("34489100-487e-443a-bf27-1b6869fb9332") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("3a49bfbb-df57-4c51-9f14-f05f848093e0").get(0);
                                if (o.getValueText() != otherChemotherapyMedicationName)
                                    o.setValueText(otherChemotherapyMedicationName);
                            } else {
                                if (chemotherapyInstitutionState != null && chemotherapyInstitutionState != "") {
                            Obs otherChemotherapyMedicationNameObs = new Obs();
                            otherChemotherapyMedicationNameObs.setConcept(conceptService.getConceptByUuid("3a49bfbb-df57-4c51-9f14-f05f848093e0"));
                            otherChemotherapyMedicationNameObs.setValueText(otherChemotherapyMedicationName);
                            chemotherapyEncounter.addObs(otherChemotherapyMedicationNameObs);
                                }
                            }
                            break;
                    }
                }
            }
            for (String s : existingChemotherapyMedictionConcepts) {
                if (chemotherapyMedictionConcepts.contains(s))
                    chemotherapyMedictionConcepts.remove(s);
                else {
                    for (Obs o : chemotherapyEncounter.getObs()) {
                        if (o.getConcept().getUuid().equals("8481b9da-74e3-45a9-9124-d69ab572d636")) {
                            if (o.getValueCoded().getUuid().equals(s))
                                o.setVoided(true);
                        }
                    }
                }

            }
            for (String s : chemotherapyMedictionConcepts) {
                Obs o = new Obs();
                o.setConcept(conceptService.getConceptByUuid("8481b9da-74e3-45a9-9124-d69ab572d636"));
                o.setValueCoded(conceptService.getConceptByUuid(s));
                chemotherapyEncounter.addObs(o);
            }

            encounterService.saveEncounter(chemotherapyEncounter);
            //log.info(PPTLogAppender.appendLog("Saved_Update_Chemotherapy", pageRequest.getRequest()));
            //log.info("Update Chemotherapy for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        }

    }
}
