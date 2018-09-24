package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.*;

public class AdminDashBoardPageController {

    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model, PageRequest pageRequest) {

        List<CancerCommunityResources> cancerComunityData = Context.getService(CancerCommunityResourcesService.class).getCancerCommunityResourcesService();
        List<PreventiveCareGuideline> listPreventiveCareGuideLine = Context.getService(PreventativeCareService.class).getPreventiveCareGuideLine();
        List<PreventiveCareGuideline> adminListPreventiveCareGuideLine = new ArrayList<PreventiveCareGuideline>();

        if(cancerComunityData != null) {
            model.addAttribute("CancerCommunityData", cancerComunityData);
        }
        else {
            model.addAttribute("CancerCommunityData", null);
        }


        for(PreventiveCareGuideline pcg : listPreventiveCareGuideLine) {

            int intervalNumber = -1, intervalLength = -1;
            PreventiveCareGuideline admin_pcgInterval;
            List<PreventiveCareGuidelineInterval> listPreventiveCareGuideLineInterval = Context.getService(PreventativeCareService.class).getPreventiveCareGuidelineInterval(pcg);

            admin_pcgInterval = new PreventiveCareGuideline();
            admin_pcgInterval.setId(pcg.getId());
            admin_pcgInterval.setName(pcg.getName());
            admin_pcgInterval.setCancerTypeId(pcg.getCancerTypeId());

            for(PreventiveCareGuidelineInterval pcg_interval : listPreventiveCareGuideLineInterval) {

                // To get the interval Length ( (id = 2) - (id = 1))
                if(pcg_interval.getIntervalNumber() == 2) {
                    intervalLength = pcg_interval.getIntervalLength();
                }

                // To get the maximum interval Number
                if(pcg_interval.getIntervalNumber() > intervalNumber) {
                    intervalNumber = pcg_interval.getIntervalNumber();
                }
            }
            admin_pcgInterval.setIntervalLength(intervalLength);
            admin_pcgInterval.setNoOfInterval(intervalNumber);

            if(intervalNumber != -1 && intervalLength != -1) {
                adminListPreventiveCareGuideLine.add(admin_pcgInterval);
            }
        }
        model.addAttribute("preventiveCareGuideLineData", adminListPreventiveCareGuideLine);
        log.info(PPTLogAppender.appendLog("REQUEST_ADMINDASHBOARD_PAGE", pageRequest.getRequest()));
    }
}

