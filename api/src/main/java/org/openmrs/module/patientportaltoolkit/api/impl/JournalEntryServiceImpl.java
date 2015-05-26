package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.db.JournalEntryDAO;

import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public class JournalEntryServiceImpl extends BaseOpenmrsService implements JournalEntryService {

    protected JournalEntryDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public JournalEntryDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(JournalEntryDAO dao) {
        this.dao = dao;
    }


    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#deleteJournalEntry(org.openmrs.module.phrjournal.domain.JournalEntry)
     */
    public void deleteJournalEntry(JournalEntry entry) throws APIException {
        dao.deleteJournalEntry(entry);
    }

    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#getAllJournalEntries()
     */
    public List<JournalEntry> getAllJournalEntries() {
        return dao.getAllJournalEntries();
    }

    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#getJournalEntry(java.lang.Integer)
     */
    public JournalEntry getJournalEntry(Integer entryId) {
        return dao.getJournalEntry(entryId);
    }

    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#saveJournalEntry(org.openmrs.module.phrjournal.domain.JournalEntry)
     */
    public void saveJournalEntry(JournalEntry entry) throws APIException {
        dao.saveJournalEntry(entry);
    }

    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#getJournalEntryForPerson(org.openmrs.Person)
     */
    public List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc ) {
        return dao.getJournalEntryForPerson(p, orderByDateDesc);
    }

    /**
     * @see org.openmrs.module.phrjournal.JournalEntryService#findEntries(java.lang.String, org.openmrs.Person, java.lang.Boolean)
     */
    public List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc) {
        return dao.findEntries(searchText,p,orderByDateDesc);
    }

    public void softDelete(JournalEntry entry) {
        dao.softDelete(entry);
    }

    public List<JournalEntry> findComments(JournalEntry entry){
        return dao.findComments(entry);
    }
}
