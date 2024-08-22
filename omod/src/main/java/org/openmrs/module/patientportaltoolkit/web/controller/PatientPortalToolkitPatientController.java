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
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maurya.
 */
@Controller
public class PatientPortalToolkitPatientController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/patientportaltoolkit/getpatient/{patientId}")
    @ResponseBody
    public Object getPatientPortalPatient(@PathVariable("patientId") String patientId)
            throws Exception {

        PatientService patientService = Context.getService(PatientService.class);
        Patient patient = Context.getPatientService().getPatientByUuid(patientId);
        Map<String, Object> patientObject = new HashMap<String, Object>();
        if (patient != null)
            patientObject = ToolkitResourceUtil.generatePerson(patientService.getPatient(patientId));
        return patientObject;

    }

    @RequestMapping(value = "/patientportaltoolkit/getallpatients")
    @ResponseBody
    public Object getAllPatientPortalPatient()
            throws Exception {
        List<Patient> omrspatients = Context.getPatientService().getAllPatients();
        List<Object> patients = new ArrayList<Object>();
        for (Patient p : omrspatients) {
            patients.add(ToolkitResourceUtil.generatePerson(Context.getPatientService().getPatientByUuid(p.getUuid())));
        }
        return patients;
    }

    @RequestMapping(value = "/patientportaltoolkit/getallnonvoidedspherepatients")
    @ResponseBody
    public Object getAllPatientPortalNonVoidedSHPEREPatients()
            throws Exception {

        org.openmrs.api.PatientService patientService = Context.getPatientService();
        UserService userService = Context.getUserService();

        List<Patient> omrspatients = patientService.getAllPatients(false);
        List<Object> patients = new ArrayList<Object>();
        for (Patient p : omrspatients) {
            try {

                // User user = userService.getUsersByPerson(p, false).get(0);
                // if (user.getUsername().toUpperCase().startsWith("SPHERE")) {
                patients.add(ToolkitResourceUtil.generatePerson(p));
                // }
            } catch (Exception e) {

            }
        }
        return patients;
    }

    @RequestMapping(value = "/patientportaltoolkit/getuser")
    @ResponseBody
    public Object getPatientPortalUser()
            throws Exception {
        User user = Context.getAuthenticatedUser();
        Map<String, Object> authenticatedUser = new HashMap<String, Object>();
        authenticatedUser.put("id", user.getUuid());
        authenticatedUser.put("Name", user.getDisplayString());

        return authenticatedUser;
    }

    @RequestMapping(value = "/patientportaltoolkit/updatepatient", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePatientPortalPatient(@RequestBody String patientObject)
            throws Exception {
        PatientService patientService = Context.getService(PatientService.class);
        Object updatedPatientObject = patientService.updatePatient(patientObject);
        return updatedPatientObject;

    }
}
