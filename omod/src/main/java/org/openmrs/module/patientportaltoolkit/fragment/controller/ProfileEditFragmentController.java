package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by maurya on 2/22/16.
 */
public class ProfileEditFragmentController {
    public void controller() {
    }
    public void saveProfileEditForm(FragmentModel model,@RequestParam(value = "personId", required = true) int personId,
                                    @RequestParam(value = "givenName", required = true) String givenName,
                                    @RequestParam(value = "familyName", required = true) String familyName,
                                    @RequestParam(value = "gender", required = true) String gender,
                                    @RequestParam(value = "birthDate", required = true) String birthDate)  {


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
    }

}
