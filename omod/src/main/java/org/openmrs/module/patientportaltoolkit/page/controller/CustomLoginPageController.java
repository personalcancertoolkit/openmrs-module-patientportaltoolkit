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

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;


/**
 * Spring MVC controller that takes over /login.htm and processes requests to authenticate a user
 */
@Controller
public class CustomLoginPageController {

    /*
	@RequestMapping("/login.htm")
	public String overrideLoginpage() {
		//TODO The referer should actually be captured from here since we are doing a redirect
		return "forward:/patientportaltoolkit/login.page";
	}
    */
    
    
    public void controller(PageModel model, PageRequest pageRequest) {

        //Log Processing events
        //HttpServletRequest request=pageRequest.getRequest();

        //log.info(PPTLogAppender.appendLog(token, pageRequest.getRequest()));

    }

	/**
	 * @should redirect the user to the home page if they are already authenticated
	 * @should show the user the login page if they are not authenticated
	 * @should set redirectUrl in the page model if any was specified in the request
	 * @should set the referer as the redirectUrl in the page model if no redirect param exists
	 * @should set redirectUrl in the page model if any was specified in the session
	 * @should not set the referer as the redirectUrl in the page model if referer URL is outside context path
	 * @should set the referer as the redirectUrl in the page model if referer URL is within context path
	 */
}
