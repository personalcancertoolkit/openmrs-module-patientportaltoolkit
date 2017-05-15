/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.db.ReminderDAO;

import java.util.Calendar;
import java.util.Date;
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
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();
        sess.setFlushMode(FlushMode.COMMIT); // allow queries to return stale state
        sess.delete(reminder);
        tx.commit();
        sess.close();
    }

    @Override
    public List<Reminder> getAllRemindersByPatient(Patient patient) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        c.add(Restrictions.eq("patient", patient));
        return c.list();
    }

    @Override
    public Reminder getReminder(Integer id) {
        return (Reminder) sessionFactory.getCurrentSession().get(Reminder.class, id);
    }

    /**
     *
     */
    @Override
    public Reminder saveReminder(Reminder reminder) {
        sessionFactory.getCurrentSession().saveOrUpdate(reminder);
        return reminder;
    }



    /**
     *
     */
    @Override
    public List<Reminder> getReminders(Patient pat) {
        //Query query = sessionFactory.getCurrentSession().createQuery("from LafReminder where allowedUrl = :url ");
        //query.setParameter("url", url);
        //List list0 = query.list();
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        crit.add(Restrictions.eq("patient", pat));
        crit.add(Restrictions.isNull("completeDate"));
        crit.add(Restrictions.isNotNull("targetDate"));
        crit.addOrder(Order.asc("targetDate"));
        @SuppressWarnings("unchecked")
        List<Reminder> list = (List<Reminder>) crit.list();
        if (list.size() >= 1)
            return list;
        else
            return null;
    }

    @Override
    public List<Reminder> getRemindersByProvider(Patient pat) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        crit.add(Restrictions.eq("patient", pat));
        crit.add(Restrictions.isNull("completeDate"));
        crit.add(Restrictions.isNotNull("targetDate"));
        crit.add(Restrictions.eq("responseType", "PHR_PROVIDER"));
        crit.addOrder(Order.asc("targetDate"));
        @SuppressWarnings("unchecked")
        List<Reminder> list = (List<Reminder>) crit.list();
        if (list.size() >= 1)
            return list;
        else
            return null;
    }

    @Override
    public List<Reminder> getRemindersCompleted(Patient pat) {
        //Query query = sessionFactory.getCurrentSession().createQuery("from LafReminder where allowedUrl = :url ");
        //query.setParameter("url", url);
        //List list0 = query.list();
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        crit.add(Restrictions.eq("patient", pat));
        crit.add(Restrictions.isNotNull("completeDate"));
        crit.addOrder(Order.asc("completeDate"));
        @SuppressWarnings("unchecked")
        List<Reminder> list = (List<Reminder>) crit.list();
        if (list.size() >= 1)
            return list;
        else
            return null;
    }

    @Override
    public Reminder getReminder(Patient pat, Concept careType, Date targetDate) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Reminder.class);
        crit.add(Restrictions.eq("patient", pat));
        crit.add(Restrictions.eq("followProcedure", careType));
        crit.add(Restrictions.eq("targetDate", clearDate(targetDate)));
        //crit.add(Restrictions.lt("targetDate", oneDayLater(targetDate)));
        crit.add(Restrictions.isNull("completeDate"));
        crit.addOrder(Order.asc("targetDate"));
        @SuppressWarnings("unchecked")
        List<Reminder> list = (List<Reminder>) crit.list();
        if (list.size() == 1) {
            log.debug("One reminder is found: patient=" + pat + "|careType=" + careType + "|targetDate=" + targetDate);
            return list.get(0);
        }
        else if(list.size() > 1) {
            log.error("More than one reminder is found: patient=" + pat + "|careType=" + careType + "|targetDate=" + targetDate);
            return list.get(0);
        }
        else
            return null;
    }

    public static Date clearDate(Date dateTime) {
        if(dateTime == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateWithoutTime = cal.getTime();
        return dateWithoutTime;
    }
}
