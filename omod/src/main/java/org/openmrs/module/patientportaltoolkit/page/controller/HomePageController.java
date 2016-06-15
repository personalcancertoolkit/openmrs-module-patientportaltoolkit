package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 3/7/16.
 */
public class HomePageController {

    protected final Log log = LogFactory.getLog(getClass());
    protected final String token="REQUEST_PROFILE_PAGE";


    public void controller(PageModel model, @RequestParam(value = "personId", required = false) String personId, PageRequest pageRequest) {


        if(personId != null && personId != ""){

           Person person = Context.getPersonService().getPersonByUuid(personId);
            log.info(PPTLogAppender.appendLog(token, pageRequest.getRequest(),"RequestedUserId:",Context.getUserService().getUsersByPerson(person,false).get(0).getSystemId(),"RequestedUserName:", Context.getUserService().getUsersByPerson(person,false).get(0).getUsername()));
            PatientPortalRelation ppr = Context.getService(PatientPortalRelationService.class).getPatientPortalRelation(person,Context.getAuthenticatedUser().getPerson(),Context.getAuthenticatedUser());
            if(ppr !=null && ppr.getShareStatus() == 1 && (ppr.getShareType().getName().equals(PatientPortalToolkitConstants.CAN_SEE_MEDICAL) || ppr.getShareType().getName().equals(PatientPortalToolkitConstants.CAN_SEE_BOTH))) {
                model.addAttribute("person", person);
                model.addAttribute("securitylevel", 2);
            }
            else {
                model.addAttribute("person", person);
                model.addAttribute("securitylevel", 1);
            }
        }
        else {
            model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
            model.addAttribute("securitylevel", 0);
            log.info(PPTLogAppender.appendLog(token,pageRequest.getRequest(),Context.getAuthenticatedUser().getSystemId(), Context.getAuthenticatedUser().getUsername()));
            }
        model.addAttribute("pptutil", new PatientPortalUtil());

    }
}
