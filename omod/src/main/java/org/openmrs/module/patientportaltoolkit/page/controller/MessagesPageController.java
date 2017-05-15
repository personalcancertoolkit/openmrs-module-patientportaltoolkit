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
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 9/7/15.
 */
public class MessagesPageController {

    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model,PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_MESSAGES_PAGE", pageRequest.getRequest()));
        //log.info("Messages Page Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")");
        Person person = null;
        model.addAttribute("pptutil",new PatientPortalUtil());
        person= Context.getPersonService().getPersonByUuid(Context.getAuthenticatedUser().getPerson().getUuid());
        model.addAttribute("messages", Context.getService(MessageService.class).getMessageForPerson(person,true));
        model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
        model.addAttribute("contextUser", Context.getAuthenticatedUser());
    }
}
