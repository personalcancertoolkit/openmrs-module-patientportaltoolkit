package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
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
    public Object getPatientPortalEncounters()
    {
        return ToolkitResourceUtil.generatePatientPortalForms(Context.getService(PatientPortalFormService.class).getAllPatientPortalForms());
    }
}
