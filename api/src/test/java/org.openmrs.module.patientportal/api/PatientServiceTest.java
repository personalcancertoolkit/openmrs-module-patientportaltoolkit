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


package org.openmrs.module.patientportal.api;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PatientService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by maurya.
 */
public class PatientServiceTest extends BaseModuleContextSensitiveTest {

    protected static final String PAT_SEARCH_DATA_XML = "org/openmrs/api/include/PatientServiceTest-findPatients.xml";

    public PatientService getService() {
        return Context.getService(PatientService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(PAT_SEARCH_DATA_XML);
    }
    @Test
    public void shouldSetupContext(){
        assertNotNull(getService());
    }

    /**
     * @verifies not return null value
     * @see PatientService#getPatient(String)
     */
    @Test
    public void getPatient_shouldNotReturnNullValue() throws Exception {
        String patientUuid = "61b38324-e2fd-4feb-95b7-9e9a2a4400df";
        Map<String, Object> patientObject = (Map<String, Object>) getService().getPatient(patientUuid);
        assertNotNull(patientObject);
        assertEquals(patientObject.get("id"), patientUuid);
    }

    @Test
    public void getAllPatients_shouldNotReturnNullValue() throws Exception {
        List<Patient> patientObjects = getService().getAllPatients();
        assertNotNull(patientObjects);
    }

//    @Test
//    public void updatePatient_shouldNotReturnNullValue() throws Exception {
//        String json= "{\n" +
//                "\"id\": \"256ccf6d-6b41-455c-9be2-51ff4386ae76\",\n" +
//                "\"GivenName\": \"Alomnaa\",\n" +
//                "\"MiddleName\": null,\n" +
//                "\"FamilyName\": \"Pacinoo\",\n" +
//                "\"Age\": 22,\n" +
//                "\"Gender\": \"M\",\n" +
//                "\"Phone\": \"11111111\",\n" +
//                "\"DOB\": null,\n" +
//                "\"Email\": null,\n" +
//                "\"Address\": {\n" +
//               // "\"id\": \"76ce88d3-4b09-42ee-8188-40f69379872b\",\n" +
//                "\"Address1\": \"address 1\",\n" +
//                "\"Address2\": \"Address 23\",\n" +
//                "\"City/Village\": \"Indianapolis\",\n" +
//                "\"State/Province\": \"Indiana\",\n" +
//                "\"Country\": \"United States of America\",\n" +
//                "\"PostalCode\": \"46202\"\n" +
//                "}\n" +
//                "}";
//        Map<String, Object> patientObject = (Map<String, Object>) getService().updatePatient(json);
//        assertNotNull(patientObject);
//        assertEquals(patientObject.get("id"), "256ccf6d-6b41-455c-9be2-51ff4386ae76");
//    }
}
