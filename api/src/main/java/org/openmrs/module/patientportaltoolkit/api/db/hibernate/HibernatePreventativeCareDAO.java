/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
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
import org.joda.time.LocalDate;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
import org.openmrs.module.patientportaltoolkit.api.db.PreventativeCareDAO;

import java.util.Date;
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
    public PreventativeCareEvent getMostRecentPreventativeCareEventInStatusPriorToDateForProcedureAndPatient(
            Patient patient,
            Date startDate,
            Concept followupProcedure,
            Integer status) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PreventativeCareEvent.class);
        c.add(Restrictions.eq("patient", patient));
        c.add(Restrictions.eq("followProcedure", followupProcedure));

        if (status == PreventativeCareEvent.COMPLETED_STATUS) {
            c.add(Restrictions.le("completeDate", startDate));
            c.addOrder(Order.desc("completeDate"));
        } else {
            c.add(Restrictions.le("targetDate", startDate));
            c.addOrder(Order.desc("targetDate"));
        }
        c.add(Restrictions.eq("status", status));

        List l = c.list();
        if (l.size() > 0) {
            return (PreventativeCareEvent) l.get(0);
        }
        return null;
    }

    @Override
    public PreventativeCareEvent getPreventativeCareEventInStatusFarthestAwayFromDateForProcedureAndPatient(
            Patient patient,
            Date startDate,
            Integer numberOfMonthsIntoTheFuture,
            Concept followupProcedure,
            Integer status) {

        Date targetDate = new LocalDate(startDate).plusMonths(numberOfMonthsIntoTheFuture).toDate();

        Criteria c = sessionFactory.getCurrentSession().createCriteria(PreventativeCareEvent.class);
        c.add(Restrictions.eq("patient", patient));
        c.add(Restrictions.eq("followProcedure", followupProcedure));
        c.add(Restrictions.le("targetDate", targetDate));
        c.add(Restrictions.eq("status", status));

        c.addOrder(Order.desc("targetDate"));

        List l = c.list();
        if (l.size() > 0) {
            return (PreventativeCareEvent) l.get(0);
        }
        return null;
    }

    @Override
    public PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent) {
        sessionFactory.getCurrentSession().saveOrUpdate(preventativeCareEvent);
        return preventativeCareEvent;
    }

    @Override
    public PreventativeCareEvent getPreventativeCareEvent(Integer id) {
        return (PreventativeCareEvent) sessionFactory.getCurrentSession().get(PreventativeCareEvent.class, id);
    }

    @Override
    public List<PreventiveCareGuideline> getAllPreventativeCareGuidelines() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PreventiveCareGuideline.class);
        return c.list();
    }

    @Override
    public PreventiveCareGuideline getPreventativeCareGuidelinebyID(Integer id) {
        return (PreventiveCareGuideline) sessionFactory.getCurrentSession().get(PreventiveCareGuideline.class, id);
    }
}
