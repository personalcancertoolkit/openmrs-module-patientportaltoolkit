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
