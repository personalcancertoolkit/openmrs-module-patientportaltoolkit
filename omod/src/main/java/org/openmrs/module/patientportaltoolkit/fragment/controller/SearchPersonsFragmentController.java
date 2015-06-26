package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Maurya on 24/06/2015.
 */
public class SearchPersonsFragmentController {
    public void controller(FragmentModel model) {
        model.addAttribute("searchPersons",null);
    }

    public String getPersonsFromSearch(FragmentModel model,@RequestParam(value = "searchQuery", required = true) String searchQuery,UiUtils ui) {
        List<Person> persons =Context.getPersonService().getPeople(searchQuery, false);
       return "json";
    }

}
