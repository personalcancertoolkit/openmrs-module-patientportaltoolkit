package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * Created by Maurya on 19/08/2015.
 */
public class SymptomManagementFragmentController {


    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model) {
        User user = Context.getAuthenticatedUser();
        String url = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        // TODO send a real response with a helpful UI message
        if (url == null || username == null || password == null) {
            throw new APIException("URL, username or password not configured yet.");
        }

        // using GET, everything is in the request
        url += "&username=" + username;
        url += "&password=" + password;
        url += "&omrs_user=" + user.getUsername();
        model.addAttribute("SymptomManagementPortalUrl",url);
    }
}
