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
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.notification.MessageException;
import org.openmrs.notification.MessageService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Created by maurya on 2/4/16.
 */
public class FeedbackFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SEND_FEEDBACK", pageRequest.getRequest()));
    }

    public void sendFeedback(FragmentModel model, @RequestParam(value = "feedbackMessage", required = true) String feedback, HttpServletRequest servletRequest) {


       /* String to = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL);
        ;

        String from = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_FROM_EMAIL);


        try {
            Context.getService(MessageService.class).sendMessage(to,from,"Contact Form Results", feedback);
        } catch (MessageException e) {
            e.printStackTrace();
        }*/

        log.info(PPTLogAppender.appendLog("SEND_FEEDBACK", servletRequest, "Feedback:", feedback));
       /* Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("patientportaltoolkit@gmail.com", "OpenMRS12#");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_FROM_EMAIL)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL)));
            message.setSubject("Testing Subject");
            message.setText(feedback);

            Transport.send(message);

            System.out.println("Done");

            //log.info("Feedback/Contact message sent by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/
        MailHelper.sendMail("Testing Subject",feedback, Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL));

    }
}
