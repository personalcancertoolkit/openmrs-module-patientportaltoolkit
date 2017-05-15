/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */

@Controller
public class PatientPortalEncounterController {

    private final static String  RADIATION_ENCOUNTER = "CANCER TREATMENT - RADIATION";
    private final static String  CHEMOTHERAPY_ENCOUNTER = "CANCER TREATMENT - CHEMOTHERAPY";
    private final static String  SURGERY_ENCOUNTER = "CANCER TREATMENT - SURGERY";
    private final static String  TREATMENTSUMMARY_ENCOUNTER = "CANCER TREATMENT SUMMARY";
    protected final Log log = LogFactory.getLog(getClass());
    @RequestMapping( value = "/patientportaltoolkit/getallencountertypes")
    @ResponseBody
    public Object getPatientPortalEncounterTypes()
    {
        return ToolkitResourceUtil.generateEncounterTypes(Context.getEncounterService().getAllEncounterTypes());
    }

    @RequestMapping( value = "/patientportaltoolkit/getallencountersforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalEncountersforPatient( @PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateEncounters(Context.getEncounterService().getEncountersByPatient(patient));
    }

    @RequestMapping( value = "/patientportaltoolkit/getchemotherapyencountersforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalChemotherapyEncountersforPatient( @PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateEncounters(getEncountersByTreatment(patient, CHEMOTHERAPY_ENCOUNTER));
    }

    @RequestMapping( value = "/patientportaltoolkit/getradiationencountersforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalRadiationEncountersforPatient( @PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateEncounters(getEncountersByTreatment(patient, RADIATION_ENCOUNTER));
    }

    @RequestMapping( value = "/patientportaltoolkit/getsurgeryencountersforpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalSurgeryEncountersforPatient( @PathVariable( "patientId" ) String patientId)
    {
        Patient patient=Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generateEncounters(getEncountersByTreatment(patient, SURGERY_ENCOUNTER));
    }

    public List<Encounter> getEncountersByTreatment(Patient patient,String treatmentType) {
        List<Encounter> encounters = Context.getEncounterService().getEncountersByPatient(patient);
        List<Encounter> treatmentEncounters = new ArrayList<Encounter>();
        for (Encounter encounter : encounters) {
            if (!encounter.isVoided() && treatmentType.equals(encounter.getEncounterType().getName())) {
                treatmentEncounters.add(encounter);
            }
        }
        return treatmentEncounters;
    }
}
