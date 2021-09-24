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
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
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

    @RequestMapping( value = "/patientportaltoolkit/sendForgotPasswordEmail/{emailId:.+}")
    @ResponseBody
    public void getSendForgotPasswordEmail(@PathVariable( "emailId" ) String emailId, HttpServletRequest httpRequest)
            throws Exception
    {
        org.openmrs.api.PersonService ps = Context.getPersonService();
        Person forgotPasswordPerson=null;
        List<Person> people = ps.getPeople(emailId,false);
        for (Person p:people){
            if (p.getAttribute("Email").getValue().equals(emailId))
                forgotPasswordPerson=p;
        }
        PasswordChangeRequest passwordChangeRequest =new PasswordChangeRequest(new Date(), UUID.randomUUID().toString(),Context.getUserService().getUsersByPerson(forgotPasswordPerson,false).get(0));
        PatientPortalMiscService ppms = Context.getService(PatientPortalMiscService.class);
        PasswordChangeRequest savedPasswordChangeRequest=ppms.savePasswordChangeRequest(passwordChangeRequest);
        String sendingReuqestURL=httpRequest.getRequestURL().toString();
        sendingReuqestURL=sendingReuqestURL.split("sendForgotPasswordEmail")[0];
        sendingReuqestURL=sendingReuqestURL+"confirmForgotPasswordEmail/"+savedPasswordChangeRequest.getUuid()+"/"+emailId;
        MailHelper.sendMail("Forgot Password", "Hello"+ forgotPasswordPerson.getPersonName()+"\nYou have requested a password change if yes, Please click on this link - "+sendingReuqestURL+" to change your password\n If this request was not made by you, please reply back to this email to report this issue.", emailId);

    }

    @RequestMapping( value = "/patientportaltoolkit/confirmForgotPasswordEmail/{uuid}/{emailId:.+}")
    @ResponseBody
    public void getConfirmForgotPasswordEmail(@PathVariable( "uuid" ) String uuid, @PathVariable( "emailId" ) String emailId, HttpServletRequest httpRequest, HttpServletResponse httpServletResponse)
            throws Exception
    {
        PatientPortalMiscService ppms = Context.getService(PatientPortalMiscService.class);
        PasswordChangeRequest pcr= ppms.getPasswordChangeRequests(uuid);
        UserService us=Context.getUserService();
        if (pcr==null){

        }
        else{
           if((Days.daysBetween(new DateTime(pcr.getDateTime()), DateTime.now()).getDays()<1) && !pcr.isRetired()){
               org.openmrs.api.PersonService ps = Context.getPersonService();
               Person forgotPasswordPerson=null;
               List<Person> people = ps.getPeople(emailId,false);
               for (Person p:people){
                   if (p.getAttribute("Email").getValue().equals(emailId))
                       forgotPasswordPerson=p;
               }
               User u=us.getUsersByPerson(forgotPasswordPerson,false).get(0);
               String newPassword = String.valueOf(PasswordUtil.getNewPassword());
               Context.getUserService().changePassword(u,newPassword);
               pcr.setRetired(true);
               MailHelper.sendMail("Forgot Password", "Hello"+ forgotPasswordPerson.getPersonName()+"\nYour password has been changed based on your request\n\n"+ newPassword +" \n\nIf this request was not made by you, please reply back to this email to report this issue.", emailId);
           }
           else{
               pcr.setRetired(true);
           }
            ppms.savePasswordChangeRequest(pcr);
        }
        String sendingReuqestURL=httpRequest.getRequestURL().toString();
        sendingReuqestURL=sendingReuqestURL.split("ws")[0]+"login.htm?passwordChangeRequest=Success";
        httpServletResponse.sendRedirect(sendingReuqestURL);

    }
    @RequestMapping( value = "/patientportaltoolkit/logEvent")
    @ResponseBody
    public String getHasAccess(@RequestParam(value = "event", required = true) String event,
                                @RequestParam(value = "data", required = false) String data) {

        PatientPortalMiscService ppmService=Context.getService(PatientPortalMiscService.class);
        //to set jquery empty strings as null when saving JSON in MySQL
        if (data.isEmpty())
            data=null;
        ppmService.logEvent(event,data);
        return "success";
    }
}
