/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
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
        url += "&omrs_user=" + user.getUuid();
        model.addAttribute("SymptomManagementPortalUrl", url);
        //log.info("Symptom Management for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
