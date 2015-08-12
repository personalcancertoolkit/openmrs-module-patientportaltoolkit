package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 29/07/2015.
 */
public class SurgeriesModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model) {
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
                         @RequestParam(value = "surgeryInstitutionState", required = false) String surgeryInstitutionState) throws ParseException {

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
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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
            }

        }
}
