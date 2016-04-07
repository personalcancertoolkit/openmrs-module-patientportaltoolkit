package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by maurya on 3/7/16.
 */
public class HomePageController {
    public void controller(PageModel model, @RequestParam(value = "personId", required = false) String personId) {

        if(personId != null && personId != ""){
           Person person = Context.getPersonService().getPersonByUuid(personId);
            model.addAttribute("person", person);
        }
        else {
            model.addAttribute("person", Context.getAuthenticatedUser().getPerson());

        }
        model.addAttribute("pptutil", new PatientPortalUtil());

    }
}
