package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.api.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by maurya on 6/9/16.
 */
public class PPTLogAppender {

    public static String appendLog (String token, HttpServletRequest request,String... data){

       /* String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }*/

        String message = Context.getAuthenticatedUser().getSystemId() +" "+ Context.getAuthenticatedUser().getUsername() +" "+ token +" "+ request.getRequestURI()+" "+request.getMethod()+""+request.getHeader("User-Agent");
        for(String iter: data){
            message=message+" "+iter;
        }
        return message;
    }

}
