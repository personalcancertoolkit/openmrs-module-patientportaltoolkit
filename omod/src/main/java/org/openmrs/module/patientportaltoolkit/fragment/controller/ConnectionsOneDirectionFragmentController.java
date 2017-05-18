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
public class ConnectionsOneDirectionFragmentController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(FragmentModel model, @FragmentParam(value="oneDirectionFragmentPerson") Person oneDirectionFragmentPerson, @FragmentParam(value="oneDirectionFragmentRelation") PatientPortalRelation oneDirectionFragmentRelation, @FragmentParam(value="oneDirectionFragmentSide") String oneDirectionFragmentSide, @FragmentParam(value="oneDirectionFragmentAccepted") boolean oneDirectionFragmentAccepted , PageRequest pageRequest) {
        model.addAttribute("oneDirectionFragmentPerson", oneDirectionFragmentPerson);
        model.addAttribute("oneDirectionFragmentRelation", oneDirectionFragmentRelation);
        model.addAttribute("oneDirectionFragmentSide", oneDirectionFragmentSide);
        model.addAttribute("oneDirectionFragmentAccepted", oneDirectionFragmentAccepted);
        log.info(PPTLogAppender.appendLog("REQUEST_RELATIONSHIPSELECT_FRAGMENT", pageRequest.getRequest()));

    }
}
