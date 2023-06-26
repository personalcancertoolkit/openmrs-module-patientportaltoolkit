package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PasswordChangeRequest;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PasswordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by srikumma on 6/1/17.
 */
@Controller
public class PatientPortalToolkitController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/patientportaltoolkit/sendForgotPasswordEmail/{emailId:.+}")
    @ResponseBody
    public void getSendForgotPasswordEmail(@PathVariable("emailId") String emailId, HttpServletRequest httpRequest) {
        System.out.println("In Forgot Password Email method");
        try {
            org.openmrs.api.PersonService ps = Context.getPersonService();
            List<Person> people = ps.getPeople(emailId, true);
            Person person = new Person();
            for (Person p : people) {
                if (p.getAttribute("Email").getValue().equals(emailId))
                    person = p;
            }
            if (person != null) {
                PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(new Date(),
                        UUID.randomUUID().toString(), Context.getUserService().getUsersByPerson(person, false).get(0));
                PatientPortalMiscService ppms = Context.getService(PatientPortalMiscService.class);
                PasswordChangeRequest savedPasswordChangeRequest = ppms
                        .savePasswordChangeRequest(passwordChangeRequest);
                String sendingRequestURL = "https://sphere.regenstrief.org/openmrs/ws/patientportaltoolkit/";
                // sendingReuqestURL = sendingReuqestURL.split("sendForgotPasswordEmail")[0];
                sendingRequestURL = sendingRequestURL + "confirmForgotPasswordEmail/"
                        + savedPasswordChangeRequest.getUuid() + "/" + emailId;
                System.out.println("Before Sending request to mailhelper");
                System.out.println("Name= " + person.getPersonName() + "sending request uri = " + sendingRequestURL
                        + "emailid= " + emailId);
                MailHelper.sendMail("Sphere - Forgot Password", "Hello " + person.getPersonName()
                        + ", \n\nWe have received a password change request for your SPHERE personal cancer toolkit account.\n\n"
                        +
                        "If this was you, please click on this LINK to change your password -" + sendingRequestURL
                        + "\n\n(If the link on the line above is not clickable, you can copy it into your browser)\n\nIf you did not request a password change request, do not click on the link above. Please notify the study team at sphere@iupui.edu.",
                        emailId);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/patientportaltoolkit/confirmForgotPasswordEmail/{uuid}/{emailId:.+}")
    @ResponseBody
    public void getConfirmForgotPasswordEmail(@PathVariable("uuid") String uuid,
            @PathVariable("emailId") String emailId, HttpServletRequest httpRequest,
            HttpServletResponse httpServletResponse)
            throws Exception {
        PatientPortalMiscService ppms = Context.getService(PatientPortalMiscService.class);
        PasswordChangeRequest pcr = ppms.getPasswordChangeRequests(uuid);
        UserService us = Context.getUserService();
        if (pcr == null) {

        } else {
            if ((Days.daysBetween(new DateTime(pcr.getDateTime()), DateTime.now()).getDays() < 1) && !pcr.isRetired()) {
                org.openmrs.api.PersonService ps = Context.getPersonService();
                Person forgotPasswordPerson = null;
                List<Person> people = ps.getPeople(emailId, false);
                for (Person p : people) {
                    if (p.getAttribute("Email").getValue().equals(emailId))
                        forgotPasswordPerson = p;
                }
                User u = us.getUsersByPerson(forgotPasswordPerson, false).get(0);
                String newPassword = String.valueOf(PasswordUtil.getNewPassword());
                Context.getUserService().changePassword(u, newPassword);
                pcr.setRetired(true);

                if (forgotPasswordPerson != null) {
                    MailHelper.sendMail("Sphere - Forgot Password", "Hello " + forgotPasswordPerson.getPersonName()
                            + "\n\nYour password has been changed based on your request. Your new credentials are:\n\nUsername: "
                            + u.getUsername() + "\n Password: " + newPassword
                            + "\n\nOnce you log in, please change your password to something you can easily remember by clicking on your username in the top banner and then clicking on the \"Change Password\" button."
                            + "\n\nIf this request was not made by you, please reply back to this email to report this issue.",
                            emailId);
                }
            } else {
                pcr.setRetired(true);
            }
            ppms.savePasswordChangeRequest(pcr);
        }
        String sendingRequestURL = httpRequest.getRequestURL().toString();
        sendingRequestURL = sendingRequestURL.split("ws")[0] + "login.htm?passwordChangeRequest=Success";
        httpServletResponse.sendRedirect(sendingRequestURL);

    }

    @RequestMapping(value = "/patientportaltoolkit/newPatientUser/{emailId:.+}")
    @ResponseBody
    public void changePasswordEmail(@PathVariable("emailId") String emailId)
            throws Exception {
        UserService us = Context.getUserService();
        org.openmrs.api.PersonService ps = Context.getPersonService();
        Person forgotPasswordPerson = null;
        List<Person> people = ps.getPeople(emailId, false);
        for (Person p : people) {
            if (p.getAttribute("Email").getValue().equals(emailId))
                forgotPasswordPerson = p;
        }
        User u = us.getUsersByPerson(forgotPasswordPerson, false).get(0);
        String newPassword = String.valueOf(PasswordUtil.getNewPassword());
        Context.getUserService().changePassword(u, newPassword);

        if (forgotPasswordPerson != null) {
            MailHelper.sendMail("Sphere - New Account", "Hello " + forgotPasswordPerson.getPersonName()
                    + "\n\nAn account has been created in the SPHERE portal - https://sphere.regenstrief.org/. Your login credentials are:\n\nUsername: "
                    + u.getUsername() + "\nPassword: " + newPassword
                    + "\n\nOnce you log in, please change your password to something you can easily remember by clicking on your username in the top banner and then clicking on the \"Change Password\" button."
                    + "\n\nIf this request was not made by you, please reply back to this email to report this issue.",
                    emailId);
        }
    }

    @RequestMapping(value = "/patientportaltoolkit/logEvent")
    @ResponseBody
    public String logEvent(@RequestParam(value = "event", required = true) String event,
            @RequestParam(value = "data", required = false) String data) {

        PatientPortalMiscService ppmService = Context.getService(PatientPortalMiscService.class);
        // to set jquery empty strings as null when saving JSON in MySQL
        if (data.isEmpty())
            data = null;
        ppmService.logEvent(event, data);
        return "Logged event: " + event + " data: " + data;
    }

    @RequestMapping(value = "/patientportaltoolkit/createinitialpreferences/{personUUID}")
    @ResponseBody
    public String createInitialPreferences(@PathVariable("personUUID") String personUUID) {
        Person p = Context.getPersonService().getPersonByUuid(personUUID);
        PersonPreferences personPreferences = new PersonPreferences();
        personPreferences.setPerson(p);
        personPreferences.setMyCancerBuddies(false);
        personPreferences.setMyCancerBuddiesDescription("Hello, I would like to be a part of My Cancer Buddies");
        personPreferences.setMyCancerBuddiesName(p.getGivenName() + p.getFamilyName());
        Context.getService(PersonPreferencesService.class).savePersonPreferences(personPreferences);
        return "Initial Preferences Successfully Created";
    }
}
