/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.api.context.Context;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maurya on 6/9/16.
 */
public class PPTLogAppender {

    public static String appendLog (String token, HttpServletRequest request,String... data){

       /* String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }*/

        String message = Context.getAuthenticatedUser().getSystemId() +" "+ Context.getAuthenticatedUser().getUsername() +" "+ token +" "+ request.getRequestURI()+" "+request.getMethod()+" "+request.getHeader("User-Agent");
        for(String iter: data){
            message=message+" "+iter;
        }
        return message;
    }

}
