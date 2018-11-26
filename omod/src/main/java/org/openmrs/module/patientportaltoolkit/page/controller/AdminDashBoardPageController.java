package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.*;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdminDashBoardPageController {

    protected final Log log = LogFactory.getLog(getClass());


    public void controller(PageModel model, PageRequest pageRequest) {

        List<CancerCommunityResources> cancerComunityData = Context.getService(CancerCommunityResourcesService.class).getCancerCommunityResourcesService();
        List<PreventiveCareGuideline> listPreventiveCareGuideLine = Context.getService(PreventativeCareService.class).getPreventiveCareGuideLine();
        List<Guideline> listGuideLines = Context.getService(GuidelineService.class).getAllGuidlines();
        List<GuidelineConditionSet> listGuideLineConditionSet = Context.getService(GuidelineService.class).getGuidlineConditionSetbyConditions();
        List<SideEffect> listSideEfect = Context.getService(SideEffectService.class).getAllSideEffects();

        List<PreventiveCareGuideline> adminListPreventiveCareGuideLine = new ArrayList<PreventiveCareGuideline>();
        List<Guideline> adminListGuideLine = new ArrayList<Guideline>();
        List<SideEffect> adminListSideEffect = new ArrayList<SideEffect>();

        // Preventive Care GuideLine
        for(PreventiveCareGuideline pcg : listPreventiveCareGuideLine) {

            int intervalNumber = -1, intervalLength = -1;
            PreventiveCareGuideline admin_pcg;
            List<PreventiveCareGuidelineInterval> listPreventiveCareGuideLineInterval = Context.getService(PreventativeCareService.class).getPreventiveCareGuidelineInterval(pcg);

            admin_pcg = new PreventiveCareGuideline();
            admin_pcg.setId(pcg.getId());
            admin_pcg.setName(pcg.getName());
            admin_pcg.setCancerTypeId(pcg.getCancerTypeId());

            StringBuilder sbFollowUpTimeline = new StringBuilder();
            for(PreventiveCareGuidelineInterval pcg_interval : listPreventiveCareGuideLineInterval) {

                sbFollowUpTimeline.append(pcg_interval.getIntervalLength() + ",");

//                To get the interval Length ( (id = 2) - (id = 1))
//                if(pcg_interval.getIntervalNumber() == 2) {
//                    intervalLength = pcg_interval.getIntervalLength();
//                }
//
//                // To get the maximum interval Number
//                if(pcg_interval.getIntervalNumber() > intervalNumber) {
//                    intervalNumber = pcg_interval.getIntervalNumber();
//                }
            }
            if(sbFollowUpTimeline != null && !sbFollowUpTimeline.toString().isEmpty() && sbFollowUpTimeline.toString().length() > 0)
                admin_pcg.setfollowupTimeLine(sbFollowUpTimeline.substring(0, sbFollowUpTimeline.length() - 1));
            else
                admin_pcg.setfollowupTimeLine(null);

            adminListPreventiveCareGuideLine.add(admin_pcg);
        }

        for(Guideline guideLineObj : listGuideLines) {

            Guideline admin_guideLine;
            List<GuidelineInterval> listGuideLineInterval = Context.getService(GuidelineService.class).getAllGuidlinesInterval(guideLineObj);

            admin_guideLine = new Guideline();
            admin_guideLine.setId(guideLineObj.getId());
            admin_guideLine.setName(guideLineObj.getName());
            admin_guideLine.setFollowupProcedure(guideLineObj.getFollowupProcedure());
            //admin_guideLine.setConditionsSet(guideLineObj.getConditionsSet());
            admin_guideLine.setFollowupTimline(guideLineObj.getFollowupTimline());


            // Interval Length
//            StringBuilder sbIntervalLength = new StringBuilder();
//            for(GuidelineInterval guideLine_interval : listGuideLineInterval) {
//                sbIntervalLength.append(guideLine_interval.getIntervalLength() + ", ");
//            }
//            String strIntervalLength = sbIntervalLength.toString().replaceAll(", $", "");
//            admin_guideLine.setIntervalLength(strIntervalLength);

            StringBuilder sbConditionSet = new StringBuilder();

            for(GuidelineConditionSet guidelineConditionSetIter:listGuideLineConditionSet){
                Set<Guideline> guidelineConditionSetGuideLines = guidelineConditionSetIter.getGuidelines();
                //if (guidelineConditionConcepts.size() == guideLineObj.getConditionsSet().size() && guidelineConditionConcepts.containsAll(guideLineObj.getConditionsSet())) {
                if (guidelineConditionSetGuideLines.contains(guideLineObj)) {

                    sbConditionSet.append(guidelineConditionSetIter.getConditionName() + "|");
                  //For future - If we need condition set
//                  for(Concept guideLineConditionSetConceptID: guidelineConditionSetIter.getConceptSet()) {
//                        sbConditionSet.append(guideLineConditionSetConceptID.getConceptId() + ",");
//                  }
                }
            }

            String stringConditionSet = sbConditionSet.toString().toLowerCase().replaceAll("[, ;]", "");
            if(sbConditionSet != null && !sbConditionSet.toString().isEmpty() && sbConditionSet.toString().length() > 0)
                admin_guideLine.setGuidelineConditionSet(stringConditionSet.substring(0, stringConditionSet.length() - 1));
            else
                admin_guideLine.setGuidelineConditionSet(null);

            adminListGuideLine.add(admin_guideLine);
        }


        for(SideEffect sideEffectObj : listSideEfect) {

            StringBuilder sbSideEffectConcept =  new StringBuilder();
            Set<Concept>  hSetConcept = sideEffectObj.getConcepts();
            for(Concept conceptObj : hSetConcept)
            {
                sbSideEffectConcept.append(conceptObj.getConceptId() + " (" + conceptObj.getName() + "), ");
            }

            if(sbSideEffectConcept != null && !sbSideEffectConcept.toString().isEmpty() && sbSideEffectConcept.toString().length() > 0)
            {
                int lastCommaIndex = sbSideEffectConcept.toString().lastIndexOf(',');
                sideEffectObj.setConceptIdName(sbSideEffectConcept.toString().substring(0, lastCommaIndex));;
            }
            else
            {
                sideEffectObj.setConceptIdName(null);
            }
            adminListSideEffect.add(sideEffectObj);
        }



        //Attribute Binding - Cancer Type
        if(cancerComunityData != null) {
            model.addAttribute("CancerCommunityData", cancerComunityData);
        }
        else {
            model.addAttribute("CancerCommunityData", null);
        }

        //Attribute Binding - Preventive Care GuideLine
        model.addAttribute("preventiveCareGuideLineData", adminListPreventiveCareGuideLine);

        //Attribute Binding - GuideLine
        model.addAttribute("GuideLineData", adminListGuideLine);

        //Attribute Binding - SideEffect
        model.addAttribute("SideEffectConceptMapping", adminListSideEffect);

        log.info(PPTLogAppender.appendLog("REQUEST_ADMINDASHBOARD_PAGE", pageRequest.getRequest()));
    }
}

