package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.api.db.PreventativeCareDAO;

import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public class HibernatePreventativeCareDAO implements PreventativeCareDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deletePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();
        sess.setFlushMode(FlushMode.COMMIT); // allow queries to return stale state
        sess.delete(preventativeCareEvent);
        tx.commit();
        sess.close();
    }

    @Override
    public List<PreventativeCareEvent> getAllPreventativeCareEventsByPatient(Patient patient) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PreventativeCareEvent.class);
        c.add(Restrictions.eq("patient", patient));
        return c.list();
    }

    @Override
    public PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        sessionFactory.getCurrentSession().saveOrUpdate(preventativeCareEvent);
        return preventativeCareEvent;
    }

    @Override
    public PreventativeCareEvent getPreventativeCareEvent(Integer id) {
        return (PreventativeCareEvent) sessionFactory.getCurrentSession().get(PreventativeCareDAO.class, id);
    }
}
