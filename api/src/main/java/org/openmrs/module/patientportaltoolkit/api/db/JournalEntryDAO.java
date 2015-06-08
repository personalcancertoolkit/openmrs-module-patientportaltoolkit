package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.JournalEntry;

import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public interface JournalEntryDAO {

    void deleteJournalEntry(JournalEntry entry);

    List<JournalEntry> getAllJournalEntries();

    JournalEntry getJournalEntry(String uuid);

    void saveJournalEntry(JournalEntry entry);

    List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc);

    List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc);

    void softDelete(JournalEntry entry);

    List<JournalEntry> findComments(JournalEntry entry);
}
