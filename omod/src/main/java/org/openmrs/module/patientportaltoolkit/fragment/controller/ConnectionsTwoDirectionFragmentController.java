package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by srikumma on 5/18/17.
 */
public class ConnectionsTwoDirectionFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model,
            @FragmentParam(value = "twoDirectionFragmentPerson") Person twoDirectionFragmentPerson,
            @FragmentParam(value = "twoDirectionFragmentRelation") PatientPortalRelation twoDirectionFragmentRelation,
            @FragmentParam(value = "twoDirectionFragmentSide") String twoDirectionFragmentSide,
            @FragmentParam(value = "twoDirectionFragmentAccepted") boolean twoDirectionFragmentAccepted,
            PageRequest pageRequest) {
        model.addAttribute("twoDirectionFragmentPerson", twoDirectionFragmentPerson);
        model.addAttribute("twoDirectionFragmentRelation", twoDirectionFragmentRelation);
        model.addAttribute("twoDirectionFragmentSide", twoDirectionFragmentSide);
        model.addAttribute("twoDirectionFragmentAccepted", twoDirectionFragmentAccepted);
        log.info(PPTLogAppender.appendLog("REQUEST_RELATIONSHIPSELECT_FRAGMENT", pageRequest.getRequest()));

    }
}
