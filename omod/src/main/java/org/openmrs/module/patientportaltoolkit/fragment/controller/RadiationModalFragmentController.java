package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maurya on 18/08/2015.
 */
public class RadiationModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model){}

    public void saveNewRadiationForm(FragmentModel model, @RequestParam(value = "radiationTypes", required = false) String radiationTypes,
                                        @RequestParam(value = "radiationStartDate", required = false) String radiationStartDate,
                                        @RequestParam(value = "radiationEndDate", required = false) String radiationEndDate,
                                        @RequestParam(value = "radiationPcpName", required = false) String radiationPcpName,
                                        @RequestParam(value = "radiationPcpEmail", required = false) String radiationPcpEmail,
                                        @RequestParam(value = "radiationPcpPhone", required = false) String radiationPcpPhone,
                                        @RequestParam(value = "radiationInstitutionName", required = false) String radiationInstitutionName,
                                        @RequestParam(value = "radiationInstitutionCity", required = false) String radiationInstitutionCity,
                                        @RequestParam(value = "radiationInstitutionState", required = false) String radiationInstitutionState) throws ParseException {

        EncounterService encounterService= Context.getEncounterService();
        Encounter newRadiationEncounter = new Encounter();
        newRadiationEncounter.setPatient(Context.getPatientService().getPatient(Context.getAuthenticatedUser().getPerson().getId()));
        Date date = new Date();
        newRadiationEncounter.setDateCreated(new Date());
        newRadiationEncounter.setEncounterDatetime(date);
        newRadiationEncounter.setEncounterType(encounterService.getEncounterType(PatientPortalToolkitConstants.RADIATION_ENCOUNTER));
        ConceptService conceptService=Context.getConceptService();
        String[] str_array = radiationTypes.split("split");
        List<String> radiationTypesConcepts = new ArrayList<>();
        for(String s: str_array){
            radiationTypesConcepts.add(s);
        }

        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("radiationStartDate");
        allTheEnteredValues.add("radiationEndDate");
        allTheEnteredValues.add("radiationPcpName");
        allTheEnteredValues.add("radiationPcpEmail");
        allTheEnteredValues.add("radiationPcpPhone");
        allTheEnteredValues.add("radiationInstitutionName");
        allTheEnteredValues.add("radiationInstitutionCity");
        allTheEnteredValues.add("radiationInstitutionState");
        for (String s : radiationTypesConcepts){
            Obs o = new Obs();
            o.setConcept(conceptService.getConceptByUuid("42fb7bb5-f840-4518-814c-893813211cba"));
            o.setValueCoded(conceptService.getConceptByUuid(s));
            newRadiationEncounter.addObs(o);
        }
        Obs radiationInstitution = new Obs();
        radiationInstitution.setConcept(conceptService.getConceptByUuid("329328ab-8e1c-461e-9261-fd4471b1f131"));

        Obs raidationSpecialist = new Obs();
        raidationSpecialist.setConcept(conceptService.getConceptByUuid("f031cc84-5eb1-4d64-beb5-c3c6bd9b915c"));

        for (String entry : allTheEnteredValues)
        {
            if(entry !=null) {
                switch (entry) {
                    case "radiationStartDate":
                        if (radiationStartDate != null && radiationStartDate != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("85c3a99e-0598-4c63-912b-796dee9c75b2"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(radiationStartDate);
                            o.setValueDate(parsedDate);
                            newRadiationEncounter.addObs(o);
                        }
                        break;
                    case "radiationEndDate":
                        if (radiationEndDate != null && radiationEndDate != "") {
                            Obs o = new Obs();
                            o.setConcept(conceptService.getConceptByUuid("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"));
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            Date parsedDate = formatter.parse(radiationEndDate);
                            o.setValueDate(parsedDate);
                            newRadiationEncounter.addObs(o);
                        }
                        break;
                    case "radiationPcpName":
                        Obs surgeonPcpNameObs = new Obs();
                        surgeonPcpNameObs.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                        surgeonPcpNameObs.setValueText(radiationPcpName);
                        raidationSpecialist.addGroupMember(surgeonPcpNameObs);
                        break;
                    case "radiationPcpEmail":
                        Obs surgeonPcpEmailObs = new Obs();
                        surgeonPcpEmailObs.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                        surgeonPcpEmailObs.setValueText(radiationPcpEmail);
                        raidationSpecialist.addGroupMember(surgeonPcpEmailObs);
                        break;
                    case "radiationPcpPhone":
                        Obs surgeonPcpPhoneObs = new Obs();
                        surgeonPcpPhoneObs.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                        surgeonPcpPhoneObs.setValueText(radiationPcpPhone);
                        raidationSpecialist.addGroupMember(surgeonPcpPhoneObs);
                        break;
                    case "radiationInstitutionName":
                        Obs surgeryInstitutionNameObs = new Obs();
                        surgeryInstitutionNameObs.setConcept(conceptService.getConceptByUuid("47d58999-d3b5-4869-a52e-841e2e6bdbb3"));
                        surgeryInstitutionNameObs.setValueText(radiationInstitutionName);
                        radiationInstitution.addGroupMember(surgeryInstitutionNameObs);
                        break;
                    case "radiationInstitutionCity":
                        Obs surgeryInstitutionCityObs = new Obs();
                        surgeryInstitutionCityObs.setConcept(conceptService.getConceptByUuid("bfa752d6-2037-465e-b0a2-c4c2d485ec32"));
                        surgeryInstitutionCityObs.setValueText(radiationInstitutionCity);
                        radiationInstitution.addGroupMember(surgeryInstitutionCityObs);
                        break;
                    case "radiationInstitutionState":
                        Obs surgeryInstitutionStateobs = new Obs();
                        surgeryInstitutionStateobs.setConcept(conceptService.getConceptByUuid("34489100-487e-443a-bf27-1b6869fb9332"));
                        surgeryInstitutionStateobs.setValueText(radiationInstitutionState);
                        radiationInstitution.addGroupMember(surgeryInstitutionStateobs);
                        break;
                }
            }
        }
        newRadiationEncounter.addObs(radiationInstitution);
        newRadiationEncounter.addObs(raidationSpecialist);
        encounterService.saveEncounter(newRadiationEncounter);
    }

    public void saveRadiationForm(FragmentModel model,  @RequestParam(value = "encounterId", required = false) String encounterId,
                                     @RequestParam(value = "radiationTypes", required = false) String radiationTypes,
                                     @RequestParam(value = "radiationStartDate", required = false) String radiationStartDate,
                                     @RequestParam(value = "radiationEndDate", required = false) String radiationEndDate,
                                     @RequestParam(value = "radiationPcpName", required = false) String radiationPcpName,
                                     @RequestParam(value = "radiationPcpEmail", required = false) String radiationPcpEmail,
                                     @RequestParam(value = "radiationPcpPhone", required = false) String radiationPcpPhone,
                                     @RequestParam(value = "radiationInstitutionName", required = false) String radiationInstitutionName,
                                  @RequestParam(value = "radiationInstitutionCity", required = false) String radiationInstitutionCity,
                                     @RequestParam(value = "radiationInstitutionState", required = false) String radiationInstitutionState) throws ParseException {

        EncounterService encounterService= Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        String[] str_array = radiationTypes.split("split");
        List<String> radiationTypesConcepts = new ArrayList<>();
        for(String s: str_array){
            radiationTypesConcepts.add(s);
        }
        List<String> existingRadiationTypesConcepts = new ArrayList<>();
        List<String> allTheEnteredValues = new ArrayList<>();
        allTheEnteredValues.add("radiationTypes");
        allTheEnteredValues.add("radiationStartDate");
        allTheEnteredValues.add("radiationEndDate");
        allTheEnteredValues.add("radiationPcpName");
        allTheEnteredValues.add("radiationPcpEmail");
        allTheEnteredValues.add("radiationPcpPhone");
        allTheEnteredValues.add("radiationInstitutionName");
        allTheEnteredValues.add("radiationInstitutionCity");
        allTheEnteredValues.add("radiationInstitutionState");
        if(encounterId !=null) {
            Encounter chemotherapyEncounter = encounterService.getEncounterByUuid(encounterId);
            Map<String,List<Obs>> radiationObservationConceptUUIDToObsMap = new HashMap<>();
            for (Obs o:chemotherapyEncounter.getObs()){
                if(radiationObservationConceptUUIDToObsMap.get(o.getConcept().getUuid())== null) {
                    List<Obs> newObsList=new ArrayList<>();
                    newObsList.add(o);
                    radiationObservationConceptUUIDToObsMap.put(o.getConcept().getUuid(),newObsList);
                }
                else
                {
                    List<Obs> existingObsList= radiationObservationConceptUUIDToObsMap.get(o.getConcept().getUuid());
                    existingObsList.add(o);
                    radiationObservationConceptUUIDToObsMap.put(o.getConcept().getUuid(),existingObsList);
                }
            }
            for (String entry : allTheEnteredValues)
            {
                if(entry !=null) {
                    switch (entry) {
                        case "radiationTypes":
                            for (Obs o : radiationObservationConceptUUIDToObsMap.get("42fb7bb5-f840-4518-814c-893813211cba"))
                                existingRadiationTypesConcepts.add(o.getValueCoded().getUuid());
                            break;
                        case "radiationStartDate":
                            if (radiationObservationConceptUUIDToObsMap.get("85c3a99e-0598-4c63-912b-796dee9c75b2") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("85c3a99e-0598-4c63-912b-796dee9c75b2").get(0);
                                if (radiationStartDate != null && radiationStartDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(radiationStartDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (radiationStartDate != null && radiationStartDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("85c3a99e-0598-4c63-912b-796dee9c75b2"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(radiationStartDate);
                                    o.setValueDate(parsedDate);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationEndDate":
                            if (radiationObservationConceptUUIDToObsMap.get("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315").get(0);
                                if (radiationEndDate != null && radiationEndDate != "") {
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(radiationEndDate);
                                    if (o.getValueDate() != parsedDate)
                                        o.setValueDate(parsedDate);
                                }
                            } else {
                                if (radiationEndDate != null && radiationEndDate != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                    Date parsedDate = formatter.parse(radiationEndDate);
                                    o.setValueDate(parsedDate);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationPcpName":
                            if (radiationObservationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("c2cb2220-c07d-47c6-a4df-e5918aac3fc2").get(0);
                                if (!o.getValueText().equals(radiationPcpName))
                                    o.setValueText(radiationPcpName);
                            } else {
                                if (radiationPcpName != null && radiationPcpName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"));
                                    o.setValueText(radiationPcpName);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationPcpEmail":
                            if (radiationObservationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("898a0028-8c65-4db9-a802-1577fce59864").get(0);
                                if (!o.getValueText().equals(radiationPcpEmail))
                                    o.setValueText(radiationPcpEmail);
                            } else {
                                if (radiationPcpEmail != null && radiationPcpEmail != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("898a0028-8c65-4db9-a802-1577fce59864"));
                                    o.setValueText(radiationPcpEmail);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationPcpPhone":
                            if (radiationObservationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("9285b227-4054-4830-ac32-5ea78462e8c4").get(0);
                                if (!o.getValueText().equals(radiationPcpPhone))
                                    o.setValueText(radiationPcpPhone);
                            } else {
                                if (radiationPcpPhone != null && radiationPcpPhone != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("9285b227-4054-4830-ac32-5ea78462e8c4"));
                                    o.setValueText(radiationPcpPhone);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationInstitutionName":
                            if (radiationObservationConceptUUIDToObsMap.get("47d58999-d3b5-4869-a52e-841e2e6bdbb3") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("47d58999-d3b5-4869-a52e-841e2e6bdbb3").get(0);
                                if (!o.getValueText().equals(radiationInstitutionName))
                                    o.setValueText(radiationInstitutionName);
                            } else {
                                if (radiationInstitutionName != null && radiationInstitutionName != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("47d58999-d3b5-4869-a52e-841e2e6bdbb3"));
                                    o.setValueText(radiationInstitutionName);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationInstitutionCity":
                            if (radiationObservationConceptUUIDToObsMap.get("bfa752d6-2037-465e-b0a2-c4c2d485ec32") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("bfa752d6-2037-465e-b0a2-c4c2d485ec32").get(0);
                                if (!o.getValueText().equals(radiationInstitutionCity))
                                    o.setValueText(radiationInstitutionCity);
                            } else {
                                if (radiationInstitutionCity != null && radiationInstitutionCity != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("bfa752d6-2037-465e-b0a2-c4c2d485ec32"));
                                    o.setValueText(radiationInstitutionCity);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                        case "radiationInstitutionState":
                            if (radiationObservationConceptUUIDToObsMap.get("34489100-487e-443a-bf27-1b6869fb9332") != null) {
                                Obs o = radiationObservationConceptUUIDToObsMap.get("34489100-487e-443a-bf27-1b6869fb9332").get(0);
                                if (!o.getValueText().equals(radiationInstitutionState))
                                    o.setValueText(radiationInstitutionState);
                            } else {
                                if (radiationInstitutionState != null && radiationInstitutionState != "") {
                                    Obs o = new Obs();
                                    o.setConcept(conceptService.getConceptByUuid("34489100-487e-443a-bf27-1b6869fb9332"));
                                    o.setValueText(radiationInstitutionState);
                                    chemotherapyEncounter.addObs(o);
                                }
                            }
                            break;
                    }
                }
            }
            for(String s: existingRadiationTypesConcepts){
                if (radiationTypesConcepts.contains(s))
                    radiationTypesConcepts.remove(s);
                else {
                    for (Obs o : chemotherapyEncounter.getObs()) {
                        if (o.getConcept().getUuid().equals("42fb7bb5-f840-4518-814c-893813211cba")) {
                            if (o.getValueCoded().getUuid().equals(s))
                                o.setVoided(true);
                        }
                    }
                }

            }
            for (String s : radiationTypesConcepts){
                Obs o = new Obs();
                o.setConcept(conceptService.getConceptByUuid("42fb7bb5-f840-4518-814c-893813211cba"));
                o.setValueCoded(conceptService.getConceptByUuid(s));
                chemotherapyEncounter.addObs(o);
            }

            encounterService.saveEncounter(chemotherapyEncounter);
        }

    }
}
