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
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PatientEmailSubscriptionService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        PersonService personService = Context.getPersonService();
        HashSet<String> personUuidMHashSet = new HashSet<>(Arrays.asList(personUuidList));

        // If messages are being sent to more than one recipient, this is a broadcast
        // message, and as of 2024, only admins are allowed to send broadcasts
        boolean messageIsABroadcast = personUuidMHashSet.size() > 1;

        for (String personUuid : personUuidMHashSet) {
            Person person = personService.getPersonByUuid(personUuid);
            try {

                Message newMessage = new Message(subject, message, user.getPerson(), person);
                Context.getService(MessageService.class).saveMessage(newMessage);

                String content = "Hello " + person.getPersonName()
                        + "\n\nYou have received a new message on the SPHERE portal. Please login at https://sphere.regenstrief.org to view the message";
                String destinationEmailAddress = person.getAttribute("Email").toString();

                // If the message is a broadcast, check if the patient is subscribed to receive
                // broadcast emails
                if (messageIsABroadcast) {
                    PatientEmailSubscription subscription = Context.getService(PatientEmailSubscriptionService.class)
                            .getSubscriptionForPerson(person);
                    if (subscription != null && subscription.getBroadcastEmail()) {
                        MailHelper.sendMail(
                                "New Message",
                                content,
                                destinationEmailAddress,
                                false);
                    }
                }
                // If the message is not a broadcast, send the email without checking
                else {
                    MailHelper.sendMail(
                            "New Message",
                            content,
                            destinationEmailAddress,
                            false);
                }

            } catch (Exception e) {
                System.out.println("Unable to send SPHERE Message to " + person.getPersonName()
                        + ". They may not have an email address");
            }
        }
    }

    public void sendReplyMessage(@RequestParam(value = "parentUuid", required = true) String parentUuid,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "message", required = true) String message, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SEND_REPLY_MESSAGE", servletRequest, "subject:", subject, "message:",
                message));

        User user = Context.getAuthenticatedUser();
        Person person = user.getPerson();

        // Get original message
        MessageService messageService = Context.getService(MessageService.class);
        Message originalMessage = messageService.getMessage(parentUuid);

        if (originalMessage != null) {
            Person originalSender = originalMessage.getSender();
            Person originalReceiver = originalMessage.getReceiver();

            Person replyReceiver = (person.equals(originalReceiver) ? originalSender : originalReceiver);

            Message newMessage = new Message(subject, message, person, replyReceiver);
            newMessage.setParentEntryId(originalMessage.getEntryId());

            messageService.saveMessage(newMessage);

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

    public void markMessageAsRead(@RequestParam(value = "messageUuid", required = true) String messageUuid,
            HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("MARK_MESSAGE_AS_READ", servletRequest, "messageUuid:", messageUuid));

        MessageService messageService = Context.getService(MessageService.class);
        User user = Context.getAuthenticatedUser();
        Person person = user.getPerson();

        Message message = messageService.getMessage(messageUuid);
        Date todayDate = new Date();

        if (message != null) {
            if (!message.hasBeenViewedByReceiver() && message.getReceiver().equals(person)) {
                message.setReceiverViewedAt(todayDate);
                messageService.saveMessage(message);
            }

            for (Message childMessage : message.getChildren()) {
                if (!childMessage.hasBeenViewedByReceiver() && childMessage.getReceiver().equals(person)) {
                    childMessage.setReceiverViewedAt(todayDate);
                    messageService.saveMessage(childMessage);
                }
            }
        }
    }

}
