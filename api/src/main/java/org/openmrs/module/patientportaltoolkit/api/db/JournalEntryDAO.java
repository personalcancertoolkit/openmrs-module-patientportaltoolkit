package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.JournalEntry;

import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public interface JournalEntryDAO {

    public void deleteJournalEntry(JournalEntry entry);

    public List<JournalEntry> getAllJournalEntries();

    public JournalEntry getJournalEntry(String uuid);

    public void saveJournalEntry(JournalEntry entry);

    public List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc);

    public List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc);

    public void softDelete(JournalEntry entry);

    public List<JournalEntry> findComments(JournalEntry entry);
}
