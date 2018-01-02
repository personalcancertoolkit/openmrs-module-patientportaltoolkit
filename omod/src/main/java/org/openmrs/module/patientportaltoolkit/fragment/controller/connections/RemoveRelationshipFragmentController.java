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
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Maurya on 23/06/2015.
 */
public class RemoveRelationshipFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_RELATIONSHIPREMOVE_FRAGMENT", pageRequest.getRequest()));
    }

    public void removeRelationship(FragmentModel model,@RequestParam(value = "relationshipId", required = true) String relationshipId, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("REMOVE_RELATIONSHIP_FRAGMENT", servletRequest));
        PatientPortalRelationService patientPortalRelationService= Context.getService(PatientPortalRelationService.class);
        patientPortalRelationService.deletePatientPortalRelation(relationshipId, Context.getAuthenticatedUser());
        //log.info("Relation/Connection Removed for relation id -" + relationshipId + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}
