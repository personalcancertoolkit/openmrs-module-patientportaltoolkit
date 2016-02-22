package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.notification.MessageException;
import org.openmrs.notification.MessageService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by maurya on 2/4/16.
 */
public class FeedbackFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void sendFeedback(FragmentModel model, @RequestParam(value = "feedbackMessage", required = true) String feedback) {


       /* String to = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_EMAIL);
        ;

        String from = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_CONTACT_US_FROM_EMAIL);


        try {
            Context.getService(MessageService.class).sendMessage(to,from,"Contact Form Results", feedback);
        } catch (MessageException e) {
            e.printStackTrace();
        }*/

        Properties props = new Properties();
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

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
