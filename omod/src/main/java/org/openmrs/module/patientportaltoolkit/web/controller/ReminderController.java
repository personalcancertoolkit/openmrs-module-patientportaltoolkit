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

package org.openmrs.module.patientportaltoolkit.web.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.PreventativeCareService;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.GuidelineInterval;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.*;
import java.text.ParseException;

/**
 * Created by Maurya on 08/06/2015.
 */
@Controller
public class ReminderController {

    @RequestMapping( value = "/patientportaltoolkit/getremindersforpatient/{patientId}")
    @ResponseBody
    public Object getAllRemindersforPatient(@PathVariable( "patientId" ) String patientId)
    {
        Patient patient= Context.getPatientService().getPatientByUuid(patientId);
        List<Reminder> reminders = Context.getService(ReminderService.class).getReminders(patient); 
        //List<Reminder> reminders = new ArrayList<Reminder>();
        return ToolkitResourceUtil.generateReminders(reminders);
    }
    
    @RequestMapping( value = "/patientportaltoolkit/getpossiblenewremindersforpatient/{patientId}")
    @ResponseBody
    public Object getPossibleNewRemindersForPatient(@PathVariable( "patientId" ) String patientId)
    {
        
        // Define Possible Reminders for Patient
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();

        /*
        map.put("procedure_name", procedureName);
        map.put("concept_id", Integer.toString(conceptID));
        data.add(map.clone());
        */

        Patient patient= Context.getPatientService().getPatientByUuid(patientId);
        GuidelineConditionSet guidelineConditionSet = Context.getService(ReminderService.class).generateGuidelineConditionSet(patient);
        for (Guideline g:  guidelineConditionSet.getGuidelines()) {
            // and for each guideline's set of intervals (e.g., check up in 6mo, 12mo, and 24mo)
            String procedureName = g.getFollowupProcedure().getName().getName();
            Integer conceptID = g.getFollowupProcedure().getConceptId();
            map.put("procedure_name", procedureName);
            map.put("concept_id", Integer.toString(conceptID));
            data.add(new HashMap<String,String>(map));
        }
        
        return data;
    }
    

    @RequestMapping( value = "/patientportaltoolkit/getpreventivecareforpatient/{patientId}")
    @ResponseBody
    public Object getAllPreventiveCareforPatient(@PathVariable( "patientId" ) String patientId) throws ParseException {
        Patient patient= Context.getPatientService().getPatientByUuid(patientId);
        return ToolkitResourceUtil.generatePreventiveCareEvents(Context.getService(PreventativeCareService.class).getAllPreventativeCareEventByPatient(patient));
    }

    @RequestMapping( value = "/patientportaltoolkit/getsampledates")
    @ResponseBody
    public Object getSampleDates()
    {

        return " [ {\n" +
                "                id: 0,\n" +
                "                name: 'Google I/O',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 4, 28),\n" +
                "                endDate: new Date(currentYear, 4, 29)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 1,\n" +
                "                name: 'Microsoft Convergence',\n" +
                "                location: 'New Orleans, LA',\n" +
                "                startDate: new Date(currentYear, 2, 16),\n" +
                "                endDate: new Date(currentYear, 2, 19)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 2,\n" +
                "                name: 'Microsoft Build Developer Conference',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 3, 29),\n" +
                "                endDate: new Date(currentYear, 4, 1)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 3,\n" +
                "                name: 'Apple Special Event',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 8, 1),\n" +
                "                endDate: new Date(currentYear, 8, 1)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 4,\n" +
                "                name: 'Apple Keynote',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 8, 9),\n" +
                "                endDate: new Date(currentYear, 8, 9)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 5,\n" +
                "                name: 'Chrome Developer Summit',\n" +
                "                location: 'Mountain View, CA',\n" +
                "                startDate: new Date(currentYear, 10, 17),\n" +
                "                endDate: new Date(currentYear, 10, 18)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 6,\n" +
                "                name: 'F8 2015',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 2, 25),\n" +
                "                endDate: new Date(currentYear, 2, 26)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 7,\n" +
                "                name: 'Yahoo Mobile Developer Conference',\n" +
                "                location: 'New York',\n" +
                "                startDate: new Date(currentYear, 7, 25),\n" +
                "                endDate: new Date(currentYear, 7, 26)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 8,\n" +
                "                name: 'Android Developer Conference',\n" +
                "                location: 'Santa Clara, CA',\n" +
                "                startDate: new Date(currentYear, 11, 1),\n" +
                "                endDate: new Date(currentYear, 11, 4)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 9,\n" +
                "                name: 'LA Tech Summit',\n" +
                "                location: 'Los Angeles, CA',\n" +
                "                startDate: new Date(currentYear, 10, 17),\n" +
                "                endDate: new Date(currentYear, 10, 17)\n" +
                "            } ]";
    }
    @RequestMapping( value = "/patientportaltoolkit/getsampledates2")
    @ResponseBody
    public Object getSampleDates2()
    {

        return " [ \n" +
                " {\n" +
                "                id: 0,\n" +
                "                name: 'Google I/O',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 4, 28),\n" +
                "                endDate: new Date(currentYear, 4, 29)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 1,\n" +
                "                name: 'Microsoft Convergence',\n" +
                "                location: 'New Orleans, LA',\n" +
                "                startDate: new Date(currentYear, 2, 16),\n" +
                "                endDate: new Date(currentYear, 2, 19)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 2,\n" +
                "                name: 'Microsoft Build Developer Conference',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 3, 29),\n" +
                "                endDate: new Date(currentYear, 4, 1)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 3,\n" +
                "                name: 'Apple Special Event',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 8, 1),\n" +
                "                endDate: new Date(currentYear, 8, 1)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 4,\n" +
                "                name: 'Apple Keynote',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 8, 9),\n" +
                "                endDate: new Date(currentYear, 8, 9)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 5,\n" +
                "                name: 'Chrome Developer Summit',\n" +
                "                location: 'Mountain View, CA',\n" +
                "                startDate: new Date(currentYear, 10, 17),\n" +
                "                endDate: new Date(currentYear, 10, 18)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 6,\n" +
                "                name: 'F8 2015',\n" +
                "                location: 'San Francisco, CA',\n" +
                "                startDate: new Date(currentYear, 2, 25),\n" +
                "                endDate: new Date(currentYear, 2, 26)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 7,\n" +
                "                name: 'Yahoo Mobile Developer Conference',\n" +
                "                location: 'New York',\n" +
                "                startDate: new Date(currentYear, 7, 25),\n" +
                "                endDate: new Date(currentYear, 7, 26)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 8,\n" +
                "                name: 'Android Developer Conference',\n" +
                "                location: 'Santa Clara, CA',\n" +
                "                startDate: new Date(currentYear, 11, 1),\n" +
                "                endDate: new Date(currentYear, 11, 4)\n" +
                "            },\n" +
                "            {\n" +
                "                id: 9,\n" +
                "                name: 'LA Tech Summit',\n" +
                "                location: 'Los Angeles, CA',\n" +
                "                startDate: new Date(currentYear, 10, 17),\n" +
                "                endDate: new Date(currentYear, 10, 17)\n" +
                "            } \n"+
                "             ]"
                ;
    }
}
