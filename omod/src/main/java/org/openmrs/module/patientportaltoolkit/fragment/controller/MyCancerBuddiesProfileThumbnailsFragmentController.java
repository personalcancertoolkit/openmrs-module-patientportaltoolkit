package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 10/4/16.
 */
public class MyCancerBuddiesProfileThumbnailsFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest, @FragmentParam(value="person") Person person ) {
        log.info(PPTLogAppender.appendLog("REQUEST_MYCANCERBUDDIESTHUMBNAILS_FRAGMENT", pageRequest.getRequest()));

            model.addAttribute("mycancerbuddiespeople",Context.getService(PersonPreferencesService.class).getAllPersonPreferences());
    }
}
