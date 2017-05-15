/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;




/**
 * Spring MVC controller that takes over /login.htm and processes requests to authenticate a user
 */
@Controller
public class CustomLoginPageController {
    
    
    public void controller(PageModel model, PageRequest pageRequest) {
        //Log Processing events
        //HttpServletRequest request=pageRequest.getRequest();

        //log.info(PPTLogAppender.appendLog(token, pageRequest.getRequest()));
        Boolean loggedInBoolean = false;
        if (Context.isAuthenticated()) {
            loggedInBoolean = true;
            //System.out.println("Already logged in");
        } else {
            //System.out.println("Not logged in");
        }
        model.addAttribute("loggedInBoolean", loggedInBoolean);
    }
    
    
    
    /////////////////////////////
    // Static Constants defined in the referenceapplication module 
    /////////////////////////////
	private static final String GET_LOCATIONS = "Get Locations";
    private static final String VIEW_LOCATIONS = "View Locations";
	public static final String REQUEST_PARAMETER_NAME_REDIRECT_URL = "redirectUrl";
	public static final String SESSION_ATTRIBUTE_REDIRECT_URL = "_REFERENCE_APPLICATION_REDIRECT_URL_";
	public static final String MODULE_ID = "patientportaltoolkit";
	protected final Log log = LogFactory.getLog(getClass());
    
    /////////////////////////////
    // Authentication Method
    /////////////////////////////
    public String post(PageModel model, 
                               @RequestParam(value = "username", required = true) String username,
                               @RequestParam(value = "password", required = true) String password, 
                               @SpringBean("locationService") LocationService locationService,
                               UiUtils ui, 
                               PageRequest pageRequest ) {
        
        
        //System.out.println("Here i am");
        //System.out.println(username);
        //System.out.println(password);
        
        
        ////////////////////////////////////////////
        // Define location as unknown location
        ////////////////////////////////////////////
        int sessionLocationId = 1;
		Location sessionLocation = null;
        try {
            // TODO as above, grant this privilege to Anonymous instead of using a proxy privilege
            Context.addProxyPrivilege(VIEW_LOCATIONS);
            Context.addProxyPrivilege(GET_LOCATIONS);
            sessionLocation = locationService.getLocation(sessionLocationId);
        }
        finally {
            Context.removeProxyPrivilege(VIEW_LOCATIONS);
            Context.removeProxyPrivilege(GET_LOCATIONS);
        }

        /////////////////////////////////
        // Attempt to log user in
        /////////////////////////////////
        Boolean login_success = false;
        try {
            Context.authenticate(username, password);
            if (Context.isAuthenticated()) {
                if (log.isDebugEnabled())
                    log.debug("User has successfully authenticated");
                login_success = true;
            }
        } catch (ContextAuthenticationException ex) {
            if (log.isDebugEnabled())
                log.debug("Failed to authenticate user");
        }

		
        /////////////////////////////////////
        // Define where to redirect user
        /////////////////////////////////////
        String final_redirect = "";
        if(!login_success) final_redirect = "redirect:" + ui.pageLink(MODULE_ID, "login");
        if(login_success) final_redirect = "redirect:" + ui.pageLink(MODULE_ID, "home");
        //System.out.println(final_redirect);
        
        return final_redirect; // Note: this does not do anything currently - but was found in the referenceapplication module
    }
}
