/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportal.api;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.PersonService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by maurya.
 */
public class PersonServiceTest extends BaseModuleContextSensitiveTest {

    protected static final String PERSON_INITIAL_DATA_XML = "org/openmrs/api/include/PersonServiceTest-createPersonPurgeVoidTest.xml";

    public PersonService getService() {
        return Context.getService(PersonService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(PERSON_INITIAL_DATA_XML);
    }

    @Test
    public void shouldSetupContext() {
        assertNotNull(getService());
    }
    /**
     * @verifies not return null value
     * @see PersonService#getPerson(String)
     */
    @Ignore
    @Test
    public void getPerson_shouldNotReturnNullValue() throws Exception {
        String personUuid = "dagh524f-27ce-4bb2-86d6-6d1d05312bd5";
        Map<String, Object> personObject = getService().getPerson(personUuid);
		assertNotNull(personObject);
		assertEquals(personObject.get("id"), personUuid);
    }

    /**
     * @verifies return proper formatted date
     * @see PersonService#getPerson(String)
     */
    @Ignore
    @Test
    public void getPerson_shouldReturnProperDate() throws Exception {
        String personUuid = "dagh524f-27ce-4bb2-86d6-6d1d05312bd5";
        Map<String, Object> personObject = getService().getPerson(personUuid);
        assertNotNull(personObject);
        assertEquals(personObject.get("DOB").toString(), "Tue Apr 08 00:00:00 EST 1975");
    }

    @Ignore
    @Test
    public void updatePerson_shouldNotReturnNull() throws Exception {
        String json= "{\n" +
                "\"id\": \"dagh524f-27ce-4bb2-86d6-6d1d05312bd5\",\n" +
                "\"GivenName\": \"Alomnaa\",\n" +
                "\"MiddleName\": null,\n" +
                "\"FamilyName\": \"Pacinoo\",\n" +
                "\"Age\": 22,\n" +
                "\"Gender\": \"M\",\n" +
                "\"Phone\": \"11111111\",\n" +
                "\"DOB\": null,\n" +
                "\"Email\": null,\n" +
                "\"Address\": {\n" +
                //"\"id\": \"76ce88d3-4b09-42ee-8188-40f69379872b\",\n" +
                "\"Address1\": \"address 1\",\n" +
                "\"Address2\": \"Address 23\",\n" +
                "\"City/Village\": \"Indianapolis\",\n" +
                "\"State/Province\": \"Indiana\",\n" +
                "\"Country\": \"United States of America\",\n" +
                "\"PostalCode\": \"46202\"\n" +
                "}\n" +
                "}";
        Map<String, Object> personObject = (Map<String, Object>) getService().updatePerson(json);
        assertNotNull(personObject);
        assertEquals(personObject.get("id"), "dagh524f-27ce-4bb2-86d6-6d1d05312bd5");
    }
}
