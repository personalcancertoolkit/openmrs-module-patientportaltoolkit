/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by maurya on 6/28/16.
 */
public class MailHelper {

    public static void sendMail (String subject, String content, String toEmail) {

        Properties props = new Properties();
        props.put("mail.smtp.host", Context.getAdministrationService().getGlobalProperty("patientportaltoolkit.smtpHost"));
        props.put("mail.smtp.socketFactory.port", Context.getAdministrationService().getGlobalProperty("patientportaltoolkit.smtpPort"));
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", Context.getAdministrationService().getGlobalProperty("patientportaltoolkit.smtpPort"));

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SENDING_EMAIL), Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SENDING_EMAIL_PASSWORD));
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_SENDING_EMAIL)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
