package org.openmrs.module.patientportal.api;

import org.junit.Before;
import org.junit.Test;
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
    public void shouldSetupContext() {
        assertNotNull(getService());
    }

    /**
     * @verifies not return null value
     * @see PatientService#getPatient(String)
     */
    @Test
    public void getPatient_shouldNotReturnNullValue() throws Exception {
        String patientUuid = "61b38324-e2fd-4feb-95b7-9e9a2a4400df";
        Map<String, Object> patientObject = getService().getPatient(patientUuid);
        assertNotNull(patientObject);
        assertEquals(patientObject.get("id"), patientUuid);
    }

    @Test
    public void getAllPatients_shouldNotReturnNullValue() throws Exception {
        List<Object> patientObjects = getService().getAllPatients();
        assertNotNull(patientObjects);
    }
}
