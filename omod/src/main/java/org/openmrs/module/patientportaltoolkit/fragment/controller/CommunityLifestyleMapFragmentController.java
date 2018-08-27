/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.GeneralHistory;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.List;

/**
 * Created by UladKasach on 3/21/2017.
 */
public class CommunityLifestyleMapFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        String maps_APIKEY = Context.getAdministrationService().getGlobalProperty(PatientPortalToolkitConstants.GP_COMMUNITIES_MAPS_APIKEY);
        model.addAttribute("CommunitiesMapsAPIKEY", maps_APIKEY);
        model.addAttribute("CancerCommunityData", null);

        List<GeneralHistory> lstGenHistory= GenerateTreatmentClassesUtil.generateGeneralHistory(Context.getPatientService().getPatientByUuid(Context.getAuthenticatedUser().getPerson().getUuid()));
        List<CancerCommunityResources> cancerComunityData = Context.getService(CancerCommunityResourcesService.class).getCancerCommunityResourcesService();

        if(lstGenHistory != null) {
            String cancerType = lstGenHistory.get(0).getCancerType();
            for(CancerCommunityResources cancerCommunityResource : cancerComunityData) {
                if(cancerCommunityResource.getCancerType().toLowerCase().equals(cancerType.toLowerCase()))
                {
                    model.addAttribute("CancerCommunityData", cancerCommunityResource);
                    break;
                }
            }
        }
    }
}
