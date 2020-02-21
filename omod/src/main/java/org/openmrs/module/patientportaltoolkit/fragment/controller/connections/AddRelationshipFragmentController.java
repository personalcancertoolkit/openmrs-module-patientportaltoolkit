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
        String irbtext= "INDIANA UNIVERSITY STUDY INFORMATION SHEET FOR\r\nSUrvivorship Care Plan-PERsonal Health Record Intervention Trial (SUPER-IT)\r\nSponsored by Walther Cancer Foundation\r\n\r\nYou are invited to participate in a research study for evaluating two different ways of delivering survivorship or follow-up care information to Cancer Patients. The main difference between the 2 approaches is that one will be delivered through a Web-based program and the other will be delivered through printed information. You were selected as a possible subject because you are caregiver or a health care provider caring for a subject who is using the web-based program. Please read this form and ask any questions you may have before agreeing to be in the study and logging into the Web-based program. The study is being conducted by Dr. David Haggstrom, Indiana University School of Medicine; Department of Internal Medicine.  It is funded by the Walther Caner Foundation.\r\n\r\nSTUDY PURPOSE\r\nThe purpose of this study is for evaluating two different ways of delivering survivorship or follow-up care information to Cancer Patients. Subjects are randomly assigned to one of the 2 approaches. The main difference between the 2 approaches is that one will be delivered through a Web-based program and the other will be delivered through printed information.  \r\nThe topics that both survivorship care plans will cover include the following:\r\n1. Follow-up Care:  guidance about appropriate follow-up or surveillance care\r\n2. Prevention:  guidance about preventive behaviors and cancer screening\r\n3. Support Communities:  local and national information resources and support groups\r\n4. Side Effects:  description of possible side effects of treatment\r\n5. Symptoms: guidance about how to self-manage your symptoms\r\n6. Healthy Lifestyle:  guidance about physical activity, diet, and tobacco use\r\nIn addition, the Web-based program will provide the following:\r\n7. An opportunity to write information about your experience with cancer and treatment into a journal (using Your Relationships, others with whom journal is shared can write in-line comments)\r\n8. An opportunity to share your on-line information with individuals whom you approve, for example, your health care provider, family members, or caregiver.\r\n9. Email program to send messages to individuals whom you approve.\r\n\r\nNUMBER OF PEOPLE TAKING PART IN THE STUDY\r\nApproximately 250 subjects who will be participating in this research. As a caregiver or health care provider of one of the subjects in the intervention group, you are invited to access the Web-based program\r\n\r\nPROCEDURES FOR THE STUDY\r\nIf you agree to be in the study, you will do the following things:\r\nThe subject who invited you to access the web based program will control how much you access of their page. You may be invited to view one or more of their social, medical and physical activity pages. You may also be granted permission to post some feedback on their social wall of the Web-based program.\r\n\r\nRISKS OF TAKING PART IN THE STUDY\r\nThere are no physical risks to taking part in this study.  One possible risk is that you maybe viewing some of the subject\u2019s social, medical and physical activity pages. This can making feel uncomfortable or overwhelmed. If using the Web-based program makes you feel uncomfortable; you can stop accessing the Web-based program until/if you feel ready again.\r\n\r\nBENEFITS OF TAKING PART IN THE STUDY\r\nThere are no direct benefits to participating in this study. The benefits to participation that are reasonable to expect are to stay involved in the subject\u2019s life and care if granted access\r\n\r\nALTERNATIVES TO TAKING PART IN THE STUDY\r\nTaking part is completely voluntary. Instead of being in the study, you have the option to not participate in the study. If you choose not to participate, your decision will not affect the subjects\u2019 relationship with their doctor, IUH or the services they provide to them.\r\n\r\n\r\nCONFIDENTIALITY\r\nEfforts will be made to keep your personal information confidential.  We cannot guarantee absolute confidentiality.  Your personal information may be disclosed if required by law.  Your identity will be held in confidence in reports in which the study may be published and databases in which results may be stored. Organizations that may inspect and/or copy your research records for quality assurance and data analysis include groups such as the study investigator and his/her research associates, the Indiana University Institutional Review Board or its designees, the study sponsor (Walther Cancer Foundation) and (as allowed by law) state or federal agencies, specifically the Office for Human Research Protections (OHRP) who may need to access your medical and/or research records.\r\n\r\nCOSTS\r\nTaking part in this study will not lead to added costs to you or the subject you are caring for insurance company.  The subject you are caring for insurance company will be responsible for the following costs:  all of the health care related to cancer treatment or follow-up care.\r\n\r\nPAYMENT\r\nYou will not receive payment for taking part in this study. \r\n\r\nCOMPENSATION FOR INJURY\r\nIn the event of physical injury resulting from subject\u2019s participation in this research, necessary medical treatment will be provided to them and billed as part of their medical expenses.  Costs not covered by their health care insurer will be their responsibility.  Also, it is their responsibility to determine the extent of their health care coverage.  There is no program in place for other monetary compensation for such injuries.  However, they are not giving up any legal rights or benefits to which they are otherwise entitled.  If they are participating in research that is not conducted at a medical facility, they will be responsible for seeking medical care and for the expenses associated with any care received.  \r\n\r\nCONTACTS FOR QUESTIONS OR PROBLEMS\r\nFor questions about the study or a research-related injury, contact the research manager, Layla Baker, at (317)274-9028 during regular business hours (8am to 5pm).  If you cannot reach the researcher during regular business hours (i.e., 8 a.m.  to 5 p.m.), please call the IU Human Subjects Office at 317-278-3458.\r\n\r\nVOLUNTARY NATURE OF THIS STUDY\r\nTaking part in this study is voluntary.  You may choose not to take part or may leave the study at any time.  Leaving the study will not result in any penalty or loss of benefits to which you are entitled.  Your decision whether or not to participate in this study will not affect your current or future relations with IU or Eskenazi Health systems. \r\n";
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
            MailHelper.sendMail("Welcome", "Hello " + person.getPersonName() + ", \n you have been added as a connection to one of the members of www.personalcancertoolkit.org, please log on to www.personalcancertoolkit.org to create your account and to accept or ignore the connection request. The username for your account is "+savedUser.getUsername()+" and password is " +newPassword+ " .Please change your password on your first login\n"+ irbtext, person.getAttribute("Email").toString());
        }
        else{
            MailHelper.sendMail("Welcome", "Hello " + person.getPersonName() + ", \n you have been added as a connection to one of the members of www.personalcancertoolkit.org, please log on to www.personalcancertoolkit.org to accept or ignore the connection request.\n"+ irbtext, person.getAttribute("Email").toString());
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
