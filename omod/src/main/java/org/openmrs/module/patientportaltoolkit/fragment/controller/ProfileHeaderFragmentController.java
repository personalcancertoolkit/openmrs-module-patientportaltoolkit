package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by Maurya on 19/06/2015.
 */
public class ProfileHeaderFragmentController {

    public void controller(PageModel model) {
        model.addAttribute("person",Context.getAuthenticatedUser().getPerson());
    }
}
