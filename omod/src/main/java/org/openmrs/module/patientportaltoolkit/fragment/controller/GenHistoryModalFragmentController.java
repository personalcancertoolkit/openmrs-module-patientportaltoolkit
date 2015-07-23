package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maurya on 16/07/2015.
 */
public class GenHistoryModalFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void saveGenHistoryForm(FragmentModel model,  @RequestParam(value = "encounterId", required = true) String encounterId,
                                   @RequestParam(value = "cancerType", required = true) String cancerType,
                                   @RequestParam(value = "cancerStage", required = true) String cancerStage,
                                   @RequestParam(value = "cancerDate", required = true) String cancerDate,
                                   @RequestParam(value = "cancerAbnormalityBool", required = true) String cancerAbnormalityBool,
                                   @RequestParam(value = "cancerAbnormalityType", required = true) String cancerAbnormalityType,
                                   @RequestParam(value = "genHistoryCancerPcpName", required = true) String genHistoryCancerPcpName,
                                   @RequestParam(value = "genHistoryCancerPcpEmail", required = true) String genHistoryCancerPcpEmail,
                                   @RequestParam(value = "genHistoryCancerPcpPhone", required = true) String genHistoryCancerPcpPhone) throws ParseException {

        EncounterService encounterService=Context.getEncounterService();
        ConceptService conceptService=Context.getConceptService();
        Encounter genHistoryEncounter = encounterService.getEncounterByUuid(encounterId);
        for (Obs o:genHistoryEncounter.getObs()){
            switch (o.getConcept().getUuid()){
                case "cdf6d767-2aa3-40b6-ae78-0386eebe2411":
                    if(o.getValueCoded().getUuid()!=cancerType)
                        o.setValueCoded(conceptService.getConceptByUuid(cancerType));
                    break;
                case "efa3f9eb-ade4-4ddb-92c9-0fc1119d112d":
                    if(o.getValueCoded().getUuid()!=cancerStage)
                        o.setValueCoded(conceptService.getConceptByUuid(cancerStage));
                    break;
                case "654e32f0-8b57-4d1f-845e-500922e800f6":
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date parsedDate = formatter.parse(cancerDate);
                    if(o.getValueDate()!=parsedDate)
                        o.setValueDate(parsedDate);
                    break;
                case "395878ae-5108-4aad-8ad8-9b88e812d278":
                    if(o.getValueCoded().getUuid()!=cancerAbnormalityBool)
                        o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityBool));
                    break;
                case "8719adbe-0975-477f-a95f-2fae4d6cbdae":
                    if(o.getValueCoded().getUuid()!=cancerAbnormalityType)
                        o.setValueCoded(conceptService.getConceptByUuid(cancerAbnormalityType));
                    break;
                case "c2cb2220-c07d-47c6-a4df-e5918aac3fc2":
                    if(o.getValueText()!=genHistoryCancerPcpName)
                        o.setValueText(genHistoryCancerPcpName);
                    break;
                case "898a0028-8c65-4db9-a802-1577fce59864":
                    if(o.getValueText()!=genHistoryCancerPcpEmail)
                        o.setValueText(genHistoryCancerPcpEmail);
                    break;
                case "9285b227-4054-4830-ac32-5ea78462e8c4":
                    if(o.getValueText()!=genHistoryCancerPcpPhone)
                        o.setValueText(genHistoryCancerPcpPhone);
                    break;
            }
        }
        encounterService.saveEncounter(genHistoryEncounter);
    }
}
