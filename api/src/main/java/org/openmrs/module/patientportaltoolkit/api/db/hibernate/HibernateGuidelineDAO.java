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
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.db.GuidelineDAO;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public class HibernateGuidelineDAO implements GuidelineDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void deleteGuideline(Guideline guideline) {

    }

    @Override
    public List<Guideline> getAllGuidelines() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        return c.list();
    }


    @Override
    public List<GuidelineInterval> getAllGuidelinesInterval(GuidelineInterval guideLineIntervalObj) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(GuidelineInterval.class);
        c.add(Restrictions.eq("guideline", guideLineIntervalObj));
        c.addOrder(Order.asc("id"));
        return c.list();
    }

    @Override
    public void saveGuideline(Guideline guideline) {

    }
    @Override
    public  List<Guideline> getGuidelinesbyConditions(List<Concept> conditions) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        return c.list();
    }

    @Override
    public List<GuidelineConditionSet> getAllGuidelineConditionSet() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(GuidelineConditionSet.class);
        return c.list();
    }

    @Override
    public Guideline getGuidelineById(int guideLineId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        c.add(Restrictions.eq("id", guideLineId));
        return (Guideline) c.uniqueResult();
    }



    @Override
    public List<GuidelineInterval> getAllGuidelinesInterval(Guideline guidLineObj) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(GuidelineInterval.class);
        c.add(Restrictions.eq("guideline", guidLineObj));
        c.addOrder(Order.asc("id"));
        return c.list();
    }

    @Override
    public void saveGuideLine(Guideline guidLineObj) {
        sessionFactory.getCurrentSession().saveOrUpdate(guidLineObj);
    }

    @Override
    public void saveGuideLineInterval(GuidelineInterval guidLineIntervalObj) {
        sessionFactory.getCurrentSession().saveOrUpdate(guidLineIntervalObj);
    }

    @Override
    public void deleteGuidelineInterval(GuidelineInterval guidLineIntervalObj) {
        sessionFactory.getCurrentSession().delete(guidLineIntervalObj);
    }


    @Override
    public GuidelineConditionSet getGuidelineConditionSetByConditionName(String ConditionName) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(GuidelineConditionSet.class);
        c.add(Restrictions.eq("conditionName", ConditionName));
        return  (GuidelineConditionSet) c.uniqueResult();
    }

    @Override
    public void saveGuideLineConditionSet(GuidelineConditionSet guideLineConditionSetObj) {
        sessionFactory.getCurrentSession().saveOrUpdate(guideLineConditionSetObj);
    }
}
