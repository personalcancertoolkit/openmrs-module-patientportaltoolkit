package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.SideEffect;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditSideEffectFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EditGuideLinesDataModal_FRAGMENT", pageRequest.getRequest()));
    }

    public void SaveEffect(FragmentModel model,@RequestParam(value = "SideEffectOperation", required = false) String OpeartionType,
                               @RequestParam(value = "SideEffectId", required = false) Integer sideEffectId,
                               @RequestParam(value = "SideEffectConditionName", required = false) String sideEffectConditionName,
                               @RequestParam(value = "SideEffectConceptIds", required = false) String sideEffectConceptIds,
                               @RequestParam(value = "SideEffectConceptIdNew", required = false) String sideEffectConceptIdNew,
                               HttpServletRequest servletRequestest) throws ParseException {

        //System.out.print(OpeartionType + "   " +  sideEffectId + "  " + sideEffectConditionName + "  " + sideEffectConceptIds + "  " +  sideEffectConceptIdNew);
        SideEffect sideEffectObj = null;
        Set<Concept> hashSetSideffectConcept = new HashSet<Concept>();

        if(OpeartionType.equals("EDIT"))
        {
            sideEffectObj = Context.getService(SideEffectService.class).getSideEffect(sideEffectId);

            Set<Integer> hashSetConceptId = new HashSet<Integer>();
            List<String> listConceptId =  Arrays.asList(sideEffectConceptIds.split(","));
            for(String strConceptId : listConceptId) {
                hashSetConceptId.add(Integer.parseInt(strConceptId));
            }

            Set<Concept> hSetSidEffectConcept = sideEffectObj.getConcepts();
            //Adding Concepts in Side Effect
            for(Concept sideEffectConcept : hSetSidEffectConcept)
            {
                if(hashSetConceptId.contains(sideEffectConcept.getConceptId()))
                    hashSetSideffectConcept.add(sideEffectConcept);
            }
        }
        else
        {
            sideEffectObj = new SideEffect();
        }

        sideEffectObj.setCondition(sideEffectConditionName);
        if(sideEffectConceptIdNew != null && !sideEffectConceptIdNew.isEmpty())
        {
            Concept concept = Context.getConceptService().getConcept(sideEffectConceptIdNew);
            hashSetSideffectConcept.add(concept);
        }
        sideEffectObj.setConcepts(hashSetSideffectConcept);

        //Saving Side Effect
        Context.getService(SideEffectService.class).saveSideEffect(sideEffectObj);
    }
}
