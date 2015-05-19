package org.openmrs.module.patientportal.api;

import org.junit.Before;
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
    @Test
    public void getPerson_shouldNotReturnNullValue() throws Exception {
        String personUuid = "dagh524f-27ce-4bb2-86d6-6d1d05312bd5";
        Map<String, Object> personObject = getService().getPerson(personUuid);
		assertNotNull(personObject);
		assertEquals(personObject.get("id"), personUuid);
    }
}
