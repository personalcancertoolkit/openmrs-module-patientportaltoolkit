/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
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
