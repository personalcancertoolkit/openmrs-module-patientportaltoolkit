package org.openmrs.module.patientportaltoolkit.fragment.controller;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.ui.framework.page.PageModel;

/**
 * Created by Maurya on 17/06/2015.
 */
public class SideEffectsFragmentController {

    public void controller(PageModel model) {
        Patient patient = null;
        Person person = (Person) model.get("person");
        patient= Context.getPatientService().getPatientByUuid(person.getUuid());
        if (patient !=null)
        model.addAttribute("concepts",Context.getService(SideEffectService.class).getAllSideEffectsForPatient(patient));
        else
            model.addAttribute("concepts",null);
    }
}