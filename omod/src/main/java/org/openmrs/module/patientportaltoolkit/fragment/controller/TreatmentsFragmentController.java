/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalPersonAttributesService;
import org.openmrs.module.patientportaltoolkit.api.util.GenerateTreatmentClassesUtil;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        if (person.getIsPatient()) {
            Date firstSurgeryDate = null;
            patient = Context.getPatientService().getPatientByUuid(person.getUuid());

            model.addAttribute("patientUUID", patient.getUuid());
            model.addAttribute("genhistory", GenerateTreatmentClassesUtil.generateLatestGeneralHistory(patient));
            model.addAttribute("treatmentsummary", GenerateTreatmentClassesUtil.generateGeneralHistory(patient));
            model.addAttribute("radiationencounters", GenerateTreatmentClassesUtil.generateRadiations(patient));
            model.addAttribute("surgeryencounters", GenerateTreatmentClassesUtil.generateSurgeries(patient));
            model.addAttribute("chemotherapyencounters", GenerateTreatmentClassesUtil.generateChemotherapies(patient));
            // model.addAttribute("pptpersonAttributes", pptpersonAttributes)
            firstSurgeryDate = ToolkitResourceUtil.getFirstSurgeryDate(patient);
            model.addAttribute("firstSurgeryDate", firstSurgeryDate);
        } else {
            model.addAttribute("patientUUID", null);
            model.addAttribute("genhistory", null);
            model.addAttribute("surgeryConcepts", null);
            model.addAttribute("chemotherapyConcepts", null);
            model.addAttribute("radiationConcepts", null);
            model.addAttribute("latestTreatmentSummary", null);
            model.addAttribute("treatmentsummary", null);
            model.addAttribute("radiationencounters", null);
            model.addAttribute("surgeryencounters", null);
            model.addAttribute("chemotherapyencounters", null);
        }
    }

    public void saveReminderTriggerDate(@RequestParam(value = "reminderTriggerDate") String reminderTriggerDate,
            HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("Save_TriggerData", servletRequest, "reminderTriggerDate:",
                reminderTriggerDate));
        Person person = Context.getAuthenticatedUser().getPerson();
        Patient patient = null;
        if (person.getIsPatient()) {
            patient = Context.getPatientService().getPatientByUuid(person.getUuid());
            PatientPortalPersonAttributes patientPortalPersonAttributes = Context
                    .getService(PatientPortalPersonAttributesService.class)
                    .getPatientPortalPersonAttributesByPatient(patient);
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date date = new Date();
            try {
                date = format.parse(reminderTriggerDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            patientPortalPersonAttributes.setReminderTriggerDate(date);
            Context.getService(PatientPortalPersonAttributesService.class)
                    .savePatientPortalPersonAttributes(patientPortalPersonAttributes);
        }
    }

    public void deleteTreatment(@RequestParam(value = "treatmentId", required = true) String treatmentId,
            HttpServletRequest servletRequest) throws ParseException {

        log.info(PPTLogAppender.appendLog("Delete_treatment", servletRequest, "Treatment Id:", treatmentId));

        Context.getEncounterService().voidEncounter(Context.getEncounterService().getEncounterByUuid(treatmentId),
                "Deleted By" + Context.getAuthenticatedUser());
    }

}
