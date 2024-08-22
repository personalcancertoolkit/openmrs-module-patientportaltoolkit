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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.EventLog;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.openmrs.module.patientportaltoolkit.api.db.GuidelineDAO;
import org.openmrs.module.patientportaltoolkit.api.db.PatientEmailSubscriptionDAO;

import java.util.List;

public class HibernatePatientEmailSubscriptionDAO implements PatientEmailSubscriptionDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PatientEmailSubscription save(PatientEmailSubscription subscription) {
        sessionFactory.getCurrentSession().saveOrUpdate(subscription);
        return subscription;
    }

    @Override
    public PatientEmailSubscription getSubscriptionForPerson(Person person) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientEmailSubscription.class);
        c.add(Restrictions.eq("person", person));
        c.addOrder(Order.desc("dateCreated"));

        @SuppressWarnings("unchecked")
        List<PatientEmailSubscription> list = c.list();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }
}
