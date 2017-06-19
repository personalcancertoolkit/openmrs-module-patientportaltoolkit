package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Created by srikumma on 5/24/17.
 */
public class ForgotPasswordFragmentController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(FragmentModel model, PageRequest pageRequest){
//        log.info(PPTLogAppender.appendLog("REQUEST_FORGOT_PASSWORD_FRAGMENT", pageRequest.getRequest()));
    }

    public void sendForgotPasswordEmail(FragmentModel model, @RequestParam(value = "emailId", required = false) String emailId, HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("REQUEST_FORGOT_PASSWORD_EMAIL_SENT", servletRequest, "emailid:", emailId));
        org.openmrs.api.PersonService ps = Context.getPersonService();
        ps.getPersonAttributeTypeByName("Email");
        List<Person> people = ps.getPeople(emailId,true);
        //ps.getPerson();
        MailHelper.sendMail("Forgot Password", "Hello"+ people.get(0).getPersonName()+"\nyou have received a new message on your patient portal module please log into www.personalcancertoolkit.org to view the message", people.get(0).getAttribute("Email").toString());

    }

    }
