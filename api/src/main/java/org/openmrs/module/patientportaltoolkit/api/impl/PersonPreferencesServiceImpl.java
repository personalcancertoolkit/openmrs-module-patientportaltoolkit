package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.db.PersonPreferencesDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maurya on 9/19/16.
 */
public class PersonPreferencesServiceImpl implements PersonPreferencesService {

   PersonPreferencesDAO dao;

    public void setDao(PersonPreferencesDAO dao) {
        this.dao = dao;
    }

    @Override
    public PersonPreferences getPersonPreferencesByUuid(String uuid) {
        return dao.getPersonPreferenceByUuid(uuid);
    }

    @Override
    public PersonPreferences getPersonPreferencesByPerson(Person person) {
       return dao.getPersonPreferencesByPerson(person);
    }

    @Override
    public List<PersonPreferences> getAllPersonPreferences() {
        List<PersonPreferences> personPreferences= dao.getAllPersonPreferences();
        List<PersonPreferences> newPersonPreferences= new ArrayList<PersonPreferences>();
        Person person= Context.getAuthenticatedUser().getPerson();
        for (PersonPreferences pp: personPreferences){
            if(!pp.getPerson().equals(person))
                newPersonPreferences.add(pp);
        }
        return  newPersonPreferences;

    }

    @Override
    public PersonPreferences savePersonPreferences(PersonPreferences personPreferences) {
        return dao.savePersonPreferences(personPreferences);
    }

    @Override
    public void deletePersonPreferences(PersonPreferences personPreferences) {
        dao.deletePersonPreferences(personPreferences);
    }
}
