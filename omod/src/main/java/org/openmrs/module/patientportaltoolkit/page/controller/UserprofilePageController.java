package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by maurya on 1/27/16.
 */
public class UserprofilePageController {

    public void controller(PageModel model) {
        model.addAttribute("person", Context.getAuthenticatedUser().getPerson());
    }

}
