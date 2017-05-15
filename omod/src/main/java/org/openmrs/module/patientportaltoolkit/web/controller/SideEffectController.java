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
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maurya on 02/06/2015.
 */

@Controller
public class SideEffectController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping( value = "/patientportaltoolkit/getallsideeffects")
     @ResponseBody
     public Object getAllSideEffects()
    {
        return ToolkitResourceUtil.generateSideEffects(Context.getService(SideEffectService.class).getAllSideEffects());
    }

    @RequestMapping( value = "/patientportaltoolkit/getsideeffectsforpatient/{patientId}")
    @ResponseBody
    public Object getAllSideEffectsforPatient(@PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateConcepts(Context.getService(SideEffectService.class).getAllSideEffectsForPatient(patient));
    }
}
