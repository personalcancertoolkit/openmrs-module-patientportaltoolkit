package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by maurya on 9/16/16.
 */
public class EditprofilePageController {

    protected final Log log = LogFactory.getLog(getClass());
    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EDITPROFILE_PAGE", pageRequest.getRequest()));
        Person person=Context.getAuthenticatedUser().getPerson();
        model.addAttribute("person", person);
        model.addAttribute("pptutil",new PatientPortalUtil());
        model.addAttribute("contextUser", Context.getAuthenticatedUser());
        if (person.getIsPatient())
            model.addAttribute("personPreferences",Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person));
        else
            model.addAttribute("personPreferences",null);
    }
}
