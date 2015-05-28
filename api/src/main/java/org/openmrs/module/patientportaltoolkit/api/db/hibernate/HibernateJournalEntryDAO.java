package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.db.JournalEntryDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public class HibernateJournalEntryDAO implements JournalEntryDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     */
    public List<JournalEntry> getAllJournalEntries() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(JournalEntry.class);
        return c.list();
    }

    /**
     *
     */
    public JournalEntry getJournalEntry(String uuid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(JournalEntry.class);
        c.add(Restrictions.eq("uuid", uuid));
        return (JournalEntry) c.uniqueResult();
    }

    /**
     *
     */
    public void saveJournalEntry(JournalEntry entry) {
        sessionFactory.getCurrentSession().saveOrUpdate(entry);
    }

    /**
     *
     */
    public void deleteJournalEntry(JournalEntry entry) {
        sessionFactory.getCurrentSession().delete(entry);
    }

    /**
     *
     */
    public List<JournalEntry> getJournalEntryForPerson(Person p, Boolean orderByDateDesc) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(JournalEntry.class);
        if(orderByDateDesc != null){
            if(orderByDateDesc){
                c.addOrder(Order.desc("dateCreated"));
            }else{
                c.addOrder(Order.asc("dateCreated"));
            }
        }
        c.add(Restrictions.eq("creator",p));
        c.add(Restrictions.eq("deleted", false));
        return c.list();
    }

    public List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(JournalEntry.class);
        if(searchText != null && !searchText.trim().equals("")){
            c.add(Restrictions.or(Restrictions.like("title", searchText, MatchMode.ANYWHERE),
                    Restrictions.like("content", searchText,MatchMode.ANYWHERE)));

        }
        if(p != null){
            c.add(Restrictions.eq("creator",p));
        }
        if(orderByDateDesc != null){
            if(orderByDateDesc){
                c.addOrder(Order.desc("dateCreated"));
            }else{
                c.addOrder(Order.asc("dateCreated"));
            }
        }
        c.add(Restrictions.eq("deleted", false));
        return c.list();
    }

    public List<JournalEntry> findComments(JournalEntry entry) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(JournalEntry.class);
        c.add(Restrictions.eq("deleted", false));
        c.add(Restrictions.eq("parentEntryId", entry.getEntryId()));
        return c.list();
    }

    public void softDelete(JournalEntry entry) {
        if(entry.getParentEntryId()==null) {
            List<JournalEntry> comments = findComments(entry);
            if(comments != null) {
                for(JournalEntry comment : comments) {
                    softDelete(comment);
                }
            }
        }

        entry.setDeleted(true);
        entry.setDateDeleted(new Date());
        saveJournalEntry(entry);
    }
}
