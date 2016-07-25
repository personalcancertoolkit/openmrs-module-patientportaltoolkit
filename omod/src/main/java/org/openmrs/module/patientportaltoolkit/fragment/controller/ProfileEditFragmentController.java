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
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by maurya on 2/22/16.
 */
public class ProfileEditFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_PROFILEEDIT_FRAGMENT", pageRequest.getRequest()));
    }
    public void saveProfileEditForm(FragmentModel model,@RequestParam(value = "personId", required = true) int personId,
                                    @RequestParam(value = "givenName", required = true) String givenName,
                                    @RequestParam(value = "familyName", required = true) String familyName,
                                    @RequestParam(value = "gender", required = true) String gender,
                                    @RequestParam(value = "birthDate", required = true) String birthDate, HttpServletRequest servletRequest)  {

        log.info(PPTLogAppender.appendLog("SAVE_PROFILEEDIT", servletRequest, "personId:", personId+"","givenName:",givenName, "familyName", familyName, "gender", gender, "birthDate", birthDate));
        Person person = Context.getPersonService().getPerson(personId);
        PersonName personName = new PersonName();
        personName.setGivenName(givenName);
        personName.setFamilyName(familyName);
        Set<PersonName> personNames = person.getNames();

        boolean personNameExists = false;
        for (PersonName pn : personNames) {
            if (pn.equalsContent(personName))
                personNameExists = true;
        }
        if (!personNameExists) {
            for (PersonName pn : personNames) {
                if (pn.getPreferred())
                    pn.setPreferred(false);
            }
            personName.setPreferred(true);
            personNames.add(personName);
        }

        person.setNames(personNames);

            DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
            try {
                person.setBirthdate(df.parse(birthDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        if (gender != null)
            person.setGender(gender);
        Context.getPersonService().savePerson(person);
        //log.info("Profile Details saved for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

}
