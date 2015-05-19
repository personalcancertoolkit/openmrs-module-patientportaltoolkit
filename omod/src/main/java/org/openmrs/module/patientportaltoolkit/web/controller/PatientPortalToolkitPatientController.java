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

package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maurya.
 */
@Controller
public class PatientPortalToolkitPatientController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping( value = "/patientportaltoolkit/getpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalPatient( @PathVariable( "patientId" ) String patientId)
            throws Exception
    {

        PatientService patientService = Context.getService(PatientService.class);
        Map<String, Object> patientObject = patientService.getPatient(patientId);
        return patientObject;

    }

    @RequestMapping( value = "/patientportaltoolkit/getallpatients")
    @ResponseBody
    public Object getAllPatientPortalPatient()
            throws Exception
    {
        PatientService patientService = Context.getService(PatientService.class);
        List<Object> patientObjects = patientService.getAllPatients();
        return patientObjects;

    }
    @RequestMapping( value = "/patientportaltoolkit/getuser")
    @ResponseBody
    public Object getPatientPortalUser()
            throws Exception
    {
        User  user=Context.getAuthenticatedUser();
        Map<String, Object> authenticatedUser = new HashMap<String, Object>();
        authenticatedUser.put("id", user.getUuid());
        authenticatedUser.put("Name", user.getDisplayString());

        return authenticatedUser;
    }
}
