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
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Maurya on 23/06/2015.
 */
public class RemoveRelationshipFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_RELATIONSHIPREMOVE_FRAGMENT", pageRequest.getRequest()));
    }

    public void removeRelationship(FragmentModel model,@RequestParam(value = "relationshipId", required = true) String relationshipId, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REMOVE_RELATIONSHIP_FRAGMENT", pageRequest.getRequest()));
        PatientPortalRelationService patientPortalRelationService= Context.getService(PatientPortalRelationService.class);
        patientPortalRelationService.deletePatientPortalRelation(relationshipId, Context.getAuthenticatedUser());
        //log.info("Relation/Connection Removed for relation id -" + relationshipId + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }
}

