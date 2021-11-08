package org.openmrs.module.patientportaltoolkit.page.controller;

import org.openmrs.Person;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

public class AddNewPatientPageController {
    public String post(PageModel model,
                       @RequestParam(value = "username", required = true) String username,
                       @RequestParam(value = "password", required = true) String password,
                       PageRequest pageRequest ) {

        //Context.getPatientService().
       // PersonService personService= Context.getPersonService();
       // Person person = new Person();
       // personService.savePerson();


        return "";

    }
}
