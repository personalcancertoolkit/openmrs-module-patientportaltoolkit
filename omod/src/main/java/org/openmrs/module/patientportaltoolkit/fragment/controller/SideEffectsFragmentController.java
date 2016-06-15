package org.openmrs.module.patientportaltoolkit.fragment.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * Created by Maurya on 17/06/2015.
 */
public class SideEffectsFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_SIDEEFFECTS_FRAGMENT", pageRequest.getRequest()));
        Patient patient = null;
        Person person = (Person) model.get("person");
        patient= Context.getPatientService().getPatientByUuid(person.getUuid());
        if (patient !=null)
        model.addAttribute("concepts",Context.getService(SideEffectService.class).getAllSideEffectsForPatient(patient));
        else
            model.addAttribute("concepts",null);
        }
}