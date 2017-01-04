/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalPersonAttributesService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Maurya on 19/06/2015.
 */
public class TreatmentsFragmentController {


    protected final Log log = LogFactory.getLog(getClass());

    public void controller(PageModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_TREATMENTS_FRAGMENT", pageRequest.getRequest()));
        Patient patient = null;
        Person person = (Person) model.get("person");
       // if(Context.getAuthenticatedUser().getPerson().isPatient()){
        if(person.isPatient()){
        patient= Context.getPatientService().getPatientByUuid(person.getUuid());
        PatientPortalFormService patientPortalFormService=Context.getService(PatientPortalFormService.class);
            PatientPortalPersonAttributes pptpersonAttributes = Context.getService(PatientPortalPersonAttributesService.class).getPatientPortalPersonAttributesByPatient(patient);
            model.addAttribute("surgeryConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.SURGERY_ENCOUNTER));
            model.addAttribute("chemotherapyConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.CHEMOTHERAPY_ENCOUNTER));
            model.addAttribute("radiationConcepts", patientPortalFormService.getPatientPortalFormByFormType(PatientPortalToolkitConstants.RADIATION_ENCOUNTER));
            model.addAttribute("latestTreatmentSummary", GenerateTreatmentClassesUtil.generateLatestGeneralHistory(patient));
            model.addAttribute("treatmentsummary", GenerateTreatmentClassesUtil.generateGeneralHistory(patient));
            model.addAttribute("radiationencounters", GenerateTreatmentClassesUtil.generateRadiations(patient));
            model.addAttribute("surgeryencounters",GenerateTreatmentClassesUtil.generateSurgeries(patient));
            model.addAttribute("chemotherapyencounters",GenerateTreatmentClassesUtil.generateChemotherapies(patient));
            model.addAttribute("pptpersonAttributes", pptpersonAttributes);
        }
        else {
            model.addAttribute("surgeryConcepts",null);
            model.addAttribute("chemotherapyConcepts", null);
            model.addAttribute("radiationConcepts", null);
            model.addAttribute("latestTreatmentSummary", null);
            model.addAttribute("treatmentsummary", null);
            model.addAttribute("radiationencounters", null);
            model.addAttribute("surgeryencounters",null);
            model.addAttribute("chemotherapyencounters",null);
        }
        //log.info("Treatments requested for -" + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")" + " Requested by - " + Context.getAuthenticatedUser().getPersonName() + "(id=" + Context.getAuthenticatedUser().getPerson().getPersonId() + ",uuid=" + Context.getAuthenticatedUser().getPerson().getUuid() + ")");
    }

    public void saveReminderTriggerDate(@RequestParam(value = "reminderTriggerDate") String reminderTriggerDate,
                                               HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("Save_TriggerData", servletRequest, "reminderTriggerDate:", reminderTriggerDate));
        Person person = Context.getAuthenticatedUser().getPerson();
        Patient patient = null;
        if(person.isPatient()) {
            patient= Context.getPatientService().getPatientByUuid(person.getUuid());
            PatientPortalPersonAttributes patientPortalPersonAttributes = Context.getService(PatientPortalPersonAttributesService.class).getPatientPortalPersonAttributesByPatient(patient);
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date date = new Date();
            try {
                date = format.parse(reminderTriggerDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            patientPortalPersonAttributes.setReminderTriggerDate(date);
            Context.getService(PatientPortalPersonAttributesService.class).savePatientPortalPersonAttributes(patientPortalPersonAttributes);
        }
    }

}
