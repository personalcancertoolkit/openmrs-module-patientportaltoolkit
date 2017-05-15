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
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.api.GuidelineService;
import org.openmrs.module.patientportaltoolkit.api.db.GuidelineDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 08/06/2015.
 */
public class GuidelineServiceImpl extends BaseOpenmrsService implements GuidelineService {

    protected GuidelineDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    private final static Integer CANCER_TYPE = 162869;
    private final static Integer CANCER_STAGE = 162875;

    /**
     * @return the dao
     */
    public GuidelineDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(GuidelineDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<Guideline> getAllGuidlines() {
        return dao.getAllGuidelines();
    }

    @Override
    public List<Guideline> getGuidlinesByConditions(Set<Concept> conditions) {
        List<Guideline> allGuidelines=dao.getAllGuidelines();
        List<Guideline> conditionGuidelines = new ArrayList<>();
        for(Guideline guideline:allGuidelines){
            Set<Concept> guidelinesConditions = guideline.getConditionsSet();
            if (guidelinesConditions.size() == conditions.size() && guidelinesConditions.containsAll(conditions)) {
                conditionGuidelines.add(guideline);
            }
        }
        return conditionGuidelines;
    }

    /**
     * Find guidelines for a given patient
     *
     * @param pat
     */
    @Override
    public List<Guideline>  findGuidelines(Patient pat) {
        //find cancer type
        Concept type = getCancerType(pat);
        //find cancer stage
        Concept cancerStageConcept = Context.getConceptService().getConcept(CANCER_STAGE);
        Obs cancerStage = findLatest(Context.getObsService().getObservationsByPersonAndConcept(pat, cancerStageConcept));
        Concept stage = cancerStage==null? null : cancerStage.getValueCoded();

        //find follow-up years guidelines
        List<Guideline> allguidelines = dao.getAllGuidelines();

        Set<Concept> conditionConcepts = new HashSet<>();
        conditionConcepts.add(type);
        conditionConcepts.add(cancerStageConcept);
        List<Guideline> guidelines = new ArrayList<>();
        for (Guideline guidlineIterator: allguidelines){
            if(guidlineIterator.getConditionsSet().equals(conditionConcepts))
                guidelines.add(guidlineIterator);
           // System.out.print(guidlineIterator.getFollowupProcedure().getConceptId());
        }
        return guidelines;
    }

    @Override
    public GuidelineConditionSet getGuidlineConditionSetbyConditions(Set<Concept> conditions) {
        List<GuidelineConditionSet> allGuidelineConditionSet=dao.getAllGuidelineConditionSet();
        GuidelineConditionSet guidelineConditionSet = new GuidelineConditionSet();
        for(GuidelineConditionSet guidelineConditionSetIter:allGuidelineConditionSet){
            Set<Concept> guidelineConditionConcepts = guidelineConditionSetIter.getConceptSet();
            if (guidelineConditionConcepts.size() == conditions.size() && guidelineConditionConcepts.containsAll(conditions)) {
                guidelineConditionSet=guidelineConditionSetIter;
            }
        }
        return guidelineConditionSet;
    }

    private Concept getCancerType(Patient pat) {
        Concept cancerTypeConcept = Context.getConceptService().getConcept(CANCER_TYPE);
        Obs cancerType = findLatest(Context.getObsService().getObservationsByPersonAndConcept(pat, cancerTypeConcept));
        Concept type = cancerType==null? null : cancerType.getValueCoded();
        return type;
    }

    /**
     *
     *
     * @param
     * @return
     */
    private Obs findLatest(List<Obs> observations) {
        Obs latest = null;

        if(observations != null) {
            for (Obs obs : observations) {
                if(obs != null && !obs.isVoided()) {
                    if(latest == null || latest.getDateCreated().before(obs.getDateCreated())) {
                        latest = obs;
                    }

                }
            }
        }

        return latest;
    }

}
