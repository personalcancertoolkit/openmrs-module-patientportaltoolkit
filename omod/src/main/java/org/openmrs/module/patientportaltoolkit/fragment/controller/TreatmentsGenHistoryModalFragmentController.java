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

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_GENERALHISTORY_FRAGMENT", pageRequest.getRequest()));
    }

    public void saveGenHistoryForm(FragmentModel model,  @RequestParam(value = "encounterId", required = true) String encounterId,
                                   @RequestParam(value = "cancerType", required = true) String cancerType,
                                   @RequestParam(value = "cancerStage", required = true) String cancerStage,
                                   @RequestParam(value = "cancerDate", required = true) String cancerDate,
                                   @RequestParam(value = "cancerAbnormalityBool", required = true) String cancerAbnormalityBool,
                                   @RequestParam(value = "cancerAbnormalityType", required = false) String cancerAbnormalityType,
                                   @RequestParam(value = "genHistoryCancerPcpName", required = true) String genHistoryCancerPcpName,
                                   @RequestParam(value = "genHistoryCancerPcpEmail", required = true) String genHistoryCancerPcpEmail,
                                   @RequestParam(value = "genHistoryCancerPcpPhone", required = true) String genHistoryCancerPcpPhone, HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("SAVE_GENERALHISTORY", servletRequest, "encounterId:", encounterId, "cancerType:", cancerType, "cancerStage:", cancerStage, "cancerDate:", cancerDate, "cancerAbnormalityBool:", cancerAbnormalityBool, "cancerAbnormalityType:", cancerAbnormalityType, "genHistoryCancerPcpName:", genHistoryCancerPcpName, "genHistoryCancerPcpEmail:", genHistoryCancerPcpEmail, "genHistoryCancerPcpPhone:", genHistoryCancerPcpPhone));
        EncounterService encounterService=Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter genHistoryEncounter = encounterService.getEncounterByUuid(encounterId);
        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("cancerType");
        allTheEnteredValues.add("cancerStage");
        allTheEnteredValues.add("cancerDate");
        allTheEnteredValues.add("cancerAbnormalityBool");
        allTheEnteredValues.add("cancerAbnormalityType");
        allTheEnteredValues.add("genHistoryCancerPcpName");
        allTheEnteredValues.add("genHistoryCancerPcpEmail");
        allTheEnteredValues.add("genHistoryCancerPcpPhone");
        if(encounterId !=null) {
            Encounter generalEncounter = encounterService.getEncounterByUuid(encounterId);
            Map<String,List<Obs>> observationConceptUUIDToObsMap = new HashMap<>();
            for (Obs o:generalEncounter.getObs()){
                if(observationConceptUUIDToObsMap.get(o.getConcept().getUuid())== null) {
                    List<Obs> newObsList=new ArrayList<>();
                    newObsList.add(o);
                    observationConceptUUIDToObsMap.put(o.getConcept().getUuid(),newObsList);
                }
                else
                {
                    List<Obs> existingObsList= observationConceptUUIDToObsMap.get(o.getConcept().getUuid());
                    existingObsList.add(o);
                    observationConceptUUIDToObsMap.put(o.getConcept().getUuid(),existingObsList);
                }
            }
            for (String entry : allTheEnteredValues)
            {
                if(entry !=null) {
                    switch (entry) {
                        case "cancerType":
                            if (observationConceptUUIDToObsMap.get("cdf6d767-2aa3-40b6-ae78-0386eebe2411") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("cdf6d767-2aa3-40b6-ae78-0386eebe2411").get(0);
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
                                Obs o = observationConceptUUIDToObsMap.get("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d").get(0);
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
                                Obs o = observationConceptUUIDToObsMap.get("654e32f0-8b57-4d1f-845e-500922e800f6").get(0);
                                if (cancerDate != null && cancerDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate =formatter.parse(cancerDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (cancerDate != null && cancerDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("654e32f0-8b57-4d1f-845e-500922e800f6"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(cancerDate);
                                    o.setValueDate(parsedDate);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                        case "cancerAbnormalityBool":
                            if (observationConceptUUIDToObsMap.get("395878ae-5108-4aad-8ad8-9b88e812d278") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("395878ae-5108-4aad-8ad8-9b88e812d278").get(0);
                                o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityBool));
                            } else {
                                Obs o = new Obs();
                                o.setConcept(conceptService.getConceptByUuid("395878ae-5108-4aad-8ad8-9b88e812d278"));
                                o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityBool));
                                generalEncounter.addObs(o);
                            }
                            break;
                        case "cancerAbnormalityType":
                            if (observationConceptUUIDToObsMap.get("8719adbe-0975-477f-a95f-2fae4d6cbdae") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("8719adbe-0975-477f-a95f-2fae4d6cbdae").get(0);
                                o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityType));
                            } else {
                                if(!cancerAbnormalityType.isEmpty()) {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("8719adbe-0975-477f-a95f-2fae4d6cbdae"));
                                    o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityType));
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                        case "genHistoryCancerPcpName":
                            if (observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2").get(0);
                                if (o.getValueText() != genHistoryCancerPcpName)
                                    o.setValueText(genHistoryCancerPcpName);
                            } else {
                                if (genHistoryCancerPcpName != null && genHistoryCancerPcpName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                                    o.setValueText(genHistoryCancerPcpName);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                        case "genHistoryCancerPcpEmail":
                            if (observationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864").get(0);
                                if (o.getValueText() != genHistoryCancerPcpEmail)
                                    o.setValueText(genHistoryCancerPcpEmail);
                            } else {
                                if (genHistoryCancerPcpEmail != null && genHistoryCancerPcpEmail != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                                    o.setValueText(genHistoryCancerPcpEmail);
                                    generalEncounter.addObs(o);
                                }
                            }
                            break;
                        case "genHistoryCancerPcpPhone":
                            if (observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4") != null) {
                                Obs o = observationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4").get(0);
                                if (o.getValueText() != genHistoryCancerPcpPhone)
                                    o.setValueText(genHistoryCancerPcpPhone);
                            } else {
                                if (genHistoryCancerPcpPhone != null && genHistoryCancerPcpPhone != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
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
       // log.info("Save General History for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
