package org.openmrs.module.patientportal.api;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by srikumma on 5/15/17.
 */
public class JournalEntryServiceTest extends BaseModuleContextSensitiveTest {
    @Before
    public void setup() throws Exception {
        executeDataSet("baseTestDataset.xml");
    }

    public JournalEntryService getService() {
        return Context.getService(JournalEntryService.class);
    }


    @Test
    public void getAllJournalEntries_shouldNotReturnNullValue() throws Exception {
        List<JournalEntry> journalEntries =  getService().getAllJournalEntries();
        assertNotEquals(journalEntries.size(),0);
    }
}
