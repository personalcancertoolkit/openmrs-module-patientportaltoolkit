package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.openmrs.module.patientportaltoolkit.api.PersonPreferencesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by maurya on 9/23/16.
 */
public class MyCancerBuddiesProfileCardFragmentController {
    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest,@FragmentParam(value="person") Person person ) {
        log.info(PPTLogAppender.appendLog("REQUEST_MYCANCERBUDDIESPROFILECARD_FRAGMENT", pageRequest.getRequest()));
        if (person.getIsPatient() && Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person) == null){
           PersonPreferences pp= new PersonPreferences();
           pp.setMyCancerBuddies(false);
           pp.setMyCancerBuddiesName(person.getGivenName()+person.getFamilyName());
           pp.setMyCancerBuddiesDescription("Hello, I would like to be a part of My Cancer Buddies");
           pp.setPerson(person);
            Context.getService(PersonPreferencesService.class).savePersonPreferences(pp);
            model.addAttribute("personPreferences",pp);
        }
        else if ( Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person) == null) {
            model.addAttribute("personPreferences", null);
        }
        else{
            model.addAttribute("personPreferences",Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person));
        }
    }
    public void RegisterMyCancerBuddiesProfileCard(HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("Register_MyCancerBuddies_Data", servletRequest));
        Person person = Context.getAuthenticatedUser().getPerson();
        PersonPreferences personPreferences= new PersonPreferences();
       if(Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person) !=null) {
           personPreferences  = Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person);
       }
       else{
           personPreferences.setPerson(person);
           personPreferences.setMyCancerBuddiesName(person.getGivenName());
           personPreferences.setMyCancerBuddiesDescription("Hello my name is"+person.getGivenName()+". I am interested in meeting new people.");
       }
       personPreferences.setMyCancerBuddies(true);
       Context.getService(PersonPreferencesService.class).savePersonPreferences(personPreferences);
    }
    public void saveMyCancerBuddiesProfileCard(@RequestParam(value = "mycancerbuddiesname") String mycancerbuddiesname,
                                        @RequestParam(value = "mycancerbuddiesdescription") String mycancerbuddiesdescription,
                                        HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("Save_MyCancerBuddies_Data", servletRequest, "mycancerbuddiesname:", mycancerbuddiesname, "mycancerbuddiesdescription:", mycancerbuddiesdescription));
        Person person = Context.getAuthenticatedUser().getPerson();
        PersonPreferences personPreferences=  Context.getService(PersonPreferencesService.class).getPersonPreferencesByPerson(person);
        personPreferences.setMyCancerBuddiesName(mycancerbuddiesname);
        personPreferences.setMyCancerBuddiesDescription(mycancerbuddiesdescription);
        Context.getService(PersonPreferencesService.class).savePersonPreferences(personPreferences);
    }
}
