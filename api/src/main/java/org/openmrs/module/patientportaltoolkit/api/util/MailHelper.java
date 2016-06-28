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
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

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
