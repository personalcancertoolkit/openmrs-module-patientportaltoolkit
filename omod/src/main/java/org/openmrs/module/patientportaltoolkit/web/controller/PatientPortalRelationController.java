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
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Maurya.
 */
@Controller
public class PatientPortalRelationController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping( value = "/patientportaltoolkit/getrelationsforpatient/{patientId}")
        @ResponseBody
        public Object getPatientPortalRelationsForPatient( @PathVariable( "patientId" ) String patientId)
            throws Exception
    {
        Patient patient= Context.getPatientService().getPatientByUuid(patientId);
        List<Object> relations = (List<Object>) ToolkitResourceUtil.generateRelations(Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPatient(patient));
        return relations;
    }

    @RequestMapping( value = "/patientportaltoolkit/getallrelations")
    @ResponseBody
    public Object getAllPatientPortalRelations()
            throws Exception
    {
        List<Object> relations = (List<Object>) ToolkitResourceUtil.generateRelations(Context.getService(PatientPortalRelationService.class).getAllPatientPortalRelations());
        return relations;
    }

    @RequestMapping( value = "/patientportaltoolkit/getrelations/{searchText}")
    @ResponseBody
    public Object getPatientPortalRelationsForSearchText(@PathVariable( "searchText" ) String searchText)
            throws Exception
    {
        List<Object> relations = (List<Object>) ToolkitResourceUtil.generateRelations(Context.getService(PatientPortalRelationService.class).getAllPatientPortalRelations());
        return relations;
    }
}
