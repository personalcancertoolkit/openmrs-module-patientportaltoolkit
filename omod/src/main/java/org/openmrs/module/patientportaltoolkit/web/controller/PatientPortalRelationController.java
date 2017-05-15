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
