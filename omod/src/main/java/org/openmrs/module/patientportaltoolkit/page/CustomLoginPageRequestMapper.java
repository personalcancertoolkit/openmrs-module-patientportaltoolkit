/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageRequestMapper;
import org.springframework.stereotype.Component;



@Component
public class CustomLoginPageRequestMapper implements PageRequestMapper {
 
   protected final Log log = LogFactory.getLog(getClass());
 
   /**
    * Implementations should call {@link PageRequest#setProviderNameOverride(String)} and
    * {@link PageRequest#setPageNameOverride(String)}, and return true if they want to remap a request,
    * or return false if they didn't remap it.
    *
    * @param request may have its providerNameOverride and pageNameOverride set
    * @return true if this page was mapped (by overriding the provider and/or page), false otherwise
    */
   public boolean mapRequest(PageRequest request) {
      if (request.getProviderName().equals("referenceapplication")) {
         if (request.getPageName().equals("login")) {
            // change to the custom login provided by the module
            request.setProviderNameOverride("patientportaltoolkit");
            request.setPageNameOverride("customLogin");
 
            log.info(request.toString());
                return true;
         }
      }
      // no override happened
      return false;
   }
 
}