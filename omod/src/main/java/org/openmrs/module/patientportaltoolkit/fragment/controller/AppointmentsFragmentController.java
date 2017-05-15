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

package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;


/**
 * Created by maurya on 10/29/15.
 */
public class AppointmentsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        /*
        Person person = (Person) model.get("person");
    
        Patient patient= Context.getPatientService().getPatient(person.getId());
        GuidelineConditionSet guidelineConditionSet = Context.getService(ReminderService.class).generateGuidelineConditionSet(patient);
        model.addAttribute("guidelines", guidelineConditionSet);
        
        */
        log.info(PPTLogAppender.appendLog("REQUEST_APPOINTMENTS_FRAGMENT", pageRequest.getRequest()));
    }
    
    
    

}
