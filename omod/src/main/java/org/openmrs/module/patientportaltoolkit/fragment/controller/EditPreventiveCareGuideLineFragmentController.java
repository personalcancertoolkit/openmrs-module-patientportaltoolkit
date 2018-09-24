package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditPreventiveCareGuideLineFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EditCategorizeCommunitiesDataModal_FRAGMENT", pageRequest.getRequest()));
    }

    public void SavePreventiveCareGuideLines(FragmentModel model,@RequestParam(value = "operation", required = false) String OpeartionType,
                                             @RequestParam(value = "pcgId", required = false) int pcg_Id,
                                             @RequestParam(value = "cancerTypeId", required = false) int cancerTypeId,
                                             @RequestParam(value = "guidLineName", required = false) String pcg_name,
                                             @RequestParam(value = "noOfInterval", required = false) int noOfInterval,
                                             @RequestParam(value = "intervalLength", required = false) int intervalLength,
                                             HttpServletRequest servletRequestest) throws ParseException {

        //log.info(PPTLogAppender.appendLog("Admin_PreventiveCareGuideLines", servletRequestest,"Cancer Type Id:" , cancerTypeId ,"GuidLine Name:", guidLineName));
        Concept concept = Context.getConceptService().getConcept(pcg_name);
        PreventiveCareGuideline preventiveCareGuideline;

        // Create preventiveCareGuideline object Based on Add or Edit Option
        if(OpeartionType.equals("ADD")) {
            preventiveCareGuideline = new PreventiveCareGuideline();
        }
        else {
            preventiveCareGuideline = Context.getService(PreventativeCareService.class).getPreventiveCareGuideLine(pcg_Id);

            // deleting records from patientportal_pcg_interval table, for edit functionality
            List<PreventiveCareGuidelineInterval> listPreventiveCareGuideLineInterval = Context.getService(PreventativeCareService.class).getPreventiveCareGuidelineInterval(preventiveCareGuideline);
            for (PreventiveCareGuidelineInterval pcg_interval: listPreventiveCareGuideLineInterval) {
                Context.getService(PreventativeCareService.class).deletePreventiveCareGuidelineInterval(pcg_interval);
            }
        }

        // Creating objects for PreventiveCareGuideline and PreventiveCareGuidelineInterval Object
        preventiveCareGuideline.setCancerTypeId(cancerTypeId);
        preventiveCareGuideline.setName(pcg_name);
        preventiveCareGuideline.setFollowupProcedure(concept);

        Set<PreventiveCareGuidelineInterval> hSetPreventiveCareGuidelineInterval = new HashSet<PreventiveCareGuidelineInterval>();
        PreventiveCareGuidelineInterval preventiveCareGuidelineInterval;
        int noOfIntervalSteps = 0;
        for(int i=1; i <= noOfInterval; i++)
        {
            preventiveCareGuidelineInterval = new PreventiveCareGuidelineInterval();

            preventiveCareGuidelineInterval.setIntervalNumber(i);
            preventiveCareGuidelineInterval.setPcgguideline(preventiveCareGuideline);
            preventiveCareGuidelineInterval.setIntervalLength(noOfIntervalSteps);
            hSetPreventiveCareGuidelineInterval.add(preventiveCareGuidelineInterval);
            noOfIntervalSteps += intervalLength;
        }
        preventiveCareGuideline.setPcgguidelineIntervalSet(hSetPreventiveCareGuidelineInterval);

        // Save Data in patientportal_pcg and patientportal_pcg_interval Table
        Context.getService(PreventativeCareService.class).savePreventiveCareGuideLine(preventiveCareGuideline);
        for (PreventiveCareGuidelineInterval pcg_interval: hSetPreventiveCareGuidelineInterval) {
            Context.getService(PreventativeCareService.class).savePreventiveCareGuidelineInterval(pcg_interval);
        }

    }

}
