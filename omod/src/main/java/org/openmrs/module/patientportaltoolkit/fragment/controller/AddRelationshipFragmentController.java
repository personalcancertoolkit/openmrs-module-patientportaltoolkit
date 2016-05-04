package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maurya on 24/06/2015.
 */
public class AddRelationshipFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller() {

    }

    public void addRelationshipfromForm(FragmentModel model, @RequestParam(value = "given", required = true) String given,
                                          @RequestParam(value = "family", required = true) String family,
                                        @RequestParam(value = "personEmail", required = true) String personEmail,
                                        @RequestParam(value = "personRelationType", required = true) String personRelationType,
                                        @RequestParam(value = "securityLayerType", required = true) String securityLayerType,
                                        @RequestParam(value = "gender", required = true) String gender) {
        log.info("Add Relationship/Connection with -"+ given + " " + family + " ("+ personEmail +", "+ personRelationType +", "+ securityLayerType +", "+ gender+") " + "Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
        int checkIfPersonExists=0;
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();
        //check if person already exists in the system
        PersonAttributeType paType=Context.getPersonService().getPersonAttributeTypeByName("Email");
        Person person = new Person();
        List<User> previoususers = Context.getUserService().getUsersByName(given,family,false);
        if(previoususers!=null){
            for (User u: previoususers){
                if(u.getPerson().getAttribute(paType).getValue().equals(personEmail)){
                    checkIfPersonExists=1;
                    person=u.getPerson();
                    break;
                }
            }
        }
        if(checkIfPersonExists ==0) {
            Person p = new Person();
            p.setPersonCreator(user);
            p.setPersonDateCreated(new Date());
            p.setPersonChangedBy(user);
            p.setPersonDateChanged(new Date());
            if (StringUtils.isEmpty(gender)) {
                log.error("Gender cannot be null.");
                //return String.valueOf("Gender cannot be null.");
            } else if (gender.toUpperCase().contains("M")) {
                p.setGender("M");
            } else if (gender.toUpperCase().contains("F")) {
                p.setGender("F");
            } else {
                log.error("Gender must be 'M' or 'F'.");
                //return new String("Gender must be 'M' or 'F'.");
            }
            if ("".equals(given) || "".equals(family)) {
                log.error("Given name and family name cannot be null.");
                //return new String("Given name and family name cannot be null.");
            }
            PersonName name = new PersonName(given, "", family);
            name.setCreator(user);
            name.setDateCreated(new Date());
            name.setChangedBy(user);
            name.setDateChanged(new Date());
            p.addName(name);
            try {
                Date d = updateAge("", "", "");
                p.setBirthdate(d);
            } catch (java.text.ParseException pe) {
                log.error(pe);
                //return new String("Birthdate cannot be parsed.");
            }
            p.setGender(gender);
            Set<PersonAttribute> personAttributeSet = new TreeSet<>();
            PersonAttribute personAttributeEmail = new PersonAttribute(paType,personEmail);
            personAttributeSet.add(personAttributeEmail);
            p.setAttributes(personAttributeSet);
            person = Context.getPersonService().savePerson(p);
            User newUser = new User(person);
            newUser.setUsername(given + family);
            newUser.addRole(userService.getRole(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE_ROLE));
            String passworduuid = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
            User savedUser = Context.getUserService().saveUser(newUser, "Tester123");
            //System.out.println("\nsystemout---password is " + "Test123" + passworduuid);
        }
        PatientPortalRelation ppr=new PatientPortalRelation(user.getPerson(),person);
        RelationshipType selectedRelationType = Context.getPersonService().getRelationshipTypeByUuid(personRelationType);
        ppr.setRelationType(selectedRelationType.getaIsToB());
        ppr.setShareType(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid(securityLayerType));
        ppr.setShareStatus(0);
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        SimpleDateFormat f = new SimpleDateFormat("dd-MMMM-yyyy");
        //System.out.println(f.format(date.getTime()));
        date.add(Calendar.YEAR,20);
        ppr.setExpireDate(date.getTime());
        Context.getService(PatientPortalRelationService.class).savePatientPortalRelation(ppr);
        //return "Success";
    }

    private Date updateAge(String birthdate, String dateformat, String age) throws java.text.ParseException {
        SimpleDateFormat df = new SimpleDateFormat();
        if (!"".equals(dateformat)) {
            dateformat = dateformat.toLowerCase().replaceAll("m", "M");
        } else {
            dateformat = new String("MM/dd/yyyy");
        }
        df.applyPattern(dateformat);
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        if ("".equals(birthdate)) {
            if ("".equals(age)) {
                return cal.getTime();
            }
            try {
                cal.add(Calendar.YEAR, -(Integer.parseInt(age)));
            }
            catch (NumberFormatException nfe) {
                log.error("Error during adding date into calendar", nfe);
            }
            return cal.getTime();
        } else {
            cal.setTime(df.parse(birthdate));
        }
        return cal.getTime();
    }

}
