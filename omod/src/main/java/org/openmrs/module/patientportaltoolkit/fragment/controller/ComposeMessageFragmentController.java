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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maurya on 9/21/15.
 */
public class ComposeMessageFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    // PageRequest messagepageRequest;
    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_COMPOSEMESSAGE_FRAGMENT", pageRequest.getRequest()));
        // messagepageRequest=pageRequest;
    }

    public void sendNewMessage(
            @RequestParam(value = "personUuidStringList", required = true) String personUuidStringList,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "message", required = true) String message, HttpServletRequest servletRequest) {

        String[] personUuidList = personUuidStringList.split(",");

        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService = Context.getPersonService();

        HashSet<String> personUuidMHashSet = new HashSet<>(Arrays.asList(personUuidList));
        for (String personUuid : personUuidMHashSet) {
            try {
                Person person = personService.getPersonByUuid(personUuid);
                Message newMessage = new Message(subject, message, user.getPerson(), person);
                Context.getService(MessageService.class).saveMessage(newMessage);
                String content = "Hello " + person.getPersonName()
                        + "\n\nYou have received a new message on the SPHERE portal. Please login at https://sphere.regenstrief.org to view the message";
                String destinationEmailAddress = person.getAttribute("Email").toString();

                MailHelper.sendMail(
                        "New Message",
                        content,
                        destinationEmailAddress,
                        false);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void sendReplyMessage(@RequestParam(value = "personUuid", required = true) String personUuid,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "message", required = true) String message,
            @RequestParam(value = "parentId", required = true) String parentId, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SEND_REPLY_MESSAGE", servletRequest, "subject:", subject, "message:",
                message, "parentId:", parentId));
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService = Context.getPersonService();

        Person person = personService.getPersonByUuid(personUuid);
        Message newMessage = new Message(subject, message, user.getPerson(), person);
        newMessage.setParentEntryId(Integer.valueOf(parentId));
        Context.getService(MessageService.class).saveMessage(newMessage);
        String content = "Hello" + person.getPersonName()
                + "\n\nYou have received a new message on the SPHERE portal. Please login at https://sphere.regenstrief.org to view the message";
        String destinationEmailAddress = person.getAttribute("Email").toString();

        MailHelper.sendMail(
                "New Message",
                content,
                destinationEmailAddress,
                false);

    }

}
