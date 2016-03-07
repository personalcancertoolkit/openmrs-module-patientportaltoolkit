package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maurya on 19/06/2015.
 */
public class ProfileHeaderFragmentController {

    public void controller(PageModel model) {
        model.addAttribute("person",Context.getAuthenticatedUser().getPerson());
    }
}
