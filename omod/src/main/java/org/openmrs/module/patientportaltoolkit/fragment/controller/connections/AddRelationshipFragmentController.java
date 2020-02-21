/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller.connections;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.util.MailHelper;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.PasswordUtil;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maurya on 24/06/2015.
 */
public class AddRelationshipFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model,PageRequest pageRequest) {
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("securityLayers",Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        log.info(PPTLogAppender.appendLog("REQUEST_ADDRELATIONSHIP_FRAGMENT", pageRequest.getRequest()));
    }

    public void addRelationshipfromForm(FragmentModel model, @RequestParam(value = "given", required = true) String given,
                                          @RequestParam(value = "family", required = true) String family,
                                        @RequestParam(value = "personEmail", required = true) String personEmail,
                                        @RequestParam(value = "personRelationType", required = true) Integer personRelationType,
                                        @RequestParam(value = "securityLayerType", required = true) String securityLayerType,
                                        @RequestParam(value = "gender", required = true) String gender, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("ADD_RELATION", servletRequest, "GIVEN_NAME:", given, "FAMILY_NAME:", family, "EMAIL:", personEmail,"RELATION_TYPE:", personRelationType.toString(), "SECURITY_TYPE:", securityLayerType, "GENDER:", gender));
        //log.info("~ADD_RELATION~"+ Context.getAuthenticatedUser().getUsername() + "~USER_ENTERED_DATA~"+ "GIVEN_NAME:"+  given + "^FAMILY_NAME:" + family + "^EMAIL:"+ personEmail +"^RELATION_TYPE:"+ personRelationType +"^SECURITY_TYPE:"+ securityLayerType +"^GENDER:"+ gender);
        int checkIfPersonExists=0;
        User user = Context.getAuthenticatedUser();
        UserService userService=Context.getUserService();
        //check if person already exists in the system
        PersonAttributeType paType=Context.getPersonService().getPersonAttributeTypeByName("Email");
        Person person = new Person();
        List<User> previoususers = Context.getUserService().getAllUsers();
        //getUsersByName(given,family,false);
        if(previoususers!=null){
            for (User u: previoususers){
                if(u.getPerson()!=null && u.getPerson().getAttribute(paType)!=null){
                if(u.getPerson().getAttribute(paType).getValue().equals(personEmail)) {
                    checkIfPersonExists = 1;
                    person = u.getPerson();
                    break;
                }
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
            String newPassword = String.valueOf(PasswordUtil.getNewPassword());
            newUser.setUserProperty("forcePassword", "true");

            User savedUser = Context.getUserService().createUser(newUser, newPassword);
            //System.out.println("\nsystemout---password is " + "Test123" + passworduuid);
            MailHelper.sendMail("Welcome", "Hello " + person.getPersonName() + ", \n you have been added as a connection to one of the members of www.personalcancertoolkit.org, please log on to www.personalcancertoolkit.org to create your account and to accept or ignore the connection request. The username for your account is "+savedUser.getUsername()+" and password is " +newPassword+ " .Please change your password on your first login", person.getAttribute("Email").toString());
        }
        else{
            MailHelper.sendMail("Welcome", "Hello " + person.getPersonName() + ", \n you have been added as a connection to one of the members of www.personalcancertoolkit.org, please log on to www.personalcancertoolkit.org to accept or ignore the connection request.", person.getAttribute("Email").toString());
        }
        PatientPortalRelation ppr=new PatientPortalRelation(user.getPerson(),person);
        ppr.setRelationType(Context.getPersonService().getRelationshipType(personRelationType));
        Person personGettingAccess=null;
        List<String> shareTypesList = Arrays.asList(securityLayerType.split(","));
        SecurityLayerService securityLayerService= Context.getService(SecurityLayerService.class);
        List<SecurityLayer> shareTypes=new ArrayList<>();
        if (ppr.getPerson().equals(user.getPerson()))
            personGettingAccess=ppr.getRelatedPerson();
        else
            personGettingAccess=ppr.getPerson();
        for (String s:shareTypesList) {
            shareTypes.add(securityLayerService.getSecurityLayerByUuid(s));
        }
        PatientPortalRelationService pprService=Context.getService(PatientPortalRelationService.class);
        ppr.setShareTypeA(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid("c21b5749-5972-425b-a8dc-15dc8f899a96"));
        ppr.setShareTypeB(Context.getService(SecurityLayerService.class).getSecurityLayerByUuid("c21b5749-5972-425b-a8dc-15dc8f899a96")); //share posts by default
        ppr.setShareStatus(0);
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        SimpleDateFormat f = new SimpleDateFormat("dd-MMMM-yyyy");
        //System.out.println(f.format(date.getTime()));
        date.add(Calendar.YEAR, 20);
        ppr.setExpireDate(date.getTime());
        pprService.savePatientPortalRelation(ppr);
        pprService.saveShareTypes(user.getPerson(),personGettingAccess,shareTypes);
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
