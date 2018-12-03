package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.GuidelineInterval;
import org.openmrs.module.patientportaltoolkit.api.GuidelineService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

public class EditGuideLineFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EditGuideLinesDataModal_FRAGMENT", pageRequest.getRequest()));
    }

    public void SaveGuideLines(FragmentModel model,@RequestParam(value = "operation", required = false) String OpeartionType,
                                             @RequestParam(value = "guideLineId", required = false) String guideLineId,
                                             @RequestParam(value = "conceptId", required = false) String conceptId,
                                             @RequestParam(value = "conditionSet", required = false) String strConditionSet,
                                             @RequestParam(value = "guideLineName", required = false) String guideLineName,
                                             @RequestParam(value = "followupTimeLine", required = false) String followupTimeLine,
                                             HttpServletRequest servletRequestest) throws ParseException {
        Concept concept = Context.getConceptService().getConcept(Integer.valueOf(conceptId.trim()));
        Guideline guideline;

        HashMap<String, String> hMapconditionSet = new HashMap<String, String>();
        hMapconditionSet.put("coloncancerstage1", "Colon cancer, Stage 1");
        hMapconditionSet.put("coloncancerstage2", "Colon cancer, Stage 2");
        hMapconditionSet.put("coloncancerstage3", "Colon cancer, Stage 3");
        hMapconditionSet.put("rectalcancerstage1", "Rectal cancer, Stage 1");
        hMapconditionSet.put("rectalcancerstage2", "Rectal cancer, Stage 2");
        hMapconditionSet.put("rectalcancerstage3", "Rectal cancer, Stage 3");

        List<String> listConditionsets = Arrays.asList(strConditionSet.split("\\|"));
        Set<String> hSetCheckedConditionSet = new HashSet<String>();
        for(int i=0; i < listConditionsets.size(); i++) {
            hSetCheckedConditionSet.add(hMapconditionSet.get(listConditionsets.get(i)));
        }

        if(OpeartionType.equals("ADD")) {

            guideline = new Guideline();
        }
        else {
            guideline = Context.getService(GuidelineService.class).getGuidelineById(Integer.parseInt(guideLineId));
        }

        // Creating objects for Guideline and GuidelineInterval Object
        guideline.setName(guideLineName);
        guideline.setFollowupProcedure(concept);
        guideline.setFollowupTimline(followupTimeLine);

        // GuideLine Interval
        Set<GuidelineInterval> hSetGuidelineInterval = new HashSet<GuidelineInterval>();
        GuidelineInterval guidelineInterval;

        //Setting Interval length based on followupTimeLine
        List<String> listIntevalLength = Arrays.asList(followupTimeLine.split("\\s*,\\s*"));
        for(int i=1; i <= listIntevalLength.size(); i++)
        {
            guidelineInterval = new GuidelineInterval();

            guidelineInterval.setGuideline(guideline);
            guidelineInterval.setIntervalLength(Integer.parseInt(listIntevalLength.get(i-1)));
            hSetGuidelineInterval.add(guidelineInterval);
        }
        for (GuidelineInterval guideLineInterval: hSetGuidelineInterval) {
            Context.getService(GuidelineService.class).saveGuideLineInterval(guideLineInterval);
        }
        List<GuidelineInterval> gIList=Context.getService(GuidelineService.class).getAllGuidlinesInterval(guideline);

        Iterator<GuidelineInterval> elementListIterator = gIList.iterator();
        while (elementListIterator.hasNext()) {
            GuidelineInterval element = elementListIterator.next();

            if (!hSetGuidelineInterval.contains(element))
                Context.getService(GuidelineService.class).deleteGuidelineInterval(element);
        }

        // GuideLine Interval

        //GuideLineConditionSet - GuideLine ConditionSet Object

        // To add GuidelineConditionSet in GuidelineConditionSet Object
        List<GuidelineConditionSet> listguideLineConditionSetToBesaved = new ArrayList<>();
        Set<Guideline> guideLines=null;
        List<GuidelineConditionSet> allGuidelineConditionSets = Context.getService(GuidelineService.class).getGuidlineConditionSetbyConditions();
        for (GuidelineConditionSet gcs: allGuidelineConditionSets) {
            guideLines= gcs.getGuidelines();
            if(hSetCheckedConditionSet.contains(gcs.getConditionName()))
                guideLines.add(guideline);
            else
                guideLines.remove(guideline);
            gcs.setGuidelines(guideLines);
            Context.getService(GuidelineService.class).saveGuideLineConditionSet(gcs);
        }

        // Save Data in guideline and guidelineInterval Table
        Context.getService(GuidelineService.class).saveGuideLine(guideline);


    }
}
