/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller.connections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maurya on 17/06/2015.
 */
public class ConnectionsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        model.addAttribute("relationships", Context.getService(PatientPortalRelationService.class)
                .getPatientPortalRelationByPerson(Context.getAuthenticatedUser().getPerson()));
        model.addAttribute("securityLayers", Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("user", Context.getAuthenticatedUser());
        log.info(PPTLogAppender.appendLog("REQUEST_CONNECTIONS_FRAGMENT", pageRequest.getRequest()));

    }

    public void saveRelationshipfromEdit(FragmentModel model,
            @RequestParam(value = "relationshipId", required = true) String relationshipId,
            @RequestParam(value = "personRelationType", required = true) Integer personRelationType,
            @RequestParam(value = "personRelationSecurityLayer", required = true) String personRelationSecurityLayer,
            HttpServletRequest servletRequest) {
        User user = Context.getAuthenticatedUser();

        Person personGettingAccess = null;
        PatientPortalRelationService pprService = Context.getService(PatientPortalRelationService.class);

        PatientPortalRelation ppr = pprService.getPatientPortalRelation(relationshipId);
        ppr.setRelationType(Context.getPersonService().getRelationshipType(personRelationType));

        List<String> shareTypesList = Arrays.asList(personRelationSecurityLayer.split(","));
        SecurityLayerService securityLayerService = Context.getService(SecurityLayerService.class);
        List<SecurityLayer> shareTypes = new ArrayList<>();
        if (ppr.getPerson().equals(user.getPerson()))
            personGettingAccess = ppr.getRelatedPerson();
        else
            personGettingAccess = ppr.getPerson();
        for (String s : shareTypesList) {
            shareTypes.add(securityLayerService.getSecurityLayerByUuid(s));
        }

        pprService.savePatientPortalRelation(ppr);
        pprService.saveShareTypes(user.getPerson(), personGettingAccess, shareTypes);

        log.info(PPTLogAppender.appendLog("EDIT_RELATIONSHIP", servletRequest));
    }

    public void acceptConnectionRequest(FragmentModel model,
            @RequestParam(value = "relationshipId", required = true) String relationshipId,
            HttpServletRequest servletRequest) {
        User user = Context.getAuthenticatedUser();

        PatientPortalRelation ppr = Context.getService(PatientPortalRelationService.class)
                .getPatientPortalRelation(relationshipId);
        ppr.setRetired(false);

        Person personWhoRequestedConnection = ppr.getPerson();
        String destinationEmailAddress = personWhoRequestedConnection.getAttribute("Email").toString();

        Person authenticatedUserPerson = user.getPerson();
        String message = authenticatedUserPerson.getPersonName()
                + " has accepted your SPHERE connection request. Login to SPHERE at https://sphere.regenstrief.org to view their profile";

        boolean successfullySent = MailHelper.sendMail(
                "SPHERE Connection Request Accepted",
                message,
                destinationEmailAddress,
                false);

        if (!successfullySent) {
            System.out.println(
                    "Unable to send connection request acceptance email to personID "
                            + personWhoRequestedConnection.getId() + " from personID " + authenticatedUserPerson.getId()
                            + " in ConnectionsFragementController:acceptConnectionRequest");
        }

        ppr.setShareStatus(1);
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);

        log.info(PPTLogAppender.appendLog("ACCEPT_RELATIONSHIP", servletRequest, "Relationship Id:", relationshipId));
        Context.getService(PatientPortalMiscService.class).logEvent("ACCEPTED_CONNECTION_REQUEST",
                "{\"relationshipPersonId\": \"" + relationshipId + "\"}");
    }

    public void ignoreConnectionRequest(FragmentModel model,
            @RequestParam(value = "relationshipId", required = true) String relationshipId,
            HttpServletRequest servletRequest) {

        PatientPortalRelation ppr = Context.getService(PatientPortalRelationService.class)
                .getPatientPortalRelation(relationshipId);
        ppr.setShareStatus(2);
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);
        Context.getService(PatientPortalRelationService.class).deletePatientPortalRelation(relationshipId,
                Context.getAuthenticatedUser());
        log.info(PPTLogAppender.appendLog("IGNORE_RELATIONSHIP", servletRequest, "Relationship Id:", relationshipId));
        Context.getService(PatientPortalMiscService.class).logEvent("IGNORED_CONNECTION_REQUEST",
                "{\"relationshipPersonId\": \"" + relationshipId + "\"}");
    }

}
