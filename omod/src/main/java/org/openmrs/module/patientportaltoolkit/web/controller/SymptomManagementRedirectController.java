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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

        return url;
       /* try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new APIException("cannot redirect.", e);
        }*/
    }

    private String respondUsingPOST(HttpServletRequest request, HttpServletResponse response, User user) {

        // get the url, username and password
        String url = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        // TODO send a real response with a helpful UI message
        if (url == null || username == null || password == null) {
            throw new APIException("URL, username or password not configured yet.");
        }

        // session attributes may be useful later
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("omrs_user", user.getUsername());

       // RequestDispatcher rd = request.getRequestDispatcher(url);
      /*  try {
            rd.forward(request, response);
        } catch (ServletException e) {
            throw new APIException("cannot redirect.", e);
        } catch (IOException e) {
            throw new APIException("cannot redirect.", e);
        }*/
        return url;
    }

    private void respondUsingGET(HttpServletRequest request, HttpServletResponse response, User user) {
        HttpSession session = request.getSession();

        // get the url, username and password
        String url = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_URL);
        String username = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_USERNAME);
        String password = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SYMPTOM_MANAGEMENT_PASSWORD);

        // TODO send a real response with a helpful UI message
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