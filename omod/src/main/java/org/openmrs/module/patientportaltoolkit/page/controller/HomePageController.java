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
