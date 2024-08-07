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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 7/13/16.
 */
public class HeaderForAppFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {

        User authenticatedUser = Context.getAuthenticatedUser();

        List<PatientPortalRelation> relationships = Context.getService(PatientPortalRelationService.class)
                .getPatientPortalRelationByPerson(authenticatedUser.getPerson());

        int numberOfConnectionRequests = 0;
        Person authenticatedUserPerson = authenticatedUser.getPerson();
        for (PatientPortalRelation relationship : relationships) {
            if (
            // An incoming request from a patient to the authenticated user patient
            // that has not been responded to
            (authenticatedUserPerson.getIsPatient()
                    && relationship.getRelatedPerson().equals(authenticatedUserPerson)
                    && relationship.getPerson().getIsPatient()
                    && relationship.getShareStatus() == PatientPortalRelation.SHARE_STATUS_PENDING)
                    ||
                    // A request where the authenticated user is not a patient and the
                    // request has not been responded to
                    (!authenticatedUserPerson.getIsPatient()
                            && relationship.getShareStatus() == PatientPortalRelation.SHARE_STATUS_PENDING)) {
                numberOfConnectionRequests++;
            }
        }

        int numberOfUnreadMessages = Context.getService(MessageService.class)
                .getUnreadMessagesForPerson(authenticatedUserPerson, true).size();

        model.addAttribute("numberOfConnectionRequests", numberOfConnectionRequests);
        model.addAttribute("numberOfUnreadMessages", numberOfUnreadMessages);
        model.addAttribute("username", authenticatedUser.getUsername());
        model.addAttribute("contextUser", authenticatedUser);

        log.info(PPTLogAppender.appendLog("REQUEST_NAVIGATION_FRAGMENT", pageRequest.getRequest()));

    }

}
