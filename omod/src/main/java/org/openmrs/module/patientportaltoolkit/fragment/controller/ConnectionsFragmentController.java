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
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.RelationshipType;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.RelationType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 17/06/2015.
 */
public class ConnectionsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model,PageRequest pageRequest) {
        model.addAttribute("relationships", Context.getService(PatientPortalRelationService.class).getPatientPortalRelationByPerson(Context.getAuthenticatedUser().getPerson()));
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("user",Context.getAuthenticatedUser());
        log.info(PPTLogAppender.appendLog("REQUEST_CONNECTIONS_FRAGMENT", pageRequest.getRequest()));

    }

    public void saveRelationshipfromEdit(FragmentModel model, @RequestParam(value = "relationshipId", required = true) String relationshipId,
                                        @RequestParam(value = "personRelationType", required = true) String personRelationType,
                                        @RequestParam(value = "personRelationSecurityLayer", required = true) String personRelationSecurityLayer, PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();

        PatientPortalRelation ppr=Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(relationshipId);
        ppr.setRelationType(personRelationType);
        ppr.setShareType(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid(personRelationSecurityLayer));
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);

        log.info(PPTLogAppender.appendLog("EDIT_RELATIONSHIP", pageRequest.getRequest()));
        //log.info("Edit Relationship/Connection -"+ relationshipId + "Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        //return "Success";
    }

    public void acceptConnectionRequest(FragmentModel model, @RequestParam(value = "relationshipId", required = true) String relationshipId, PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();

        PatientPortalRelation ppr=Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(relationshipId);
        PatientPortalRelation pprNew = new PatientPortalRelation(user.getPerson(),ppr.getPerson());
        pprNew.setShareStatus(1);
        //Remove when adding relationtype to PatientPortalRelation Class
        List<RelationshipType> relationTypes = Context.getPersonService().getAllRelationshipTypes();
        for (RelationshipType rt: relationTypes){
            if(rt.getaIsToB().equals(ppr.getRelationType())){
                pprNew.setRelationType(rt.getbIsToA());
            }
        }

        pprNew.setShareType(Context.getService(SecurityLayerService.class).getSecurityLayerByName(PatientPortalToolkitConstants.CAN_SEE_POSTS));

        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        SimpleDateFormat f = new SimpleDateFormat("dd-MMMM-yyyy");
        //System.out.println(f.format(date.getTime()));
        date.add(Calendar.YEAR,20);
        pprNew.setExpireDate(date.getTime());
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(pprNew);
        ppr.setShareStatus(1);
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);
        log.info(PPTLogAppender.appendLog("ACCEPT_RELATIONSHIP", pageRequest.getRequest(),"Relationship Id:", relationshipId));
        //log.info("Accept Relationship/Connection -" + relationshipId + "Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        //return "Success";
    }

    public void ignoreConnectionRequest(FragmentModel model, @RequestParam(value = "relationshipId", required = true) String relationshipId, PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();
        PatientPortalRelation ppr=Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(relationshipId);
        ppr.setShareStatus(2);
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);
        log.info(PPTLogAppender.appendLog("IGNORE_RELATIONSHIP", pageRequest.getRequest(), "Relationship Id:", relationshipId));
        //log.info("Ignore Relationship/Connection -" + relationshipId + "Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
