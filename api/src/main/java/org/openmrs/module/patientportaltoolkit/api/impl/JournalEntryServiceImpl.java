package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.db.JournalEntryDAO;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
     *
     */
    public void deleteJournalEntry(JournalEntry entry) throws APIException {
        dao.deleteJournalEntry(entry);
    }

    /**
     *
     */
    public List<JournalEntry> getAllJournalEntries() {

        return dao.getAllJournalEntries();
    }

    /**
     *
     */
    public JournalEntry getJournalEntry(String uuid) {
        return dao.getJournalEntry(uuid);
    }

    /**
     *
     */
    public void saveJournalEntry(JournalEntry entry) throws APIException {
        entry.setCreator(Context.getAuthenticatedUser().getPerson());
        Date date = new Date();
        entry.setDateCreated(date);
        dao.saveJournalEntry(entry);
    }

    /**
     *
     */
    public List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc ) {
        List<JournalEntry> totalJournalList= dao.getJournalEntryForPerson(p, orderByDateDesc);
        List<JournalEntry> returnJournalList = new ArrayList<JournalEntry>();
        for(JournalEntry je: totalJournalList){
            if(je.getParentEntryId() ==null)
                returnJournalList.add(je);
        }
        for(JournalEntry je:returnJournalList){
            if(je.getChildren() !=null){
                Set<JournalEntry> journalEntriesSet= new TreeSet<JournalEntry>(new Comparator<JournalEntry>() {
                    public int compare(JournalEntry je1, JournalEntry je2) {
                        return je1.getDateCreated().compareTo(je2.getDateCreated());
                    }
                });
                journalEntriesSet.addAll(je.getChildren());

                je.setChildren(journalEntriesSet);
            }
        }
        return returnJournalList;
    }

    /**
     *
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
