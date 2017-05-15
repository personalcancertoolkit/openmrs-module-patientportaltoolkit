/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 3/7/16.
 */
public class HomePageController {

    protected final Log log = LogFactory.getLog(getClass());
    protected final String token="REQUEST_PROFILE_PAGE";


    public void controller(PageModel model, @RequestParam(value = "personId", required = false) String personId, PageRequest pageRequest) {


        if(personId != null && personId != ""){

           Person person = Context.getPersonService().getPersonByUuid(personId);
            log.info(PPTLogAppender.appendLog(token, pageRequest.getRequest(),"RequestedUserId:",Context.getUserService().getUsersByPerson(person,false).get(0).getSystemId(),"RequestedUserName:", Context.getUserService().getUsersByPerson(person,false).get(0).getUsername()));
            PatientPortalRelation ppr = Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(person,Context.getAuthenticatedUser().getPerson(),Context.getAuthenticatedUser());
            if(ppr !=null && ppr.getShareStatus() == 1 && (ppr.getShareTypeA().getName().equals(PatientPortalToolkitConstants.CAN_SEE_MEDICAL) || ppr.getShareTypeA().getName().equals(PatientPortalToolkitConstants.CAN_SEE_BOTH))) {
                model.addAttribute("person", person);
                model.addAttribute("securitylevel", 2);
            }
            else if(ppr !=null) {
                model.addAttribute("person", person);
                model.addAttribute("securitylevel", 1);
            }
            else {
                model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
                model.addAttribute("securitylevel", 0);
            }
            if(ppr !=null && !(ppr.getRelationType().getaIsToB().equals("Doctor")))
            model.addAttribute("isACareGiver",1);
            else
                model.addAttribute("isACareGiver",0);
        }
        else {
            model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
            model.addAttribute("securitylevel", 0);
            log.info(PPTLogAppender.appendLog(token, pageRequest.getRequest(), Context.getAuthenticatedUser().getSystemId(), Context.getAuthenticatedUser().getUsername()));
            model.addAttribute("isACareGiver",0);
        }
        model.addAttribute("pptutil", new PatientPortalUtil());
        model.addAttribute("contextUser", Context.getAuthenticatedUser());


    }
}
