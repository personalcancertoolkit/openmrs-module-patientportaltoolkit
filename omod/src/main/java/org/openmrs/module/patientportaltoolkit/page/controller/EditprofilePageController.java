/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.openmrs.module.patientportaltoolkit.api.PatientEmailSubscriptionService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
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
        Context.getService(PatientPortalMiscService.class).logEvent("USER_ACCOUNT_PAGE_VIEWED", null);
        log.info(PPTLogAppender.appendLog("REQUEST_EDITPROFILE_PAGE", pageRequest.getRequest()));

        Person person = Context.getAuthenticatedUser().getPerson();
        model.addAttribute("person", person);
        model.addAttribute("pptutil", new PatientPortalUtil());
        model.addAttribute("contextUser", Context.getAuthenticatedUser());
        if (person.getIsPatient())
            model.addAttribute("personPreferences",
                    Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person));
        else
            model.addAttribute("personPreferences", null);

        PatientEmailSubscription emailSubscription = Context.getService(PatientEmailSubscriptionService.class)
                .getSubscriptionForPerson(person);
        if (emailSubscription != null) {
            model.addAttribute("emailSubscription", emailSubscription);
        } else {
            model.addAttribute("emailSubscription", null);
        }

    }
}
