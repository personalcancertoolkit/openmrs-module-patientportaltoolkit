package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by maurya on 3/7/16.
 */
public class CommunitiesPageController {
    public void controller(PageModel model) {

        model.addAttribute("pptutil",new PatientPortalUtil());

    }
}
