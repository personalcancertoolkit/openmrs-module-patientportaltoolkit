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
import org.openmrs.RelationshipType;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by Maurya on 22/06/2015.
 */
public class RelationshipSelectFragmentController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(FragmentModel model,  @FragmentParam(value="selectedRelationShip", required=false) String relationshipType,@FragmentParam(value="parentForm", required=false) String parentForm, PageRequest pageRequest) {
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("selectedRelationShiptype", relationshipType);
        model.addAttribute("parentForm", parentForm);
        log.info(PPTLogAppender.appendLog("REQUEST_RELATIONSHIPSELECT_FRAGMENT", pageRequest.getRequest()));

    }
}
