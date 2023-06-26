/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.web.controller;

import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.springframework.stereotype.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Maurya on 18/08/2015.
 */
@Controller
public class SymptomManagementRedirectController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/module/patientportaltoolkit/portal.htm", method = RequestMethod.GET)
    public String redirect(HttpServletResponse response) {
        User user = Context.getAuthenticatedUser();

        // get the url, username and password
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
        url += "&omrs_user=" + user.getUsername();

        return url;
    }

    @SuppressWarnings("unused")
    private String respondUsingPOST(HttpServletRequest request, HttpServletResponse response, User user) {

        // get the url, username and password
        String url = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        if (url == null || username == null || password == null) {
            throw new APIException("URL, username or password not configured yet.");
        }

        // session attributes may be useful later
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("omrs_user", user.getUsername());
        return url;
    }

    @SuppressWarnings("unused")
    private void respondUsingGET(HttpServletRequest request, HttpServletResponse response, User user) {
        HttpSession session = request.getSession();

        // get the url, username and password
        String url = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService()
                .getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        if (url == null || username == null || password == null) {
            throw new APIException("URL, username or password not configured yet.");
        }

        // session attributes may be useful later
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("omrs_user", user.getUsername());

        // using GET, everything is in the request
        url += "&username=" + username;
        url += "&password=" + password;
        url += "&omrs_user=" + user.getUsername();

        log.info("Setting omrs_user to: " + user.getUsername());

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new APIException("cannot redirect.", e);
        }
    }
}