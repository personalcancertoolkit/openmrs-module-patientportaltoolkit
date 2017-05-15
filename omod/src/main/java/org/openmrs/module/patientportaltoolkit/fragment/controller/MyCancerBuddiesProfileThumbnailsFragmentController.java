package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by maurya on 10/4/16.
 */
public class MyCancerBuddiesProfileThumbnailsFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest, @FragmentParam(value="person") Person person ) {
        log.info(PPTLogAppender.appendLog("REQUEST_MYCANCERBUDDIESTHUMBNAILS_FRAGMENT", pageRequest.getRequest()));

        model.addAttribute("mycancerbuddiespreferences",Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person));
        model.addAttribute("mycancerbuddiespeople",Context.getService(PersonPreferencesService.class).getAllEnrolledPersonPreferences());
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
    }

    public void addRelationshipforFellowPatients(FragmentModel model, @RequestParam(value = "securityLayerType", required = true) String securityLayerType,  @RequestParam(value = "relationshipPersonId", required = true) String relationshipPersonId, @RequestParam(value = "relationshipNote", required = false) String relationshipNote, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("ADD_FellowPatientRelation", servletRequest));

        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();
        //check if person already exists in the system

        Person relatedPerson = Context.getPersonService().getPersonByUuid(relationshipPersonId);

        PatientPortalRelation ppr=new PatientPortalRelation(user.getPerson(),relatedPerson);
        ppr.setRelationType(Context.getPersonService().getRelationshipType(10)); //for fellow patients
        ppr.setShareTypeA(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid(securityLayerType));
        ppr.setShareTypeB(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid("c21b5749-5972-425b-a8dc-15dc8f899a96")); //share posts by default
        ppr.setShareStatus(0);
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.YEAR, 20);
        ppr.setExpireDate(date.getTime());
        if(relationshipNote!=null)
            ppr.setAddConnectionNote(relationshipNote);
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);
    }
}
