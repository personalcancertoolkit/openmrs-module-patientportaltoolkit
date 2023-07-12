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
 * Created by Maurya on 16/07/2015.
 */
public class TreatmentsGenHistoryModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_GENERALHISTORY_FRAGMENT", pageRequest.getRequest()));
        PatientPortalFormService patientPortalFormService = Context.getService(PatientPortalFormService.class);
        model.addAttribute("genHistoryConcepts", patientPortalFormService
                .getPatientPortalFormByFormType(PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER));
    }

    public void saveNewGenHistoryForm(FragmentModel model, @RequestParam(value = "patientUuid") String patientUuid,
            @RequestParam(value = "cancerType", required = false) String cancerType,
            @RequestParam(value = "cancerStage", required = false) String cancerStage,
            @RequestParam(value = "cancerDate", required = false) String cancerDate,
            @RequestParam(value = "genHistoryCancerPcpName", required = false) String genHistoryCancerPcpName,
            @RequestParam(value = "genHistoryCancerPcpPhone", required = false) String genHistoryCancerPcpPhone,
            HttpServletRequest servletRequest) throws ParseException {

        EncounterService encounterService = Context.getEncounterService();
        Encounter newGenHistoryEncounter = new Encounter();
        if (patientUuid == null || patientUuid.isEmpty()) {
            newGenHistoryEncounter.setPatient(
                    Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        } else {
            newGenHistoryEncounter.setPatient(Context.getPatientService().getPatientByUuid(patientUuid));
        }
        Date date = new Date();
        newGenHistoryEncounter.setDateCreated(new Date());
        newGenHistoryEncounter.setEncounterDatetime(date);
        newGenHistoryEncounter.setEncounterType(
                encounterService.getEncounterType(PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER));
        ConceptService conceptService = Context.getConceptService();
        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("cancerType");
        allTheEnteredValues.add("radiationEndDate");
        allTheEnteredValues.add("cancerStage");
        allTheEnteredValues.add("cancerDate");
        allTheEnteredValues.add("genHistoryCancerPcpName");
        allTheEnteredValues.add("genHistoryCancerPcpPhone");

        for (String entry : allTheEnteredValues) {
            if (entry != null) {
                switch (entry) {
                    case "cancerType":
                        Obs cancerTypeObs = new Obs();
                        cancerTypeObs
                                .setConcept(conceptService.getConceptByUuid("cdf6d767-2aa3-40b6-ae78-0386eebe2411"));
                        cancerTypeObs.setValueCoded(conceptService.getConceptByUuid(cancerType));
                        newGenHistoryEncounter.addObs(cancerTypeObs);
                        break;
                    case "cancerStage":
                        Obs cancerStageObs = new Obs();
                        cancerStageObs
                                .setConcept(conceptService.getConceptByUuid("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d"));
                        cancerStageObs.setValueCoded(conceptService.getConceptByUuid(cancerStage));
                        newGenHistoryEncounter.addObs(cancerStageObs);
                        break;
                    case "cancerDate":
                        if (cancerDate != null && cancerDate != "") {
                            Obs cancerDateObs = new Obs();
                            cancerDateObs.setConcept(
                                    conceptService.getConceptByUuid("654e32f0-8b57-4d1f-845e-500922e800f6"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(cancerDate);
                            cancerDateObs.setValueDate(parsedDate);
                            newGenHistoryEncounter.addObs(cancerDateObs);
                        }
                        break;
                    case "genHistoryCancerPcpName":
                        if (genHistoryCancerPcpName != null && genHistoryCancerPcpName != "") {
                            Obs genHistoryCancerPcpNameObs = new Obs();
                            genHistoryCancerPcpNameObs.setConcept(
                                    conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                            genHistoryCancerPcpNameObs.setValueText(genHistoryCancerPcpName);
                            newGenHistoryEncounter.addObs(genHistoryCancerPcpNameObs);
                        }
                        break;

                    case "genHistoryCancerPcpPhone":

                        if (genHistoryCancerPcpPhone != null && genHistoryCancerPcpPhone != "") {
                            Obs genHistoryCancerPcpPhoneObs = new Obs();
                            genHistoryCancerPcpPhoneObs.setConcept(
                                    conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                            genHistoryCancerPcpPhoneObs.setValueText(genHistoryCancerPcpPhone);
                            newGenHistoryEncounter.addObs(genHistoryCancerPcpPhoneObs);
                        }
                        break;
                }
            }
        }
        System.out.println(newGenHistoryEncounter.getPatient());
        encounterService.saveEncounter(newGenHistoryEncounter);
    }

    public void saveGenHistoryForm(FragmentModel model,
            @RequestParam(value = "encounterId", required = true) String encounterId,
            @RequestParam(value = "cancerType", required = false) String cancerType,
            @RequestParam(value = "cancerStage", required = false) String cancerStage,
            @RequestParam(value = "cancerDate", required = false) String cancerDate,
            @RequestParam(value = "genHistoryCancerPcpName", required = false) String genHistoryCancerPcpName,
            @RequestParam(value = "genHistoryCancerPcpPhone", required = false) String genHistoryCancerPcpPhone,
            HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("SAVE_GENERALHISTORY", servletRequest, "encounterId:", encounterId,
                "cancerType:", cancerType, "cancerStage:", cancerStage, "cancerDate:", cancerDate,
                "genHistoryCancerPcpName:", genHistoryCancerPcpName, "genHistoryCancerPcpPhone:",
                genHistoryCancerPcpPhone));
        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService = Context.getConceptService();
        Encounter genHistoryEncounter = encounterService.getEncounterByUuid(encounterId);
        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("cancerType");
        allTheEnteredValues.add("cancerStage");
        allTheEnteredValues.add("cancerDate");
        allTheEnteredValues.add("genHistoryCancerPcpName");
        allTheEnteredValues.add("genHistoryCancerPcpPhone");
        if (encounterId != null) {
            Encounter generalEncounter = encounterService.getEncounterByUuid(encounterId);
            Map<String, List<Obs>> observationConceptUUIDToObsMap = new HashMap<>();
            for (Obs o : generalEncounter.getObs()) {
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
                        case "cancerType":
                            if (observationConceptUUIDToObsMap.get("cdf6d767-2aa3-40b6-ae78-0386eebe2411") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("cdf6d767-2aa3-40b6-ae78-0386eebe2411")
                                        .get(0);
                                o.setValueCoded(conceptService.getConceptByUuid(cancerType));
                            } else {
                                Obs o = new Obs();
                                o.setConcept(conceptService.getConceptByUuid("cdf6d767-2aa3-40b6-ae78-0386eebe2411"));
                                o.setValueCoded(conceptService.getConceptByUuid(cancerType));
                                generalEncounter.addObs(o);
                            }
                            break;
                        case "cancerStage":
                            if (observationConceptUUIDToObsMap.get("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d")
                                        .get(0);
                                o.setValueCoded(conceptService.getConceptByUuid(cancerStage));
                            } else {
                                Obs o = new Obs();
                                o.setConcept(conceptService.getConceptByUuid("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d"));
                                o.setValueCoded(conceptService.getConceptByUuid(cancerStage));
                                generalEncounter.addObs(o);
                            }
                            break;
                        case "cancerDate":
                            if (observationConceptUUIDToObsMap.get("654e32f0-8b57-4d1f-845e-500922e800f6") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("654e32f0-8b57-4d1f-845e-500922e800f6")
                                        .get(0);
                                if (cancerDate != null && cancerDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(cancerDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (cancerDate != null && cancerDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(
                                            conceptService.getConceptByUuid("654e32f0-8b57-4d1f-845e-500922e800f6"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(cancerDate);
                                    o.setValueDate(parsedDate);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                        case "genHistoryCancerPcpName":
                            if (observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2")
                                        .get(0);
                                if (o.getValueText() != genHistoryCancerPcpName)
                                    o.setValueText(genHistoryCancerPcpName);
                            } else {
                                if (genHistoryCancerPcpName != null && genHistoryCancerPcpName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(
                                            conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                                    o.setValueText(genHistoryCancerPcpName);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;

                        case "genHistoryCancerPcpPhone":
                            if (observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4")
                                        .get(0);
                                if (o.getValueText() != genHistoryCancerPcpPhone)
                                    o.setValueText(genHistoryCancerPcpPhone);
                            } else {
                                if (genHistoryCancerPcpPhone != null && genHistoryCancerPcpPhone != "") {
                                    Obs o = new Obs();
                                    o.setConcept(
                                            conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                                    o.setValueText(genHistoryCancerPcpPhone);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                    }
                }
            }
        }
        encounterService.saveEncounter(genHistoryEncounter);
    }
}
