package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.GeneralHistory;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 01/07/2015.
 */
public class GenerateTreatmentClassesUtil {

    public static  List<GeneralHistory> generateGeneralHistory(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER);
        List<GeneralHistory> generalHistoryList=new ArrayList<GeneralHistory>();
        for(Encounter e: encounters){
            GeneralHistory generalHistory=new GeneralHistory();
            Set<Obs> obsList= e.getObs();
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("efa3f9eb-ade4-4ddb-92c9-0fc1119d112d"))
                    generalHistory.setCancerStage(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("cdf6d767-2aa3-40b6-ae78-0386eebe2411"))
                    generalHistory.setCancerType(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("395878ae-5108-4aad-8ad8-9b88e812d278")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        generalHistory.setHasGeneticOrPredisposingAbnormality(true);
                    else
                        generalHistory.setHasGeneticOrPredisposingAbnormality(false);
                }
                if(o.getConcept().getUuid().equals("8719adbe-0975-477f-a95f-2fae4d6cbdae"))
                    generalHistory.setGeneticOrPredisposingAbnormality(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("654e32f0-8b57-4d1f-845e-500922e800f6"))
                    generalHistory.setDiagnosisDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    generalHistory.setPcpName(o.getValueText());
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    generalHistory.setPcpEmail(o.getValueText());
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    generalHistory.setPcpPhone(o.getValueText());

            }
            generalHistoryList.add(generalHistory);
        }
        return generalHistoryList;
    }

    public static List<Encounter> getEncountersByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        List<Encounter> treatmentEncounters = new ArrayList<Encounter>();
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                treatmentEncounters.add(encounter);
            }
        }
        return treatmentEncounters;
    }
}
