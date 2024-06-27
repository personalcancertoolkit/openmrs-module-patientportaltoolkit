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

    public void controller(FragmentModel model, PageRequest pageRequest) {
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("securityLayers", Context.getService(SecurityLayerService.class).getAllSecurityLayers());
        log.info(PPTLogAppender.appendLog("REQUEST_ADDRELATIONSHIP_FRAGMENT", pageRequest.getRequest()));
    }

    public void addRelationshipfromForm(FragmentModel model,
            @RequestParam(value = "given", required = true) String given,
            @RequestParam(value = "family", required = true) String family,
            @RequestParam(value = "personEmail", required = true) String personEmail,
            @RequestParam(value = "personRelationType", required = true) Integer personRelationType,
            @RequestParam(value = "securityLayerType", required = true) String securityLayerType,
            @RequestParam(value = "gender", required = true) String gender, HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("ADD_RELATION", servletRequest, "GIVEN_NAME:", given, "FAMILY_NAME:", family,
                "EMAIL:", personEmail, "RELATION_TYPE:", personRelationType.toString(), "SECURITY_TYPE:",
                securityLayerType, "GENDER:", gender));

        Boolean personExists = false;
        User user = Context.getAuthenticatedUser();
        UserService userService = Context.getUserService();

        // check if person already exists in the system
        PersonAttributeType paType = Context.getPersonService().getPersonAttributeTypeByName("Email");
        Person person = new Person();
        List<User> previoususers = Context.getUserService().getAllUsers();

        if (previoususers != null) {
            for (User u : previoususers) {
                if (u.getPerson() != null && u.getPerson().getAttribute(paType) != null) {
                    if (u.getPerson().getAttribute(paType).getValue().equals(personEmail)) {
                        personExists = true;
                        person = u.getPerson();
                        break;
                    }
                }
            }
        }
        if (!personExists) {
            Person newPerson = new Person();
            newPerson.setPersonCreator(user);
            newPerson.setPersonDateCreated(new Date());
            newPerson.setPersonChangedBy(user);
            newPerson.setPersonDateChanged(new Date());

            if (StringUtils.isEmpty(gender)) {
                log.error("Biological Sex cannot be null.");
            } else if (gender.toUpperCase().contains("M")) {
                newPerson.setGender("M");
            } else if (gender.toUpperCase().contains("F")) {
                newPerson.setGender("F");
            } else {
                log.error("Biological Sex must be 'M' or 'F'.");
            }
            if ("".equals(given) || "".equals(family)) {
                log.error("Given name and family name cannot be null.");
            }

            PersonName name = new PersonName(given, "", family);
            name.setCreator(user);
            name.setDateCreated(new Date());
            name.setChangedBy(user);
            name.setDateChanged(new Date());
            newPerson.addName(name);
            try {
                Date d = updateAge("", "", "");
                newPerson.setBirthdate(d);
            } catch (java.text.ParseException pe) {
                log.error(pe);
                // return new String("Birthdate cannot be parsed.");
            }
            newPerson.setGender(gender);

            Set<PersonAttribute> personAttributeSet = new TreeSet<>();
            PersonAttribute personAttributeEmail = new PersonAttribute(paType, personEmail);
            personAttributeSet.add(personAttributeEmail);
            newPerson.setAttributes(personAttributeSet);
            person = Context.getPersonService().savePerson(newPerson);

            User newUser = new User(person);
            newUser.setUsername(given + family);
            newUser.addRole(userService.getRole(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE_ROLE));
            String newPassword = String.valueOf(PasswordUtil.getNewPassword());

            User savedUser = Context.getUserService().createUser(newUser, newPassword);
            String content = getResearchInformation();
            String bodyText = "Hello " + person.getPersonName() + ", <br/><br/>"
                    + user.getPersonName()
                    + " added you as a connection on www.sphere.regenstrief.org. Please visit https://sphere.regenstrief.org to access your account and accept or ignore the connection request. The username for your account is "
                    + savedUser.getUsername() + " and the password is " + newPassword
                    + " . Please change your password after you log in" + content;
            String destinationEmailAddress = person.getAttribute("Email").toString();

            MailHelper.sendMail(
                    "Welcome",
                    bodyText,
                    destinationEmailAddress,
                    true);
        } else {
            String destinationEmailAddress = person.getAttribute("Email").toString();
            String content = "Hello " + person.getPersonName() + ",\n\n"
                    + user.getPersonName()
                    + " added you as a connection on www.sphere.regenstrief.org. Please login at https://sphere.regenstrief.org to accept or ignore the connection request.";

            MailHelper.sendMail(
                    "Welcome",
                    content,
                    destinationEmailAddress,
                    false);

            // Check for existing relationship between the person and the user
            PatientPortalRelation existingPatientPortalRelation = Context.getService(PatientPortalRelationService.class)
                    .getPatientPortalRelation(user.getPerson(), person, user);
            if (existingPatientPortalRelation != null) {
                return;
            }
        }

        PatientPortalRelation ppr = new PatientPortalRelation(user.getPerson(), person);
        ppr.setRelationType(Context.getPersonService().getRelationshipType(personRelationType));
        Person personGettingAccess = null;
        List<String> shareTypesList = Arrays.asList(securityLayerType.split(","));
        SecurityLayerService securityLayerService = Context.getService(SecurityLayerService.class);

        List<SecurityLayer> shareTypes = new ArrayList<>();
        if (ppr.getPerson().equals(user.getPerson()))
            personGettingAccess = ppr.getRelatedPerson();
        else
            personGettingAccess = ppr.getPerson();
        for (String s : shareTypesList) {
            shareTypes.add(securityLayerService.getSecurityLayerByUuid(s));
        }
        PatientPortalRelationService pprService = Context.getService(PatientPortalRelationService.class);
        ppr.setShareTypeA(Context.getService(SecurityLayerService.class)
                .getSecurityLayerByUuid(SecurityLayer.CAN_SEE_POSTS));
        ppr.setShareTypeB(Context.getService(SecurityLayerService.class)
                .getSecurityLayerByUuid(SecurityLayer.CAN_SEE_POSTS)); // share posts by default
        ppr.setShareStatus(0);
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());

        // System.out.println(f.format(date.getTime()));
        date.add(Calendar.YEAR, 20);
        ppr.setExpireDate(date.getTime());
        pprService.savePatientPortalRelation(ppr);
        pprService.saveShareTypes(user.getPerson(), personGettingAccess, shareTypes);
        // return "Success";
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
            } catch (NumberFormatException nfe) {
                log.error("Error during adding date into calendar", nfe);
            }
            return cal.getTime();
        } else {
            cal.setTime(df.parse(birthdate));
        }
        return cal.getTime();
    }

    private String getResearchInformation() {
        String content = "<br/><br/><br/>"
                + "<h3 align=center>INDIANA UNIVERSITY STUDY INFORMATION SHEET FOR RESEARCH</h3>"
                + "<h4 align=center>Survivorship Plan Health Record (SPHERE) Implementation Trial<br/>National Cancer Institute (NCI)</h4>"
                + "<p><b>You are being asked to participate in a research study.</b>"
                + " Scientists do research to answer important questions that might help change or improve the way we do things in the future. "
                + "This document will give you information about the study to help you decide whether you want to participate. "
                + "Please read this form, and ask any questions you have, before participating in the study.<br/><br/>"

                + "<b>All research is voluntary.  You may choose not to take part in the study or may choose to leave the study at any time.</b> "
                + "Deciding not to participate, or deciding to leave the study later, will not result in any penalty or loss of benefits to which you are entitled and will not affect your relationship with IU or Eskenazi Health systems.<br/><br/>"
                + "You were selected as a possible participant because you are a caregiver of one of the subjects in the study.  "
                + "Thus, you have been invied by them to access the online survivorship care plan.  The study is being conducted by Dr. David Haggstrom, Indiana University School of Medicine, Department of Internal Medicine. "
                + "It is funded by the National Cancer Institute (NCI).<br/><br/>"

                + "<b>The purpose of this study</b> is for evaluating an online Survivorship Care Plan. <br/>"
                + "The topics that the online Survivorship Care Plancovers include the following:"
                + "<ul>"
                + "<li>Follow-up Care:  guidance about appropriate follow-up or surveillance care"
                + "<li>Prevention:  guidance about preventive behaviors and cancer screening"
                + "<li>Support Communities:  local and national information resources and support groups"
                + "<li>Side Effects:  description of possible side effects of treatment"
                + "<li>Symptoms: guidance about how to self-manage your symptoms"
                + "</ul><br/><br/>"

                + "In addition, the Web-based program will provide the following:"
                + "<ul>"
                + "<li>An opportunity to write information about your experience with cancer and treatment into a journal (using Your Relationships, others with whom journal is shared can write in-line comments)"
                + "<li>An opportunity to share your on-line information with individuals whom you approve, for example, family members."
                + "<li>Email program to send messages to individuals whom you approve."
                + "</ul><br/><br/>"

                + "<b>You have now received an email invite from a patient whom you know to use the online Survivorship Care Plan.  If you agree to be in the study, you will do the following things.</b>"
                + "<ul>"
                + "<li>Once you accept the invite, you will be directed back to the Survivorship Care Plan"
                + "<li>You will be able to access with portal with a given username and create a new password for your usage "
                + "<li>The patient who invited you may give you access to either their medical records online, social records online, or both"
                + "<li>Based on your access level, you will be to interact with the patient by messaging them or posting a response on their social page"
                + "</ul><br/><br/>"

                + "<b>Before you agree to participate, you should consider the risks and potential benefits of taking part in this study.</b>"
                + "There are no physical risks to taking part in this study.  One possible risk is that you maybe viewing some of the subject's social and medical pages. "
                + "This can making feel uncomfortable or overwhelmed. If using the Web-based program makes you feel uncomfortable; you can stop accessing the Web-based program until/if you feel ready again.  "
                + "We don't expect you to receive any benefit from taking part in this study, but we hope to learn things which will help scientists in the future and guide them to increase cancer surveillance, improve symptom control, increase healthy behaviors, and increase preventive care.<br/><br/>"

                + "<b>You will not be paid for participating in this study. There is no cost to participate in the study.</b><br/><br/>"

                + "<b>We will protect your information </b> and make every effort to keep your personal information confidential, but we cannot guarantee absolute confidentiality will be made to keep your personal information confidential. "
                + "We cannot guarantee absolute confidentiality. Your personal information may be disclosed if required by law. No information and databases in which results may be stored which could identify you will be shared in publications about this study. <br/><br/>"

                + "A description of this clinical trial will be available on ClinicalTrials.gov, as required by U.S. law.  This website will not include information that can identify you.  At most, the website will include a summary of the results.  "
                + "You can search this website at any time. <br/><br/>"

                + "Organizations that may inspect and/or copy your research records for quality assurance and data analysis include groups such as the study investigator and his/her research associates, the Indiana University Institutional Review Board or its designees, National Institute of health and state or federal agencies who may need to access the research records (as allowed by law). "
                + "State and federal agencies may include the \"National Cancer Institute (NCI)\" for research funded or supported by NCI, and/or \"National Institutes of Health (NIH)\" for research funded or supported by NIH, etc.<br/><br/>"

                + "For the protection of your privacy, this research is covered by a Certificate of Confidentiality from the National Institutes of Health.  "
                + "The researchers may not disclose or use any information, documents, or specimens that could identify you in any civil, criminal, administrative, legislative, or other legal proceeding, unless you consent to it.  "
                + "Information, documents, or specimens protected by this Certificate may be disclosed to someone who is not connected with the research:"

                + "<ul>"
                + "<li>if there is a federal, state, or local law that requires disclosure (such as to report child abuse or communicable diseases); "
                + "<li>if you consent to the disclosure, including for your medical treatment; "
                + "<li>if it is used for other scientific research in a way that is allowed by the federal regulations that protect research subjects;"
                + "<li>for the purpose of auditing or program evaluation by the government or funding agency."
                + "</ul><br/><br/>"

                + "A Certificate of Confidentiality does not prevent you from voluntarily releasing information about yourself.  "
                + "If you want your research information released to an insurer, medical care provider, or any other person not connected with the research, you must provide consent to allow the researchers to release it.<br/><br/>"

                + "<b>If you have questions about the study or encounter a problem with the research</b>, contact the research manager, Mitch Smith, at (317) 274-9028.  "
                + "If you cannot reach the researcher during regular business hours (i.e., 8 a.m. to 5 p.m.), please contact the IU Human Subjects Office at 800-696-2949 or at irb@iu.edu.<br/><br/>"

                + "For questions about your rights as a research participant, to discuss problems, complaints, or concerns about a research study, or to obtain information or to offer input, please contact the IU Human Research Protection Program office at 800-696-2949 or at irb@iu.edu.<br/><br/>"
                + "<b>If you decide to participate in this study, you can change your mind and decide to leave the study at any time in the future.</b> <br/><br/>"

                + "If you decide to participate in this study, you can change your mind and decide to leave the study at any time in the future.  "
                + "The study team will help you withdraw from the study safely.  If you decide to withdraw, you will need to be let us know in writing and mail your request to the attention of Dr. David Haggstrom at 1101 W.10th street, Indianapolis, IN 46202. <br/><br/>"

                + "Your participation may be terminated by the investigator without regard to your consent if you misuse your privileges on the cancer web-based portal.</p>";

        return content;
    }

}
