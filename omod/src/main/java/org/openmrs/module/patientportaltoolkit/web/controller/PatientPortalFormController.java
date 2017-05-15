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
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maurya on 01/06/2015.
 */

@Controller
public class PatientPortalFormController {

    protected final Log log = LogFactory.getLog(getClass());
    @RequestMapping( value = "/patientportaltoolkit/getallforms")
    @ResponseBody
    public Object getAllPatientPortalForms()
    {
        return ToolkitResourceUtil.generatePatientPortalForms(Context.getService(PatientPortalFormService.class).getAllPatientPortalForms());
    }

    @RequestMapping( value = "/patientportaltoolkit/getform/{formId}")
    @ResponseBody
    public Object getPatientPortalForm(@PathVariable( "formId" ) String formId)
    {
        return ToolkitResourceUtil.generatePatientPortalForm(Context.getService(PatientPortalFormService.class).getPatientPortalForm(formId));
    }
}
