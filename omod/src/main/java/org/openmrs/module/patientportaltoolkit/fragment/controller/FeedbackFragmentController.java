package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.notification.MessageException;
import org.openmrs.notification.MessageService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 2/4/16.
 */
public class FeedbackFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void sendFeedback(FragmentModel model, @RequestParam(value = "feedbackMessage", required = true) String feedback) {


        String to = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL);
        ;

        String from = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_FROM_EMAIL);


        try {
            Context.getService(MessageService.class).sendMessage(to,from,"Contact Form Results", feedback);
        } catch (MessageException e) {
            e.printStackTrace();
        }

    }
}
