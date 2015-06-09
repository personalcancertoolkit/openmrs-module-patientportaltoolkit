package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.db.ReminderDAO;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public class HibernateReminderDAO implements ReminderDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void deleteReminder(Reminder reminder) {

    }

    @Override
    public List<Reminder> getAllRemindersByPatient(Patient patient) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        c.add(Restrictions.eq("patient", patient));
        return c.list();
    }

    @Override
    public void saveReminder(Reminder reminder) {

    }
}
