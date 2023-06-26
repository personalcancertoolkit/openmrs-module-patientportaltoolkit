/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.SideEffect;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.db.SideEffectDAO;
import java.util.*;

/**
 * Created by Maurya on 02/06/2015.
 */
public class SideEffectServiceImpl extends BaseOpenmrsService implements SideEffectService {

    protected SideEffectDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());
    private final static String CHEMOTHERAPY_MEDS = "8481b9da-74e3-45a9-9124-d69ab572d636";
    private final static String CHEMOTHERAPY_MEDS_OXALIPLATIN = "478ac4b7-56a2-47b4-8c6d-929c44a431ec";
    private final static String CHEMOTHERAPY_MEDS_MITOMYCIN = "cd7db341-0e36-4669-bd1f-dc9c1a797ef2";
    private final static String CANCER_TYPE = "cdf6d767-2aa3-40b6-ae78-0386eebe2411";

    // private final static String CANCER_TREATMENT_SUMMARY_ENCOUNTER = "CANCER
    // TREATMENT SUMMARY";
    private final static String RADIATION_ENCOUNTER = "CANCER TREATMENT - RADIATION";
    private final static String CHEMOTHERAPY_ENCOUNTER = "CANCER TREATMENT - CHEMOTHERAPY";
    private final static String SURGERY_ENCOUNTER = "CANCER TREATMENT - SURGERY";

    /**
     * @return the dao
     */
    public SideEffectDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(SideEffectDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<SideEffect> getAllSideEffects() {
        return dao.getAllSideEffects();
    }

    @Override
    public List<Concept> getAllSideEffectsForPatient(Patient patient) {
        List<Concept> patientSideEffects = new ArrayList<Concept>();
        List<SideEffect> allSideEffects = getAllSideEffects();
        Map<String, Set<Concept>> allSideEffectsMap = new HashMap<String, Set<Concept>>();
        for (SideEffect se : allSideEffects) {
            allSideEffectsMap.put(se.getCondition(), se.getConcepts());
        }
        /*
         * //Male condition
         * if ("M".equalsIgnoreCase(patient.getGender()))
         * patientSideEffects.addAll(allSideEffectsMap.get("Male"));
         * //Female condition
         * else if ("F".equalsIgnoreCase(patient.getGender()))
         * patientSideEffects.addAll(allSideEffectsMap.get("Female"));
         * else{
         * patientSideEffects.addAll(allSideEffectsMap.get("Male"));
         * patientSideEffects.addAll(allSideEffectsMap.get("Female"));
         * }
         */
        // Adding all side effects for all patients
        patientSideEffects.addAll(allSideEffectsMap.get("Male"));
        patientSideEffects.addAll(allSideEffectsMap.get("Female"));
        // Chemotherapy medication used side effects
        Encounter enc = findCancerTreatment(patient, CHEMOTHERAPY_ENCOUNTER);
        if (enc != null) {
            Concept chemoMedsConcept = Context.getConceptService().getConceptByUuid(CHEMOTHERAPY_MEDS);
            List<Obs> meds = Context.getObsService().getObservationsByPersonAndConcept(patient, chemoMedsConcept);
            if (meds != null) {
                for (Obs med : meds) {
                    if (med.getValueCoded() != null) {
                        if (med.getValueCoded().getUuid().equals(CHEMOTHERAPY_MEDS_OXALIPLATIN))
                            patientSideEffects.addAll(allSideEffectsMap.get("Oxaliplatin"));
                        else if (med.getValueCoded().getUuid().equals(CHEMOTHERAPY_MEDS_MITOMYCIN))
                            patientSideEffects.addAll(allSideEffectsMap.get("Mitomycin"));
                        log.debug("Chemotherapy med added: " + med + ", id=" + med.getValueCoded().getUuid());
                    }
                }
            } else {
                log.debug("No chemotherapy meds are found.");
            }
        } else {
            log.debug("No chemotherapy is found.");
        }

        // find Radiation types
        enc = findCancerTreatment(patient, RADIATION_ENCOUNTER);
        if (enc != null) {
            patientSideEffects.addAll(allSideEffectsMap.get("Radiation"));
        }

        // find Surgery types
        enc = findCancerTreatment(patient, SURGERY_ENCOUNTER);
        if (enc != null) {
            // get the patient's cancer type
            try {

                Concept cancerType = getCancerType(patient);
                if (cancerType.getUuid().equals("834a9412-9bdd-4bfd-917f-7e881dcf92af"))
                    patientSideEffects.addAll(allSideEffectsMap.get("Rectal Cancer"));
                else if (cancerType.getUuid().equals("9ce42960-5d1a-4a50-a135-9b459a36d8db"))
                    patientSideEffects.addAll(allSideEffectsMap.get("Colon Cancer"));
            } catch (NullPointerException e) {
                System.out.println("Cancer Type is null");
            }
        }

        return patientSideEffects;
    }

    // helper function to retrieve the required encounter
    private Encounter findCancerTreatment(Patient patient, String encounterType) {
        // find cancer treatment summary encounter
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        Integer encId = null;
        Date encDate = null;
        Encounter latestEncounter = null;
        for (Encounter encounter : encounters) {
            if (!encounter.getVoided() && encounterType.equals(encounter.getEncounterType().getName())) {
                if ((encId == null ||
                        (encDate != null && encounter.getEncounterDatetime() != null
                                && encounter.getEncounterDatetime().after(encDate)))) {
                    encId = encounter.getId();
                    encDate = encounter.getEncounterDatetime();
                    encounter.getObs();
                    latestEncounter = encounter;
                }
            }
        }

        return latestEncounter;

    }

    private Concept getCancerType(Patient pat) {
        Concept cancerTypeConcept = Context.getConceptService().getConceptByUuid(CANCER_TYPE);
        Obs cancerType = findLatest(Context.getObsService().getObservationsByPersonAndConcept(pat, cancerTypeConcept));
        Concept type = cancerType == null ? null : cancerType.getValueCoded();
        return type;
    }

    /**
     */
    private Obs findLatest(List<Obs> observations) {
        Obs latest = null;

        if (observations != null) {
            for (Obs obs : observations) {
                if (obs != null && !obs.getVoided()) {
                    if (latest == null) {
                        latest = obs;
                    } else {
                        Date latestCreatedDate = latest.getDateCreated();
                        Date obsCreatedDate = obs.getDateCreated();
                        if (latestCreatedDate != null && obsCreatedDate != null
                                && latestCreatedDate.before(obsCreatedDate)) {
                            latest = obs;
                        }
                    }

                }
            }
        }

        return latest;
    }
}
