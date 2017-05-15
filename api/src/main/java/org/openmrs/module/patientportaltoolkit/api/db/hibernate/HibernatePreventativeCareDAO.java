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
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;
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
