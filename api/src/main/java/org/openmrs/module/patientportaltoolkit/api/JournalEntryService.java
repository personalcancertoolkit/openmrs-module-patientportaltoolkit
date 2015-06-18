package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public interface JournalEntryService extends OpenmrsService {

    /**
     * @return all journal entries
     * @should return all journal entries
     */
    @Transactional(readOnly = true)
    List<JournalEntry> getAllJournalEntries();

    /**
     * @param entryId
     * @return the journal entry with the corresponding entryId
     * @should return entry with given id
     */
    @Transactional(readOnly = true)
    JournalEntry getJournalEntry(String uuid);

    /**
     * @param p the person who wrote the journal entries
     * @return all journal entries written by person p
     * @should return entries written by supplied person
     * @should return entries ordered by date
     */
    @Transactional(readOnly = true)
    List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc);

    /**
     * @param p the person who wrote the journal entries
     * @param searchText the text to searc hfor
     * @return all journal entries written by person p
     * @should return entries written by supplied person
     * @should return entries ordered by date
     * @should return entries with searchString in title or body
     */
    @Transactional(readOnly = true)
    List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc);

    /**
     * Create or update journal entry
     */
    @Transactional
    void saveJournalEntry(JournalEntry entry) throws APIException;

    /**
     * Delete journal entry
     */
    @Transactional
    void deleteJournalEntry(JournalEntry entry) throws APIException;

    /**
     * Sets the deleted flag to true
     */
    @Transactional
    void softDelete(JournalEntry entry);

    /**
     * find all comments of a given entry
     * @param entry parent entry of comments
     * @return all comments of a given entry
     */
    @Transactional
    List<JournalEntry> findComments(JournalEntry entry);
}
