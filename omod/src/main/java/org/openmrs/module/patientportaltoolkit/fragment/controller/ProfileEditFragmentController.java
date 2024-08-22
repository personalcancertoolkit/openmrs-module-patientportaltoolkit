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
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.openmrs.module.patientportaltoolkit.api.PatientEmailSubscriptionService;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * Created by maurya on 2/22/16.
 */
public class ProfileEditFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_PROFILEEDIT_FRAGMENT", pageRequest.getRequest()));
    }

    public void saveProfileEditForm(FragmentModel model,
            @RequestParam(value = "personId", required = true) int personId,
            @RequestParam(value = "givenName", required = true) String givenName,
            @RequestParam(value = "familyName", required = true) String familyName,
            @RequestParam(value = "gender", required = true) String gender,
            @RequestParam(value = "birthDate", required = true) String birthDate,
            @RequestParam(value = "myCancerBuddies", required = true) String myCancerBuddies,
            @RequestParam(value = "getsBroadcastEmails", required = true) String getsBroadcastEmails,
            @RequestParam(value = "getsAppointmentReminderEmails", required = true) String getsAppointmentReminderEmails,
            HttpServletRequest servletRequest) {

        log.info(PPTLogAppender.appendLog("SAVE_PROFILEEDIT", servletRequest, "personId:", personId + "", "givenName:",
                givenName, "familyName", familyName, "gender", gender, "birthDate", birthDate));

        ///////////////////////
        // get person id
        ///////////////////////
        Person person = Context.getPersonService().getPerson(personId);

        //////////////////////////////////////////////////////////////////////////////////
        // Update person object settings
        //////////////////////////////////////////////////////////////////////////////////
        ///////////////////////
        // set person name
        ///////////////////////
        // - create new name
        PersonName personName = new PersonName();
        personName.setGivenName(givenName);
        personName.setFamilyName(familyName);
        // - encorporate this name with other names for person
        Set<PersonName> personNames = person.getNames();
        // - removed prefered status from all existing names
        for (PersonName pn : personNames) {
            if (pn.getPreferred())
                pn.setPreferred(false);
        }
        // - set this name as the prefered name
        // - add this name to person's names if not already added
        boolean personNameExists = false;
        for (PersonName pn : personNames) {
            if (pn.equalsContent(personName)) {
                personNameExists = true;
                pn.setPreferred(true);
            }
        }
        if (!personNameExists) {
            personName.setPreferred(true);
            personNames.add(personName);
        }
        // - save updated names for person
        person.setNames(personNames);

        ///////////////////////
        // set birth date
        ///////////////////////
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        try {
            person.setBirthdate(df.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ///////////////////////
        // set gender
        ///////////////////////
        if (gender != null)
            person.setGender(gender);

        ///////////////////////
        // save person with new updated settings
        ///////////////////////
        Context.getPersonService().savePerson(person);

        //////////////////////////////////////////////////////////////////////////////////
        // Update person preferences
        //////////////////////////////////////////////////////////////////////////////////
        PersonPreferences pp = Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person);
        if (pp != null) {
            boolean mycancerBuddiesValue = Boolean.parseBoolean(myCancerBuddies);
            pp.setMyCancerBuddies(mycancerBuddiesValue);
            Context.getService(PersonPreferencesService.class).savePersonPreferences(pp);
        }

        PatientEmailSubscriptionService pess = Context.getService(PatientEmailSubscriptionService.class);
        PatientEmailSubscription subscription = pess.getSubscriptionForPerson(person);
        if (subscription == null) {
            subscription = new PatientEmailSubscription();
        }
        subscription.setBroadcastEmail(Boolean.parseBoolean(getsBroadcastEmails));
        subscription.setAppointmentReminderEmail(Boolean.parseBoolean(getsAppointmentReminderEmails));
        pess.save(subscription);
    }

}
