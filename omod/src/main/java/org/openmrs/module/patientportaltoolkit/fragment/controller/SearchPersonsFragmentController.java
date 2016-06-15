/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

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

    public String getPersonsFromSearch(FragmentModel model,@RequestParam(value = "searchQuery", required = true) String searchQuery,UiUtils ui, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("GET_PERSONS_FROMSEARCH", pageRequest.getRequest(), "searchQuery:", searchQuery));
        List<Person> persons =Context.getPersonService().getPeople(searchQuery, false);
       return "json";
    }

}
