/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by Maurya on 19/08/2015.
 */
public class SymptomManagementFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SYMPTOMMANAGEMENT_FRAGMENT", pageRequest.getRequest()));
        User user = Context.getAuthenticatedUser();
        String url = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        if (url == null || username == null || password == null) {
            throw new APIException("URL, username or password not configured yet.");
        }

        // using GET, everything is in the request
        url += "&username=" + username;
        url += "&password=" + password;
        url += "&omrs_user=" + user.getUuid();
        model.addAttribute("SymptomManagementPortalUrl", url);
    }
}
