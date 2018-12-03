package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

public class EditPreventiveCareGuideLineFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EditPreventiveCareGuideLineDataModal_FRAGMENT", pageRequest.getRequest()));
    }

    public void SavePreventiveCareGuideLines(FragmentModel model,@RequestParam(value = "operation", required = false) String OpeartionType,
                                             @RequestParam(value = "pcgId", required = false) int pcg_Id,
                                             @RequestParam(value = "guidLineName", required = false) String pcg_name,
                                             @RequestParam(value = "followUpTimeLine", required = false) String followUpTimeLine,
                                             HttpServletRequest servletRequestest) throws ParseException {

        Concept concept = Context.getConceptService().getConcept(pcg_name);
        PreventiveCareGuideline preventiveCareGuideline;

        // Create preventiveCareGuideline object Based on Add or Edit Option
        if(OpeartionType.equals("ADD")) {
            preventiveCareGuideline = new PreventiveCareGuideline();
            preventiveCareGuideline.setName(pcg_name);
            preventiveCareGuideline.setFollowupProcedure(concept);
        }
        else {
            preventiveCareGuideline = Context.getService(PreventativeCareService.class).getPreventiveCareGuideLine(pcg_Id);

            // deleting records from patientportal_pcg_interval table, for edit functionality
            //List<PreventiveCareGuidelineInterval> listPreventiveCareGuideLineInterval = Context.getService(PreventativeCareService.class).getPreventiveCareGuidelineInterval(preventiveCareGuideline);
           // for (PreventiveCareGuidelineInterval pcg_interval: listPreventiveCareGuideLineInterval) {
           //     Context.getService(PreventativeCareService.class).deletePreventiveCareGuidelineInterval(pcg_interval);
          //  }
        }

        // Creating objects for PreventiveCareGuideline and PreventiveCareGuidelineInterval Object


        Set<PreventiveCareGuidelineInterval> hSetPreventiveCareGuidelineInterval = new HashSet<PreventiveCareGuidelineInterval>();
        PreventiveCareGuidelineInterval preventiveCareGuidelineInterval;
        int noOfIntervalSteps = 0;

        List<String> listIntevalLength = Arrays.asList(followUpTimeLine.split("\\s*,\\s*"));
        for(int i=0; i < listIntevalLength.size(); i++)
        {
            preventiveCareGuidelineInterval = new PreventiveCareGuidelineInterval();

            preventiveCareGuidelineInterval.setPcgguideline(preventiveCareGuideline);
            preventiveCareGuidelineInterval.setIntervalLength(Integer.parseInt(listIntevalLength.get(i)));
            hSetPreventiveCareGuidelineInterval.add(preventiveCareGuidelineInterval);
        }
        List<PreventiveCareGuidelineInterval> pcgIList=Context.getService(PreventativeCareService.class).getPreventiveCareGuidelineInterval(preventiveCareGuideline);

        Iterator<PreventiveCareGuidelineInterval> elementListIterator = pcgIList.iterator();
        while (elementListIterator.hasNext()) {
            PreventiveCareGuidelineInterval element = elementListIterator.next();

            if (!hSetPreventiveCareGuidelineInterval.contains(element))
                Context.getService(PreventativeCareService.class).deletePreventiveCareGuidelineInterval(element);
        }

        preventiveCareGuideline.setPcgguidelineIntervalSet(hSetPreventiveCareGuidelineInterval);

        // Save Data in patientportal_pcg and patientportal_pcg_interval Table
        Context.getService(PreventativeCareService.class).savePreventiveCareGuideLine(preventiveCareGuideline);
        for (PreventiveCareGuidelineInterval pcg_interval: hSetPreventiveCareGuidelineInterval) {
            Context.getService(PreventativeCareService.class).savePreventiveCareGuidelineInterval(pcg_interval);
        }

    }

}
