/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;

import java.util.*;

/**
 * Created by Maurya on 01/07/2015.
 */
public class GenerateTreatmentClassesUtil {

    public static  List<GeneralHistory> generateGeneralHistory(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER);
        List<GeneralHistory> generalHistoryList=new ArrayList<GeneralHistory>();
        for(Encounter e: encounters){
            GeneralHistory generalHistory=new GeneralHistory();
            generalHistory.setEncounterUuid(e.getUuid());
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
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    generalHistory.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    generalHistory.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    generalHistory.setPcpPhone(o.getValueText());

            }
            generalHistoryList.add(generalHistory);
        }
        return generalHistoryList;
    }

    public static  List<Surgery> generateSurgeries(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.SURGERY_ENCOUNTER);
        List<Surgery> surgeriesList=new ArrayList<Surgery>();
        for(Encounter e: encounters){
            Surgery surgery=new Surgery();
            List<String> surgeryTypes= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            surgery.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("d409122c-8a0b-4282-a17f-07abad81f278"))
                    surgeryTypes.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("99ef1d68-05ed-4f37-b98b-c982e3574138")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        surgery.setHasMajorComplications(true);
                    else
                        surgery.setHasMajorComplications(false);
                }
                if(o.getConcept().getUuid().equals("c2d9fca3-1e0b-4007-8c3c-b3ebb4e67963"))
                    surgery.setMajorComplications(o.getValueText());
                if(o.getConcept().getUuid().equals("87a69397-65ef-4576-a709-ae0a526afd85"))
                    surgery.setSurgeryDate(o.getValueDate());
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    surgery.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    surgery.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    surgery.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    surgery.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    surgery.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    surgery.setInstitutionState(o.getValueText());
            }
            surgery.setSurgeryTypes(surgeryTypes);
            surgeriesList.add(surgery);
        }
        return surgeriesList;
    }

    public static  List<Chemotherapy> generateChemotherapies(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER);
        List<Chemotherapy> chemotherapiesList=new ArrayList<Chemotherapy>();
        for(Encounter e: encounters){
            Chemotherapy chemotherapy=new Chemotherapy();
            List<String> chemomedications= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            chemotherapy.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("8481b9da-74e3-45a9-9124-d69ab572d636"))
                    chemomedications.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("85c3a99e-0598-4c63-912b-796dee9c75b2"))
                    chemotherapy.setChemoStartDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"))
                    chemotherapy.setChemoEndDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("361b7f9b-a985-4b18-9055-03af3b41b8b3")){
                    if(o.getValueCoded().getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
                        chemotherapy.setCentralLine(true);
                    else
                        chemotherapy.setCentralLine(false);
                }
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    chemotherapy.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    chemotherapy.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    chemotherapy.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    chemotherapy.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    chemotherapy.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    chemotherapy.setInstitutionState(o.getValueText());
            }
            chemotherapy.setChemoMedications(chemomedications);
            chemotherapiesList.add(chemotherapy);
        }
        return chemotherapiesList;
    }

    public static  List<Radiation> generateRadiations(Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.RADIATION_ENCOUNTER);
        List<Radiation> radiationsList=new ArrayList<Radiation>();
        for(Encounter e: encounters){
            Radiation radiation=new Radiation();
            List<String> radiationTypes= new ArrayList<String>();
            Set<Obs> obsList= e.getObs();
            radiation.setEncounterUuid(e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("42fb7bb5-f840-4518-814c-893813211cba"))
                    radiationTypes.add(o.getValueCoded().getName().getName());
                if(o.getConcept().getUuid().equals("85c3a99e-0598-4c63-912b-796dee9c75b2"))
                    radiation.setStartDate(o.getValueDate());
                if(o.getConcept().getUuid().equals("7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315"))
                    radiation.setEndDate(o.getValueDate());
                //doctors name
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    radiation.setPcpName(o.getValueText());
                //doctors email
                if(o.getConcept().getUuid().equals("898a0028-8c65-4db9-a802-1577fce59864"))
                    radiation.setPcpEmail(o.getValueText());
                //doctors phone
                if(o.getConcept().getUuid().equals("9285b227-4054-4830-ac32-5ea78462e8c4"))
                    radiation.setPcpPhone(o.getValueText());
                if(o.getConcept().getUuid().equals("47d58999-d3b5-4869-a52e-841e2e6bdbb3"))
                    radiation.setInstitutionName(o.getValueText());
                if(o.getConcept().getUuid().equals("bfa752d6-2037-465e-b0a2-c4c2d485ec32"))
                    radiation.setInstitutionCity(o.getValueText());
                if(o.getConcept().getUuid().equals("34489100-487e-443a-bf27-1b6869fb9332"))
                    radiation.setInstitutionState(o.getValueText());
            }
            radiation.setRadiationTypes(radiationTypes);
            radiationsList.add(radiation);
        }
        return radiationsList;
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

    public static  Encounter generateLatestGeneralHistory(Patient patient){
        return getLatestEncounterByTreatment(patient, PatientPortalToolkitConstants.TREATMENTSUMMARY_ENCOUNTER);
    }

    public static Encounter getLatestEncounterByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        Encounter treatmentEncounter = null;
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                if(treatmentEncounter==null)
                treatmentEncounter=encounter;
                else if(treatmentEncounter.getDateCreated().before(encounter.getDateCreated()))
                    treatmentEncounter=encounter;
            }
        }
        return treatmentEncounter;
    }

    public static  List<Object> generateInfluenzaVaccines (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.INFLUENZA_VACCINE);
        List<Object> influenzaVaccineList=new ArrayList<>();
        for(Encounter e: encounters){
            Map<String, String> influenzaVaccineValues=new HashMap<>();
            Set<Obs> obsList= e.getObs();
            influenzaVaccineValues.put("uuid", e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("f1cba252-751f-470b-871b-2399565af396"))
                    influenzaVaccineValues.put("completedDate", o.getValueDate().toString());
                if(o.getConcept().getUuid().equals("13b3f829-50ca-48a0-b5fe-340fc96e1a2b"))
                    influenzaVaccineValues.put("completed", o.getValueBoolean().toString());
            }
            influenzaVaccineList.add(influenzaVaccineValues);
        }
        return influenzaVaccineList;
    }

    public static  List<Object> generatePneumococcalVaccines (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.PNEUMOCOCCAL_VACCINE);
        List<Object> pneumococcalVaccineList=new ArrayList<>();
        for(Encounter e: encounters){
            Map<String, String> pneumococcalVaccineValues=new HashMap<>();
            Set<Obs> obsList= e.getObs();
            pneumococcalVaccineValues.put("uuid", e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("c93df44f-d5b7-49a6-8539-e8265c03dbb3"))
                    pneumococcalVaccineValues.put("completedDate", o.getValueDate().toString());
                if(o.getConcept().getUuid().equals("3242085f-b88b-4852-afc0-f45911e1d4d7"))
                    pneumococcalVaccineValues.put("completed", o.getValueBoolean().toString());
            }
            pneumococcalVaccineList.add(pneumococcalVaccineValues);
        }
        return pneumococcalVaccineList;
    }

    public static  List<Object> generateBPScreenings (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.BP_SCREENING);
        List<Object> bPScreeningList=new ArrayList<>();
        for(Encounter e: encounters){
            Map<String, String> bPScreeningValues=new HashMap<>();
            Set<Obs> obsList= e.getObs();
            bPScreeningValues.put("uuid", e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("bec04eab-2be5-4f9e-a017-873e3a0b32ab"))
                    bPScreeningValues.put("completedDate", o.getValueDate().toString());
                if(o.getConcept().getUuid().equals("63ee5099-567e-4b55-936c-c4c8d71d1144"))
                    bPScreeningValues.put("bpTopNumber", o.getValueNumeric().toString());
                if(o.getConcept().getUuid().equals("02310664-f7bb-477c-a703-0325af4c3f46"))
                    bPScreeningValues.put("bpBottomNumber", o.getValueNumeric().toString());
            }
            bPScreeningList.add(bPScreeningValues);
        }
        return bPScreeningList;
    }

    public static  List<Object> generateCholesterolScreenings (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.CHOLESTEROL_SCREENING);
        List<Object> cholesterolScreeningList=new ArrayList<>();
        for(Encounter e: encounters) {
            Map<String, String> cholesterolScreeningValues = new HashMap<>();
            Set<Obs> obsList = e.getObs();
            cholesterolScreeningValues.put("uuid", e.getUuid());
            for (Obs o : obsList) {
                if (o.getConcept().getUuid().equals("01f5d7c7-f0c5-4329-8b2d-2053155a962f"))
                    cholesterolScreeningValues.put("completedDate", o.getValueDate().toString());
                if (o.getConcept().getUuid().equals("4788cb2c-6324-412f-b617-31ef341e7455"))
                    cholesterolScreeningValues.put("cholesterolTotalNumber", o.getValueNumeric().toString());
                if (o.getConcept().getUuid().equals("b0a44f7a-4188-44b3-b86f-955a32d8f4cd"))
                    cholesterolScreeningValues.put("cholesterolLDLNumber", o.getValueNumeric().toString());
            }
            cholesterolScreeningList.add(cholesterolScreeningList);
        }
        return cholesterolScreeningList;
    }

    public static  List<Object> generateHivScreenings (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.HIV_SCREENING);
        List<Object> hivScreeningList=new ArrayList<>();
        for(Encounter e: encounters){
            Map<String, String> hivScreeningValues=new HashMap<>();
            Set<Obs> obsList= e.getObs();
            hivScreeningValues.put("uuid", e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("695ccb4a-a01f-4039-9e00-8f2679b63065"))
                    hivScreeningValues.put("completedDate", o.getValueDate().toString());
                if(o.getConcept().getUuid().equals("785fd684-c6ca-48d7-9f71-07ae9b5e93d2"))
                    hivScreeningValues.put("hivResult", o.getValueBoolean().toString());
            }
            hivScreeningList.add(hivScreeningValues);
        }
        return hivScreeningList;
    }

    public static  List<Object> generateMammographyScreenings (Patient patient) {
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.MAMMOGRAPHY_SCREENING);
        List<Object> mammographyScreeningList = new ArrayList<>();
        for (Encounter e : encounters) {
            Map<String, String> mammographyScreeningValues = new HashMap<>();
            Set<Obs> obsList = e.getObs();
            mammographyScreeningValues.put("uuid", e.getUuid());
            for (Obs o : obsList) {
                if (o.getConcept().getUuid().equals("d32ef213-d270-4682-bf3a-b81d40b1d661"))
                    mammographyScreeningValues.put("completedDate", o.getValueDate().toString());
                if (o.getConcept().getUuid().equals("39ca0f60-ffe3-49cc-9dcf-7cce8f69c0f5"))
                    mammographyScreeningValues.put("mammographyResult", o.getValueBoolean().toString());
                if (o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    mammographyScreeningValues.put("mammographyDoctor", o.getValueText().toString());

            }
            mammographyScreeningList.add(mammographyScreeningValues);
        }
        return mammographyScreeningList;
    }

    public static  List<Object> generateCervicalCancerScreenings (Patient patient){
        List<Encounter> encounters = getEncountersByTreatment(patient, PatientPortalToolkitConstants.CERVICAL_CANCER_SCREENING);
        List<Object> cervicalCancerScreeningList=new ArrayList<>();
        for(Encounter e: encounters){
            Map<String, String> cervicalCancerScreeningValues=new HashMap<>();
            Set<Obs> obsList= e.getObs();
            cervicalCancerScreeningValues.put("uuid", e.getUuid());
            for(Obs o: obsList){
                if(o.getConcept().getUuid().equals("baf0de5b-17e7-47c5-a8f5-87d3df4966b4"))
                    cervicalCancerScreeningValues.put("completedDate", o.getValueDate().toString());
                if(o.getConcept().getUuid().equals("838800a3-9991-4fd8-9df1-d6c4f9c2ffae"))
                    cervicalCancerScreeningValues.put("cervicalCancerResult", o.getValueBoolean().toString());
                if(o.getConcept().getUuid().equals("c2cb2220-c07d-47c6-a4df-e5918aac3fc2"))
                    cervicalCancerScreeningValues.put("cervicalCancerDoctor", o.getValueText().toString());

            }
            cervicalCancerScreeningList.add(cervicalCancerScreeningValues);
        }
        return cervicalCancerScreeningList;
    }
}
