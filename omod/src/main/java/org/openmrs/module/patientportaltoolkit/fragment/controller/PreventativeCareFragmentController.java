package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maurya on 2/1/17.
 */
public class PreventativeCareFragmentController {
    public void controller(PageModel model, PageRequest pageRequest) {
        User user = Context.getAuthenticatedUser();
        Person person = (Person) model.get("person");
        org.openmrs.api.PersonService personService=Context.getPersonService();
        model.addAttribute("preventiveCareGuidelines",null);
        List<PreventativeCareEvent> alertablePreventativeCareEvents = new ArrayList<>();
        if(person.isPatient()) {
            Patient patient = Context.getPatientService().getPatientByUuid(person.getUuid());
            model.addAttribute("preventiveCareGuidelines",Context.getService(PreventativeCareService.class).getPreventativeCareGuideline(patient));
            List<PreventativeCareEvent> pcgeList =  new ArrayList<>();
            pcgeList= Context.getService(PreventativeCareService.class).getAllPreventativeCareEventByPatient(patient);
            alertablePreventativeCareEvents.addAll(pcgeList);
        }
        model.addAttribute("alertableevents",alertablePreventativeCareEvents);
    }
}
