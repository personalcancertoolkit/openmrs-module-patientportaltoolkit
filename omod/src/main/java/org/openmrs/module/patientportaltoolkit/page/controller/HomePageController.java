package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 3/7/16.
 */
public class HomePageController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, @RequestParam(value = "personId", required = false) String personId) {


        if(personId != null && personId != ""){

           Person person = Context.getPersonService().getPersonByUuid(personId);
            log.info( "Profile Page of -"+ person.getPersonName() + "(id="+person.getPersonId()+",uuid="+person.getUuid()+")"+" Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")" );
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
            log.info("Profile Page of -" + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")"+ " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id="+Context.getAuthenticatedUser().getPerson().getPersonId()+",uuid="+Context.getAuthenticatedUser().getPerson().getUuid()+")");
        }
        model.addAttribute("pptutil", new PatientPortalUtil());

    }
}
