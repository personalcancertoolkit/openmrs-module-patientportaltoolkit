/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.web.rest.v1_0.controller;

import org.openmrs.module.patientportaltoolkit.web.rest.PatientPortalToolkitRestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maurya on 9/9/16.
 *
 */
@Controller
@RequestMapping("/rest/" + PatientPortalToolkitRestConstants.REST_NAMESPACE)
public class JournalEntryResourceController extends MainResourceController {
    /**
     * @see MainResourceController#getNamespace()
     */
    @Override
    public String getNamespace() {
        return PatientPortalToolkitRestConstants.REST_NAMESPACE;
    }

}
