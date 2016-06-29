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
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PersonService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maurya on 9/21/15.
 */
public class ComposeMessageFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    //PageRequest messagepageRequest;
    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_COMPOSEMESSAGE_FRAGMENT", pageRequest.getRequest()));
        //messagepageRequest=pageRequest;
    }

    public void sendNewMessage(@RequestParam(value = "personUuid", required = true) String personUuid,
                                        @RequestParam(value = "subject", required = true) String subject,
                                        @RequestParam(value = "message", required = true) String message, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SEND_NEW_MESSAGE", servletRequest, "subject:", subject, "message:", message));
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        Person person = personService.getPersonByUuid(personUuid);
        Message newMessage= new Message(subject,message,user.getPerson(),person);
        Context.getService(MessageService.class).saveMessage(newMessage);
        MailHelper.sendMail("New Message", "Hello "+ person.getPersonName()+"you have received a new message on your patient portal module please log into www.personalcancertoolkit.org to view the message", person.getAttribute("Email").toString());
        //log.info("Send New Message to -" + person.getPersonName() + "(id=" + person.getPersonId() + ",uuid=" + person.getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

    public void sendReplyMessage(@RequestParam(value = "personUuid", required = true) String personUuid,
                               @RequestParam(value = "subject", required = true) String subject,
                               @RequestParam(value = "message", required = true) String message,
                                 @RequestParam(value = "parentId", required = true) String parentId, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SEND_REPLY_MESSAGE", servletRequest, "subject:", subject, "message:", message, "parentId:", parentId));
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        Person person = personService.getPersonByUuid(personUuid);
        Message newMessage= new Message(subject,message,user.getPerson(),person);
        newMessage.setParentEntryId(Integer.valueOf(parentId));
        Context.getService(MessageService.class).saveMessage(newMessage);
        //log.info("Send Reply Message to -" + person.getPersonName() + "(id=" + person.getPersonId() + ",uuid=" + person.getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        MailHelper.sendMail("New Message", "Hello"+ person.getPersonName()+"you have received a new message on your patient portal module please log into www.personalcancertoolkit.org to view the message", person.getAttribute("Email").toString());

    }

}
