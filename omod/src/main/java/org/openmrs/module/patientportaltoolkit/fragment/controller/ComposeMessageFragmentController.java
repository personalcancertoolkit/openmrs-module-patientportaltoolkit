package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.PersonService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 9/21/15.
 */
public class ComposeMessageFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void sendNewMessage(@RequestParam(value = "personUuid", required = true) String personUuid,
                                        @RequestParam(value = "subject", required = true) String subject,
                                        @RequestParam(value = "message", required = true) String message) {
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        Message newMessage= new Message(subject,message,user.getPerson(),personService.getPersonByUuid(personUuid));
        Context.getService(MessageService.class).saveMessage(newMessage);
    }

    public void sendReplyMessage(@RequestParam(value = "personUuid", required = true) String personUuid,
                               @RequestParam(value = "subject", required = true) String subject,
                               @RequestParam(value = "message", required = true) String message,
                                 @RequestParam(value = "parentId", required = true) String parentId) {
        User user = Context.getAuthenticatedUser();
        org.openmrs.api.PersonService personService=Context.getPersonService();
        Message newMessage= new Message(subject,message,user.getPerson(),personService.getPersonByUuid(personUuid));
        newMessage.setParentEntryId(Integer.valueOf(parentId));
        Context.getService(MessageService.class).saveMessage(newMessage);
    }

}
