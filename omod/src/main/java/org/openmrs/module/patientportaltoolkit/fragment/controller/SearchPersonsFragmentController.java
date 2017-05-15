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
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Maurya on 24/06/2015.
 */
public class SearchPersonsFragmentController {
    protected final Log log = LogFactory.getLog(getClass());
    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SEARCHPERSONS_FRAGMENT", pageRequest.getRequest()));
        model.addAttribute("searchPersons",null);
    }

    public String getPersonsFromSearch(FragmentModel model,@RequestParam(value = "searchQuery", required = true) String searchQuery,UiUtils ui, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("GET_PERSONS_FROMSEARCH", servletRequest, "searchQuery:", searchQuery));
        List<Person> persons =Context.getPersonService().getPeople(searchQuery, false);
       return "json";
    }

}
